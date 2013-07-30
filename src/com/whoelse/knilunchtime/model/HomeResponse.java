package com.whoelse.knilunchtime.model;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

public class HomeResponse implements Serializable {

    public Supplier[] suppliers;

    public static HomeResponse fromJsonObject(JSONObject jsonObject) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject.toString(), HomeResponse.class);
    }
}
