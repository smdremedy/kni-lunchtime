package com.whoelse.knilunchtime.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Serializable {
    @SerializedName("order_id")
    public int orderId;
    @SerializedName("option_id")
    public java.lang.Integer optionId;

}
