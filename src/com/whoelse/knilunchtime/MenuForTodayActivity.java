package com.whoelse.knilunchtime;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.whoelse.knilunchtime.model.HomeResponse;
import com.whoelse.knilunchtime.model.Item;
import com.whoelse.knilunchtime.model.Supplier;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MenuForTodayActivity extends Activity {

    public static final String TAG = "LUNCHTIME";
    private ListView mListView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mListView = (ListView) findViewById(R.id.items_lv);

        try {
            JSONObject jsonObject = new JSONObject(readTextFromRawResource(R.raw.meals));
            HomeResponse response = HomeResponse.fromJsonObject(jsonObject);
            fillListWithItems(response.suppliers);
        } catch (JSONException e) {
            Log.e(TAG, "Error while parsing json", e);
        }


    }

    private void fillListWithItems(Supplier[] suppliers) {
        List<Item> items = new ArrayList<Item>();
        for (Supplier supplier : suppliers) {
            for (Item item : supplier.items) {
                items.add(item);
                item.supplier = supplier;
            }
        }

        ArrayAdapter<Item> itemArrayAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, items);
        mListView.setAdapter(itemArrayAdapter);
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