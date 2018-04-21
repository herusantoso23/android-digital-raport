package com.herusantoso.latihan.mingguapps.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by santoso on 4/12/18.
 */

public class Lecturer {
    @SerializedName("id")
    private Integer id;
    @SerializedName("nip")
    private String nip;
    @SerializedName("nama")
    private String name;
    @SerializedName("mapel")
    private String mapel;
    @SerializedName("no_hp")
    private String phone;
    @SerializedName("alamat")
    private String address;
    @SerializedName("photo")
    private String photo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMapel() {
        return mapel;
    }

    public void setMapel(String mapel) {
        this.mapel = mapel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
