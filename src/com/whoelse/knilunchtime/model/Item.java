package com.whoelse.knilunchtime.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

public class Item implements Serializable {

    @SerializedName("item_id")
    public int itemId;
    public String name;
    public BigDecimal price;
    public String date;
    public Order order;
    public Option[] options;

    public transient Supplier supplier;

}
