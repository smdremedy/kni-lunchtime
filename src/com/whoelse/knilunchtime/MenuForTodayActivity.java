package com.whoelse.knilunchtime;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.whoelse.knilunchtime.model.HomeResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MenuForTodayActivity extends Activity {

    public static final String TAG = "LUNCHTIME";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        try {
            JSONObject jsonObject = new JSONObject(readTextFromRawResource(R.raw.meals));
            HomeResponse response = HomeResponse.fromJsonObject(jsonObject);
        } catch (JSONException e) {
            Log.e(TAG, "Error while parsing json", e);
        }
    }

    private String readTextFromRawResource(int resourceId) {

        InputStream inputStream = getResources().openRawResource(resourceId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "Error while opening resource", e);
        }

        return byteArrayOutputStream.toString();
    }
}