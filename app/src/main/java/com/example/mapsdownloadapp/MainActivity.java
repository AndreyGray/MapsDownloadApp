package com.example.mapsdownloadapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mapsdownloadapp.adapter.MyRecyclerRegionsAdapter;
import com.example.mapsdownloadapp.model.Region;
import com.example.mapsdownloadapp.utils.ParserSingleton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity{
    TextView mViewFreeSpaceGb;

    ProgressBar mBar;

    RecyclerView mRecyclerView;
    MyRecyclerRegionsAdapter adapter;
    LinearLayoutManager layoutManager;

    ArrayList<Region> mRegions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dexter.withContext(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();

        initView();

        mRegions =  ParserSingleton.getInstance(this);
        adapter = new MyRecyclerRegionsAdapter(mRegions,MainActivity.this);
        mRecyclerView.setAdapter(adapter);

        freeMemory();
    }


    private void initView() {
            //free space views
            mViewFreeSpaceGb = findViewById(R.id.text_view_free_gb);
            mBar = findViewById(R.id.progress_free_space);

            mRecyclerView = findViewById(R.id.recycler_regions);
            layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);

    }

    public void freeMemory() {
        final double SIZE_KB = 1024;
        final double SIZE_GB = SIZE_KB * SIZE_KB *SIZE_KB;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        double availableSpace = stat.getAvailableBytes()/SIZE_GB;
        mViewFreeSpaceGb.setText(String.format(Locale.ENGLISH,"%.2f",availableSpace));
        mBar.setProgress((int)(100-((stat.getAvailableBytes()*100)/stat.getTotalBytes())));
    }
}