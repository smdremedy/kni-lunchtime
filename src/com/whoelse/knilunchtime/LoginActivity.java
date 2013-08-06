package com.whoelse.knilunchtime;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.apache.http.cookie.Cookie;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity implements View.OnClickListener {

    private AQuery mAQ;
    private Button mLoginButton;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginButton = (Button) findViewById(R.id.login_btn);
        mEmailEditText = (EditText) findViewById(R.id.email_et);
        mPasswordEditText = (EditText) findViewById(R.id.password_et);

        mLoginButton.setOnClickListener(this);
        mAQ = new AQuery(this);
    }

    @Override
    public void onClick(View v) {
        loginClicked(v);

    }

    public void loginClicked(View button) {

        String url = Constants.LOGIN_URL;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.EMAIL_API_KEY, mEmailEditText.getText().toString());
        params.put(Constants.PASSWORD_API_KEY, mPasswordEditText.getText().toString());

        mAQ.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                if (json.optInt(Constants.STATUS_API_KEY, -1) != 200) {
                    Toast.makeText(getApplicationContext(), "Error during login:" + json.optString(Constants.MESSAGE_API_KEY),
                            Toast.LENGTH_LONG).show();
                } else {

                    for (Cookie cookie : status.getCookies()) {

                        if (Constants.LUNCHTIME_SESSION_API_KEY.equals(cookie.getName())) {
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Constants.COOKIE_VALUE_PREFS_KEY, cookie.getValue());
                            editor.putInt(Constants.USER_ID_PREFS_KEY, json.optInt(Constants.USER_ID_API_KEY));

                            editor.commit();
                            setResult(RESULT_OK);
                            finish();

                        }

                    }
                }
            }
        });

    }

}
