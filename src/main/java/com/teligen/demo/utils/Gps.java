package com.teligen.demo.utils;

public class Gps {

    private double wgLat;
    private double wgLon;

    private String longitude;
    private String latitude;

    public Gps(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Gps(double wgLat, double wgLon) {
        setWgLat(wgLat);
        setWgLon(wgLon);
    }

    public double getWgLat() {
        return wgLat;
    }

    public void setWgLat(double wgLat) {
        this.wgLat = wgLat;
    }

    public double getWgLon() {
        return wgLon;
    }

    public void setWgLon(double wgLon) {
        this.wgLon = wgLon;
    }

    @Override
    public String toString() {
        return wgLat + "," + wgLon;
    }

    public static Gps changeToStandard(String longitude, String latitude, String gpsType) {
        Gps gps = null;
//        if (PositionUtils.APP_GPS_TYPE == 2) {
//            gps = PositionUtils.bd09_To_Gps84(longitude, latitude, gpsType);
//        } else {
//            gps = PositionUtils.gcj_To_Gps84(CommonUtils.safeToDouble(latitude, 0), CommonUtils.safeToDouble(longitude, 0));
//            gps.setLongitude(StrUtil.emptyIfNull(gps.getWgLon()));
//            gps.setLatitude(StrUtil.emptyIfNull(gps.getWgLat()));
//        }
        return gps;
    }
}

