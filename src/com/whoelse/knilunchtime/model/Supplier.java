package com.whoelse.knilunchtime.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Supplier implements Serializable {
    @SerializedName("supplier_id")
    public int supplierId;
    public Item[] items;
    public String name;
}
