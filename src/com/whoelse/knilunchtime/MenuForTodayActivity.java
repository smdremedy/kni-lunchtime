package com.whoelse.knilunchtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.whoelse.knilunchtime.model.HomeResponse;
import com.whoelse.knilunchtime.model.Item;

import org.json.JSONObject;


public class MenuForTodayActivity extends Activity implements AdapterView.OnItemClickListener {

    static final int LOGIN_REQUEST = 0;
    static final int ORDER_REQUEST = 1;

    private AQuery mAQ;
    private MenuArrayAdapter mItemArrayAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mListView = (ListView) findViewById(R.id.items_lv);

        mAQ = new AQuery(this);
        mItemArrayAdapter = new MenuArrayAdapter(this, R.layout.item_on_list);
        mListView.setAdapter(mItemArrayAdapter);
        mListView.setOnItemClickListener(this);

        if (! loadSuppliers()) {
            startActivityForResult(new Intent(this, LoginActivity.class), LOGIN_REQUEST);
        }

    }

    public void itemsCallback(String url, JSONObject json, AjaxStatus status) {
        if (json != null) {
            HomeResponse homeResponse = HomeResponse.fromJsonObject(json);
            mItemArrayAdapter.setSuppliers(homeResponse.suppliers);
        } else {
            //HANDLE RELOGIN
        }

    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == LOGIN_REQUEST || requestCode == ORDER_REQUEST) {
                //user was logged or order changed get data from server
                loadSuppliers();
            }
        }

    }

    private boolean loadSuppliers() {
        String cookieValue = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Constants.COOKIE_VALUE_PREFS_KEY, "");
        if (!TextUtils.isEmpty(cookieValue)) {

            String url = Constants.SUPPLIERS_URL;
            AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
            cb.url(url).type(JSONObject.class).weakHandler(this, "itemsCallback");
            cb.cookie(Constants.LUNCHTIME_SESSION_API_KEY, cookieValue);
            mAQ.ajax(cb);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Item item = mItemArrayAdapter.getItem(i);
        Intent intent = new Intent(this, ItemDetailsActivity.class);
        intent.putExtra(Constants.ITEM_BUNDLE_KEY, item);
        startActivityForResult(intent, ORDER_REQUEST);
    }



}