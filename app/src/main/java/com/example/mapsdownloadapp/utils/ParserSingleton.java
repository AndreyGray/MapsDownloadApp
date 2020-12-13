package com.example.mapsdownloadapp.utils;

import android.content.Context;
import android.util.Log;

import com.example.mapsdownloadapp.R;
import com.example.mapsdownloadapp.RegionsResourceParser;
import com.example.mapsdownloadapp.model.Region;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class ParserSingleton {

    private static ArrayList<Region> mRegions;

    private static ParserSingleton INSTANCE = new ParserSingleton();

    private ParserSingleton() {mRegions=new ArrayList<>();}


    public static ArrayList<Region> getInstance(Context context){
        if (INSTANCE.mRegions.size()==0){
            XmlPullParser xpp = context.getResources().getXml(R.xml.regions);
            return parseXpp(xpp);

        }else
            return INSTANCE.mRegions;
    }

    private static ArrayList<Region> parseXpp(XmlPullParser xpp) {
       RegionsResourceParser par =  new RegionsResourceParser();
       par.parse(xpp);
       mRegions.addAll(par.getRegions());
       return par.getRegions();

    }
}
