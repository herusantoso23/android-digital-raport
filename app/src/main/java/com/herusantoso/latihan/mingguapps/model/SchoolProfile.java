package com.herusantoso.latihan.mingguapps.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by santoso on 5/20/18.
 */

public class SchoolProfile {
    @SerializedName("id")
    private int id;
    @SerializedName("nama")
    private String name;
    @SerializedName("alamat")
    private String address;
    @SerializedName("telp")
    private String phone;
    @SerializedName("email")
    private String email;
    @SerializedName("fax")
    private String fax;
    @SerializedName("gambar")
    private String picture;
    @SerializedName("jumlah_siswa")
    private String studentCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(String studentCount) {
        this.studentCount = studentCount;
    }
}
