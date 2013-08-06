package com.whoelse.knilunchtime;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.whoelse.knilunchtime.model.Item;
import com.whoelse.knilunchtime.model.Option;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ItemDetailsActivity extends Activity {
    private Item mItem;
    private AQuery mAQ;
    private boolean mOrdered;
    private RadioGroup mOptionsRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        mAQ = new AQuery(this);
        mItem = (Item) getIntent().getSerializableExtra(Constants.ITEM_BUNDLE_KEY);

        mAQ.id(R.id.details_image_iv).image(mItem.image, false, true, 600, 0, null, com.androidquery.util.Constants.FADE_IN);
        if(mItem.supplier != null) {
            mAQ.id(R.id.details_item_supplier_name_tv).text(mItem.supplier.name);
        }
        mAQ.id(R.id.details_cancel_btn).clicked(this, "cancelClicked");
        mOrdered = (mItem.order != null);
        mAQ.id(R.id.details_confirm_btn).text(mOrdered ? R.string.cancel_order : R.string.order).clicked(this, "confirmClicked");
        mAQ.id(R.id.details_item_name_tv).text(mItem.name);
        mAQ.id(R.id.details_item_price_tv).text(getString(R.string.price_for_details,
                String.valueOf(new DecimalFormat("#0.##").format(mItem.price))));
        mAQ.id(R.id.details_options_ll).visibility(mItem.options.length > 0 ? View.VISIBLE : View.INVISIBLE);
        boolean markThis = mItem.order == null;
        for (Option option : mItem.options) {
            mOptionsRadioGroup = (RadioGroup) findViewById(R.id.details_options_rg);
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option.name);
            radioButton.setTag("options[" + option.optionId + "]");
            radioButton.setId(option.optionId);
            radioButton.setEnabled(!mOrdered);
            if(mOrdered && mItem.order.optionId != null && mItem.order.optionId == option.optionId) {
                markThis = true;
            }

            radioButton.setChecked(markThis);
            if (markThis) {

                markThis = false;
            }
            mOptionsRadioGroup.addView(radioButton);
        }

    }

    public void cancelClicked(View button) {
        setResult(RESULT_CANCELED);
        finish();

    }

    public void confirmClicked(View button) {
        if (mOrdered) {
            cancelOrder(mItem.order.orderId);
        } else {
            placeOrder(mItem.itemId);
        }

    }

    private void cancelOrder(int orderId) {
        String cookieValue = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Constants.COOKIE_VALUE_PREFS_KEY, "");
        if (!TextUtils.isEmpty(cookieValue)) {

            String url = Constants.CANCEL_ORDER_URL;
            AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
            cb.weakHandler(this, "deleteOrderCallback");
            cb.cookie(Constants.LUNCHTIME_SESSION_API_KEY, cookieValue);
            mAQ.delete(String.format(url, orderId), JSONObject.class, cb);
        } else {
            Toast.makeText(this, R.string.not_logged, Toast.LENGTH_SHORT).show();
        }
    }

    private void placeOrder(int itemId) {
        String cookieValue = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Constants.COOKIE_VALUE_PREFS_KEY, "");
        int userId = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt(Constants.USER_ID_PREFS_KEY, 0);
        if (!TextUtils.isEmpty(cookieValue)) {

            String url = Constants.PLACE_ORDER_URL;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(Constants.ITEM_ID_API_KEY, itemId);
            params.put(Constants.USER_ID_API_KEY, userId);
            if(mOptionsRadioGroup != null) {
                params.put(String.valueOf(mAQ.id(mOptionsRadioGroup.getCheckedRadioButtonId()).getTag()),
                        1);
            }
            AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
            cb.url(url).type(JSONObject.class).weakHandler(this, "placeOrderCallback").params(params);
            cb.cookie(Constants.LUNCHTIME_SESSION_API_KEY, cookieValue);
            mAQ.ajax(cb);
        } else {
            Toast.makeText(this, R.string.not_logged, Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteOrderCallback(String url, JSONObject json, AjaxStatus status) {
        Toast.makeText(this, String.valueOf(json), Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();

    }

    public void placeOrderCallback(String url, JSONObject json, AjaxStatus status) {
        Toast.makeText(this, String.valueOf(json), Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actionbar_back_ibtn:
                onBackPressed();
                return;
        }
    }
}