package com.whoelse.knilunchtime.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Option implements Serializable {
    @SerializedName("option_id")
    public int optionId;
    public String name;
}
