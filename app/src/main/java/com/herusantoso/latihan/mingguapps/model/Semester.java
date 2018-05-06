package com.herusantoso.latihan.mingguapps.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by santoso on 5/6/18.
 */

public class Semester {
    @SerializedName("semester")
    private String semester;

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
