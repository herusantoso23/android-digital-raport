package com.herusantoso.latihan.mingguapps.model;

import com.google.gson.annotations.SerializedName;

import static android.R.attr.id;

/**
 * Created by santoso on 3/31/18.
 */

public class StudentUpdate {
    @SerializedName("nis")
    private String nis;
    @SerializedName("kelas")
    private String classLevel;
    @SerializedName("no_hp")
    private String phone;
    @SerializedName("alamat")
    private String address;

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(String classLevel) {
        this.classLevel = classLevel;
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
}
