package com.whoelse.knilunchtime;


public class Constants {
    public static final String ITEM_BUNDLE_KEY = "item";
    public static final String COOKIE_VALUE_PREFS_KEY = "cookie_value";
    public static final String USER_ID_PREFS_KEY = "user_id";

    public static final String EMAIL_API_KEY = "email";
    public static final String LUNCHTIME_SESSION_API_KEY = "_lunchtime_session";
    public static final String PASSWORD_API_KEY = "password";
    public static final String STATUS_API_KEY = "status";
    public static final String ITEM_ID_API_KEY = "item_id";
    public static final String USER_ID_API_KEY = "user_id";


    public static final String MESSAGE_API_KEY = "message";
    public static final String LUNCHTIME_URL = "https://kni-lunchtime.herokuapp.com";
    public static final String LOGIN_URL = LUNCHTIME_URL + "/login?format=json";
    public static final String SUPPLIERS_URL = LUNCHTIME_URL + "/home/index?format=json";
    public static final String CANCEL_ORDER_URL = LUNCHTIME_URL + "/orders/%d?format=json";
    public static final String PLACE_ORDER_URL = LUNCHTIME_URL + "/orders?format=json";
}
