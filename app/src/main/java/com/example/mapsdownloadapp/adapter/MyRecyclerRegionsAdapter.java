package com.example.mapsdownloadapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.example.mapsdownloadapp.DetailActivity;
import com.example.mapsdownloadapp.R;
import com.example.mapsdownloadapp.model.Region;
import com.example.mapsdownloadapp.utils.Utils;
import java.util.List;

public class MyRecyclerRegionsAdapter extends RecyclerView.Adapter<MyViewHolder>{

    List<Region> regions;
    Context mContext;

    private static String dirPath;
    int downloadId;

    public MyRecyclerRegionsAdapter(List<Region> regions, Context context) {
        this.regions = regions;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_region,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name =regions.get(position).getName().substring(0,1).toUpperCase()+
                regions.get(position).getName().substring(1).toLowerCase();
        holder.tvRegionName.setText(name);

        if(regions.get(position).isMapDownloaded()){
            holder.imageMap.setColorFilter(mContext.getColor(R.color.item_icon_green));
            holder.importButton.setVisibility(View.GONE);
        }else
        {
            holder.imageMap.setColorFilter(mContext.getColor(R.color.item_icon_color));
            holder.importButton.setVisibility(View.VISIBLE);
        }

        if(!regions.get(position).isMap()){

            holder.importButton.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_action_remove_dark,mContext.getTheme()));
            holder.importButton.setEnabled(false);
        }else{
            holder.importButton.setEnabled(true);
            holder.importButton.setImageDrawable(mContext.getDrawable(R.drawable.ic_action_import));
            holder.importButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    downloadId = position;
                    String url = "http://download.osmand.net/download.php?standard=yes&file=" + regions.get(position).getDownloadName();
                    dirPath = Utils.getRootDirPath(mContext.getApplicationContext());
                    holder.containerBar.setVisibility(View.VISIBLE);

                    if (Status.RUNNING == PRDownloader.getStatus(downloadId)) {
                        PRDownloader.pause(downloadId);
                        return;
                    }

                    holder.importButton.setEnabled(false);
                    holder.progressDownload.setIndeterminate(true);
                    holder.progressDownload.getIndeterminateDrawable().setColorFilter(
                            Color.BLUE, PorterDuff.Mode.SRC_IN);

                    if (Status.PAUSED == PRDownloader.getStatus(downloadId)) {
                        PRDownloader.resume(downloadId);
                        return;
                    }
                    downloadId = PRDownloader.download(url, dirPath, regions.get(position).getName())
                            .build()
                            .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                                @Override
                                public void onStartOrResume() {
                                    holder.progressDownload.setIndeterminate(false);
                                }
                            })
                            .setOnPauseListener(new OnPauseListener() {
                                @Override
                                public void onPause() {
                                }
                            })
                            .setOnCancelListener(new OnCancelListener() {
                                @Override
                                public void onCancel() {
                                    downloadId = 0;
                                    holder.progressDownload.setProgress(0);
                                    holder.textDownload.setText("");
                                    holder.progressDownload.setIndeterminate(false);
                                }
                            })
                            .setOnProgressListener(new OnProgressListener() {
                                @Override
                                public void onProgress(Progress progress) {
                                    long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                    holder.progressDownload.setProgress((int) progressPercent);
                                    holder.textDownload.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                                }
                            })
                            .start(new OnDownloadListener() {
                                @Override
                                public void onDownloadComplete() {
                                    holder.imageMap.setColorFilter(mContext.getResources().getColor(R.color.item_icon_green, mContext.getTheme()));
                                    holder.importButton.setEnabled(false);
                                    holder.importButton.setVisibility(View.GONE);
                                    holder.containerBar.setVisibility(View.GONE);
                                    regions.get(position).setMapDownloaded(true);
                                }

                                @Override
                                public void onError(Error error) {
                                    holder.textDownload.setText("");
                                    holder.progressDownload.setProgress(0);
                                    downloadId = 0;
                                    holder.progressDownload.setIndeterminate(false);
                                    holder.importButton.setEnabled(true);
                                    holder.containerBar.setVisibility(View.GONE);
                                }
                            });
                }
            });
        }



        if (regions.get(position).getChildRegions().size()!=0){
            holder.tvRegionName.setTextColor(Color.BLUE);
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra("position",position);
                    intent.putExtra("name",name);
                    mContext.startActivity(intent);
                }
            });

        }else {
            holder.tvRegionName.setTextColor(mContext.getColor(R.color.item_text_color));
            holder.container.setOnClickListener(null);
        }

    }

    @Override
    public int getItemCount() {
        return regions.size();
    }

}
