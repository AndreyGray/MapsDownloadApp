package com.example.mapsdownloadapp.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapsdownloadapp.R;

public class MyViewHolder extends RecyclerView.ViewHolder{
    ImageView imageMap;
    TextView tvRegionName;
    CardView container;
    ImageButton importButton;
    LinearLayout containerBar;

    ProgressBar progressDownload;
    TextView textDownload;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        imageMap = itemView.findViewById(R.id.image_map_ic);
        tvRegionName = itemView.findViewById(R.id.tv_region_name);
        container = itemView.findViewById(R.id.item_container);
        importButton = itemView.findViewById(R.id.image_import);

        containerBar = itemView.findViewById(R.id.container_download_bar);
        progressDownload =itemView.findViewById(R.id.progress_downloading);
        textDownload = itemView.findViewById(R.id.textProgress);


    }
}
