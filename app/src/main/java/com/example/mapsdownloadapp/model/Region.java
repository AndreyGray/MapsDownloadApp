package com.example.mapsdownloadapp.model;

import java.util.ArrayList;

import static com.example.mapsdownloadapp.RegionsResourceParser.CONTINENT;
import static com.example.mapsdownloadapp.RegionsResourceParser.MAP;
import static com.example.mapsdownloadapp.RegionsResourceParser.SRTM;

public class Region {

    private String name = null;
    private String boundary = null;

    private String type = null;
    private boolean map = true;
    private boolean wiki = true;
    private boolean roads = true;
    private boolean srtm = true;
    private boolean hillshade = true;

    private String translate = null;

    private String innerDownloadSuffix = null;
    private String downloadSuffix = null;
    private String downloadPrefix = null;
    private String innerDownloadPrefix = null;
    private String downloadName = null;
    private boolean isMapDownloaded = false;

    private ArrayList<Region> childRegions = new ArrayList<>();

    public Region() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoundary() {
        return boundary;
    }

    public void setBoundary(String boundary) {
        this.boundary = boundary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
//        type=srtm: map=no, srtm=yes, hillshade=no
//        type=hillshade: map=no, srtm=no, hillshade=yes
//        type=continent: map=no, srtm=no, hillshade=no
        switch (type){
            case SRTM:
                setMap(false);
                setSrtm(true);
                setHillshade(false);
                break;
            case MAP:
                setMap(true);
                setSrtm(false);
                setHillshade(false);
                break;
            case CONTINENT:
                setMap(false);
                setSrtm(false);
                setHillshade(false);
                break;
        }
    }

    public boolean isMap() {
        return map;
    }

    public void setMap(boolean map) {
        this.map = map;
        this.wiki = map;
        this.roads = map;
    }

    public boolean isWiki() {
        return wiki;
    }

    public void setWiki(boolean wiki) {
        this.wiki = wiki;
    }

    public boolean isRoads() {
        return roads;
    }

    public void setRoads(boolean roads) {
        this.roads = roads;
    }

    public boolean isSrtm() {
        return srtm;
    }

    public void setSrtm(boolean srtm) {
        this.srtm = srtm;
    }

    public boolean isHillshade() {
        return hillshade;
    }

    public void setHillshade(boolean hillshade) {
        this.hillshade = hillshade;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getInnerDownloadSuffix() {
        return innerDownloadSuffix;
    }

    public void setInnerDownloadSuffix(String innerDownloadSuffix) {
        this.innerDownloadSuffix = innerDownloadSuffix;
    }

    public String getDownloadSuffix() {
        return downloadSuffix;
    }

    public void setDownloadSuffix(String downloadSuffix) {
        this.downloadSuffix = downloadSuffix;
    }

    public String getDownloadPrefix() {
        return downloadPrefix;
    }

    public void setDownloadPrefix(String downloadPrefix) {
        this.downloadPrefix = downloadPrefix;
    }

    public String getInnerDownloadPrefix() {
        return innerDownloadPrefix;
    }

    public void setInnerDownloadPrefix(String innerDownloadPrefix) {
        this.innerDownloadPrefix = innerDownloadPrefix;
    }
//    download_suffix, inner_download_suffix, download_prefix, inner_download_prefix
//			1. In case download_suffix, download_prefix specified, then
//    download_name = download_prefix + "_" + name + "_" + download_suffix
//    In case only download_suffix speicfied
//            download_name =  name + "_" + download_suffix
//    In case only download_prefix speicfied
//            download_name =  download_prefix + "_" + name
//			2. If download_suffix is not specified it is taken from parent region or from parent parent region ...
//            2.1 If parent region has inner_download_suffix, then download_suffix=inner_download_suffix
//




    public String getDownloadName() {

            String downName = name.substring(0, 1).toUpperCase() + name.substring(1);
            if (this.downloadPrefix != null)
                downName = downloadPrefix.substring(0, 1).toUpperCase() + downloadPrefix.substring(1)
                        + "_" + this.name;
            if (this.downloadSuffix!= null&&downloadSuffix.length()!=0)
                downName = downName + "_" + downloadSuffix;
            else downName =downName+ "_" +"europe";
            downName = downName + "_2.obf.zip";
            setDownloadName(downName);


            return downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    public ArrayList<Region> getChildRegions() {
        return childRegions;
    }

    public void setChildRegions(ArrayList<Region> childRegions) {
        this.childRegions = childRegions;
    }

    public boolean isMapDownloaded() {
        return isMapDownloaded;
    }

    public void setMapDownloaded(boolean mapDownloaded) {
        isMapDownloaded = mapDownloaded;
    }
}
