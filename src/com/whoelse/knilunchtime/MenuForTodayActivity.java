package com.whoelse.knilunchtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.whoelse.knilunchtime.model.HomeResponse;
import com.whoelse.knilunchtime.model.Item;
import com.whoelse.knilunchtime.model.Supplier;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MenuForTodayActivity extends Activity implements AdapterView.OnItemClickListener {

    public static final String TAG = "LUNCHTIME";
    static final int ORDER_REQUEST = 1;

    private ListView mListView;
    private MenuArrayAdapter mItemArrayAdapter;
    private HomeResponse mResponse;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mListView = (ListView) findViewById(R.id.items_lv);
        mItemArrayAdapter = new MenuArrayAdapter(this, R.layout.item_on_list);
        mListView.setAdapter(mItemArrayAdapter);
        mListView.setOnItemClickListener(this);

        try {
            JSONObject jsonObject = new JSONObject(readTextFromRawResource(R.raw.meals));
            mResponse = HomeResponse.fromJsonObject(jsonObject);
            fillListWithItems();
        } catch (JSONException e) {
            Log.e(TAG, "Error while parsing json", e);
        }


    }

    private void fillListWithItems() {

        mItemArrayAdapter.setSuppliers(mResponse.suppliers);


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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Item item = mItemArrayAdapter.getItem(position);
        Intent intent = new Intent(this, ItemDetailsActivity.class);

        intent.putExtra(Constants.ITEM_BUNDLE_KEY, item);
        startActivityForResult(intent, ORDER_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ORDER_REQUEST) {

                fillListWithItems();
            }
        }

    }
}