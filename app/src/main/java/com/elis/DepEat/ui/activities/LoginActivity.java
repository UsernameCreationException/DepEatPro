package com.elis.DepEat.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.elis.DepEat.R;
import com.elis.DepEat.backend.SharedPreferencesSettings;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText emailEt;
    private EditText passwordEt;
    private TextView errorMsgTv;
    private Button loginBtn;
    private Button registerBtn;

    boolean hasAccount = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        emailEt = findViewById(R.id.email_et);
        passwordEt = findViewById(R.id.password_et);
        errorMsgTv = findViewById(R.id.errormsg_tv);
        loginBtn = findViewById(R.id.login_btn);
        registerBtn = findViewById(R.id.register_btn);

        if(SharedPreferencesSettings.getBooleanFromPreferences(this,"Account")){
            registerBtn.setVisibility(View.GONE);
        }

        emailEt.addTextChangedListener(this);
        passwordEt.addTextChangedListener(this);

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }


    private void doLogin(){
        if(checkUser()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            errorMsgTv.setText(R.string.login_error);
            errorMsgTv.setVisibility(View.VISIBLE);
        }
    }

    private boolean checkUser(){
        if(SharedPreferencesSettings.getStringFromPreferences(this,emailEt.getText().toString()) != null){

            if(SharedPreferencesSettings.getStringFromPreferences(this,emailEt.getText().toString()).equals(passwordEt.getText().toString()))
                return true;
            else
                return false;
        } else
            return false;

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.login_btn){
            doLogin();
        } else {
            Intent intent = new Intent(this, RegistrationActivity.class);
            intent.putExtra("email", emailEt.getText().toString());
            startActivity(intent);
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        errorMsgTv.setVisibility(View.GONE);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
