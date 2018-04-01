package com.herusantoso.latihan.mingguapps.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by santoso on 3/31/18.
 */

public class ResultMessage {
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private Object result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
