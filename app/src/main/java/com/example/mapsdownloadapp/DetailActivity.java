package com.example.mapsdownloadapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mapsdownloadapp.adapter.MyRecyclerRegionsAdapter;
import com.example.mapsdownloadapp.model.Region;
import com.example.mapsdownloadapp.utils.ParserSingleton;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    ActionBar mBar;
    RecyclerView mRecyclerView;
    MyRecyclerRegionsAdapter mAdapter;
    ArrayList<Region>  mRegions;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        pos = bundle.getInt("position");

        mBar = getSupportActionBar();
        mBar.setHomeButtonEnabled(true);
        mBar.setDisplayHomeAsUpEnabled(true);
        mBar.setTitle(bundle.getString("name"));



        mRecyclerView = findViewById(R.id.detail_container);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRegions = ParserSingleton.getInstance(this);
        mAdapter = new MyRecyclerRegionsAdapter(mRegions.get(pos).getChildRegions(),this);
        mRecyclerView.setAdapter(mAdapter);


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}