package com.whoelse.knilunchtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements View.OnClickListener
{
    private Button mLoginButton;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginButton = (Button) findViewById(R.id.login_btn);
        mEmailEditText = (EditText) findViewById(R.id.email_et);
        mPasswordEditText = (EditText) findViewById(R.id.password_et);

        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        if(isLoginValid(email, password)) {
            Intent intent = new Intent(this, MenuForTodayActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Incorrect or missing data", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean isLoginValid(String email, String password) {
        return !TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(password) && password.equals("haslo");
    }
}
