package com.example.mapsdownloadapp;

import android.util.Log;

import com.example.mapsdownloadapp.model.Region;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class RegionsResourceParser {

    public final static String REGION = "region";
    public final static String NAME ="name";
    public final static String TYPE ="type";
    public final static String MAP ="map";
    public final static String WIKI ="wiki";
    public final static String ROADS ="roads";
    public final static String SRTM ="srtm";
    public final static String CONTINENT ="continent";
    public final static String HILLSHADE ="hillshade";
    public final static String TRANSLATE = "translate";
    public final static String DOWNL_SUFF = "download_suffix";
    public final static String IN_DOWNL_SUFF = "inner_download_suffix";
    public final static String DOWNL_PREF = "download_prefix";
    public final static String IN_DOWNL_PREF = "inner_download_prefix";

    String suff = "europe";
    String pref = null;

    ArrayList<Region> mRegions;
    ArrayList<Region> childs;

    public RegionsResourceParser() {
            mRegions = new ArrayList<>();
    }

    public ArrayList<Region> getRegions() {
        return mRegions;
    }

    public boolean parse(XmlPullParser xpp){
        boolean status = true;
        Region currentRegion = new Region();
        boolean inEntry = false;
        boolean inInner =false;
        try {

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG: {
                        if (tagName.equalsIgnoreCase(REGION))  {
                            if (xpp.getAttributeValue(null,"poly_extract")!=null&&xpp.getAttributeValue(null,"poly_extract").contains("-europe")){
                                Log.d("PARSER",xpp.getAttributeCount()+""+xpp.getAttributeValue(null,TYPE));
                                    childs = new ArrayList<>();

                                    inEntry = true;
                                    //mRegions.add(currentRegion);
                                    currentRegion = new Region();
                                    for (int i = 0; i < xpp.getAttributeCount(); i++) {
                                        Log.d("ATRIBUT",xpp.getAttributeName(i));
                                        switch (xpp.getAttributeName(i)) {
                                            case NAME: {
                                                currentRegion.setName(xpp.getAttributeValue(i));
                                                break;
                                            }
                                            case TYPE: {
                                                currentRegion.setType(xpp.getAttributeValue(i));
                                                break;
                                            }
                                            case MAP:{
                                                if(xpp.getAttributeValue(i).equalsIgnoreCase("no"))
                                                    currentRegion.setMap(false);
                                                break;
                                            }
                                            case WIKI:{
                                                if(xpp.getAttributeValue(i).equalsIgnoreCase("no"))
                                                    currentRegion.setWiki(false);
                                                break;
                                            }
                                            case SRTM:{
                                                if(xpp.getAttributeValue(i).equalsIgnoreCase("no"))
                                                    currentRegion.setSrtm(false);
                                                break;
                                            }
                                            case HILLSHADE:{
                                                if(xpp.getAttributeValue(i).equalsIgnoreCase("no"))
                                                    currentRegion.setHillshade(false);
                                                break;
                                            }
                                            case DOWNL_SUFF:{
                                                if(!xpp.getAttributeValue(i).equalsIgnoreCase("$name")){
                                                    currentRegion.setDownloadSuffix(xpp.getAttributeValue(i));
                                                }else{
                                                    currentRegion.setDownloadSuffix(xpp.getAttributeValue(null,NAME));
                                                }
                                                break;
                                            }
                                            case IN_DOWNL_SUFF:{
                                                if(!xpp.getAttributeValue(i).equalsIgnoreCase("$name")){
                                                    suff = xpp.getAttributeValue(i);
                                                }else suff = xpp.getAttributeValue(null,NAME);;

                                                currentRegion.setInnerDownloadSuffix(suff);
                                                break;
                                            }
                                            case DOWNL_PREF:{
                                                if(!xpp.getAttributeValue(i).equalsIgnoreCase("$name"))
                                                    currentRegion.setDownloadPrefix(xpp.getAttributeValue(i));
                                                else
                                                    currentRegion.setDownloadPrefix(xpp.getAttributeValue(null,NAME));
                                                //Log.d("DPPPPPPPPPPPP",currentRegion.getDownloadPrefix());
                                                break;
                                            }
                                            case IN_DOWNL_PREF:{
                                                if(!xpp.getAttributeValue(i).equalsIgnoreCase("$name")) {
                                                    pref = xpp.getAttributeValue(i);
                                                }else pref = xpp.getAttributeValue(null,NAME);
                                                currentRegion.setInnerDownloadPrefix(pref);
                                                Log.d("ATRIbliat",pref);//xpp.getAttributeValue(i));
                                                break;
                                            }
                                        }
                                        if(currentRegion.getDownloadSuffix()==null)currentRegion.setDownloadSuffix("europe");
                                        //if(currentRegion.getDownloadPrefix()==null)currentRegion.setDownloadPrefix(pref);

                                    }
                                    //mRegions.add(currentRegion);

                            }else
                            if(inEntry){//(xpp.getAttributeValue(null,TRANSLATE)!=null&&xpp.getAttributeValue(null,TRANSLATE).contains("entity="))
                               // ||(xpp.getAttributeValue(null,"hillshade")!=null&&xpp.getAttributeValue(null,"hillshade").contains("no"))) {
                                inInner =true;
                                suff = currentRegion.getInnerDownloadSuffix();
                                pref = currentRegion.getInnerDownloadPrefix();
                                Region childRegion = new Region();

                                childRegion.setDownloadSuffix(currentRegion.getInnerDownloadSuffix());
                                childRegion.setDownloadPrefix(currentRegion.getInnerDownloadPrefix());

                                childRegion.setName(xpp.getAttributeValue(null,NAME));
                                if (xpp.getAttributeValue(null,TYPE)!=null){
                                    String type =xpp.getAttributeValue(null,TYPE);
                                    childRegion.setType(type);
                                    switch (type){
                                        case "map":
                                            childRegion.setMap(true);
                                            childRegion.setSrtm(false);
                                            childRegion.setHillshade(false);
                                            break;
                                        case "hillshade":
                                            childRegion.setMap(false);
                                            childRegion.setSrtm(false);
                                            childRegion.setHillshade(true);
                                            break;
                                        case "continent":
                                            childRegion.setMap(false);
                                            childRegion.setSrtm(false);
                                            childRegion.setHillshade(false);
                                            break;
                                        case "srtm":
                                            childRegion.setMap(false);
                                            childRegion.setSrtm(true);
                                            childRegion.setHillshade(false);
                                            break;


                                    }
                                }
                                if (xpp.getAttributeValue(null,TRANSLATE)!=null)
                                    childRegion.setType(xpp.getAttributeValue(null,TRANSLATE));
                                if(xpp.getAttributeValue(null,"wiki")!=null)
                                    childRegion.setWiki(xpp.getAttributeValue(null,"wiki").equalsIgnoreCase("yes"));
                                if(xpp.getAttributeValue(null,"roads")!=null)
                                    childRegion.setRoads(xpp.getAttributeValue(null,"roads").equalsIgnoreCase("yes"));
                                childs.add(childRegion);

                                if((xpp.getAttributeValue(null,DOWNL_SUFF)!=null)
                                    && !xpp.getAttributeValue(null,DOWNL_SUFF).equalsIgnoreCase("$name"))
                                    suff = xpp.getAttributeValue(null,DOWNL_SUFF);
                                    //currentRegion.getName();

                                childRegion.setDownloadSuffix(suff);

                                if(xpp.getAttributeValue(null,DOWNL_PREF)!=null
                                        && !xpp.getAttributeValue(null,DOWNL_PREF).equalsIgnoreCase("$name"))
                                    pref = xpp.getAttributeValue(null,DOWNL_PREF);

                                childRegion.setDownloadPrefix(pref);

                                inEntry =false;
                            }
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:

                        if(inEntry){
                            if (tagName.equalsIgnoreCase(REGION)) {
                                currentRegion.setChildRegions(childs);
                                mRegions.add(currentRegion);
                                pref =null;
                                suff = "europe";
                                inEntry = false;
                                inInner =false;
                            }
                        }
                        if(inInner){
                            currentRegion.setChildRegions(childs);
                            //inInner =false;
                            inEntry =true;
                        }
                        break;
                    default:
                }
                eventType = xpp.next();
            }

        } catch (XmlPullParserException | IOException e) {
            status =false;
            e.printStackTrace();
        }
        return status;
    }



}
