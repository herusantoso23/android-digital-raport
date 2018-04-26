package com.herusantoso.latihan.mingguapps.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by santoso on 4/26/18.
 */

public class Schedule {

    @SerializedName("nama_kelas")
    private String className;
    @SerializedName("jurusan")
    private String jurusan;
    @SerializedName("kode_mapel")
    private String mapelCode;
    @SerializedName("nama_mapel")
    private String mapelName;
    @SerializedName("waktu")
    private String time;
    @SerializedName("guru")
    private String teacher;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getMapelCode() {
        return mapelCode;
    }

    public void setMapelCode(String mapelCode) {
        this.mapelCode = mapelCode;
    }

    public String getMapelName() {
        return mapelName;
    }

    public void setMapelName(String mapelName) {
        this.mapelName = mapelName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
