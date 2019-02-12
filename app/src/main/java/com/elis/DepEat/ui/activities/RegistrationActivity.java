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
import com.elis.DepEat.backend.Utils;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEt;
    private EditText passwordEt;
    private EditText confirmPwdEt;
    private EditText phoneNumberEt;
    private TextView errorMsgTv;
    private Button registerBtn;

    private final static Pattern EMAILPAT = Pattern.compile("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");   //Era possibile farlo con il pattern di android: android.pattern.etc
    private final static Pattern PHONEPAT = Pattern.compile("^[+][0-9]{1,4}[\\s]{1}[/0-9]*$");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);

        emailEt = findViewById(R.id.email_et);
        passwordEt = findViewById(R.id.password_et);
        confirmPwdEt = findViewById(R.id.confirmpwd_et);
        phoneNumberEt = findViewById(R.id.phonenumber_et);
        errorMsgTv = findViewById(R.id.errormsg_tv);
        registerBtn = findViewById(R.id.register_btn);

        emailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerBtn.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Utils.checkEmailFormat(emailEt.getText().toString())){
                    errorMsgTv.setText(R.string.emailformat_error);
                    errorMsgTv.setVisibility(View.VISIBLE);
                } else {
                    errorMsgTv.setVisibility(View.GONE);
                    if(Utils.checkButtonAvailability()) registerBtn.setEnabled(true);
                }
            }
        });

        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerBtn.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Utils.checkPasswordFormat(passwordEt.getText().toString())){
                    errorMsgTv.setText(R.string.passwordlength_error);
                    errorMsgTv.setVisibility(View.VISIBLE);
                } else {
                    errorMsgTv.setVisibility(View.GONE);
                    if(!Utils.checkPasswordConfirmation(passwordEt.getText().toString(), confirmPwdEt.getText().toString())){
                        errorMsgTv.setText(R.string.passwordequals_error);
                        errorMsgTv.setVisibility(View.VISIBLE);
                    }else if(Utils.checkButtonAvailability()) {
                        errorMsgTv.setVisibility(View.GONE);
                        registerBtn.setEnabled(true);
                    }
                }
            }
        });

        confirmPwdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerBtn.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Utils.checkPasswordConfirmation(confirmPwdEt.getText().toString(), passwordEt.getText().toString())){
                    errorMsgTv.setText(R.string.passwordequals_error);
                    errorMsgTv.setVisibility(View.VISIBLE);
                } else {
                    errorMsgTv.setVisibility(View.GONE);
                    if(Utils.checkButtonAvailability()) registerBtn.setEnabled(true);
                }
            }
        });

        phoneNumberEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerBtn.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Utils.checkPhoneNumberFormat(phoneNumberEt.getText().toString())){
                    errorMsgTv.setText(R.string.phoneformat_error);
                    errorMsgTv.setVisibility(View.VISIBLE);
                } else if(!Utils.checkPhoneNumberLenght(phoneNumberEt.getText().toString())){
                        errorMsgTv.setText(R.string.phonelength_error);
                        errorMsgTv.setVisibility(View.VISIBLE);
                } else{
                    errorMsgTv.setVisibility(View.GONE);
                    if(Utils.checkButtonAvailability()) registerBtn.setEnabled(true);
                }
            }
        });

        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        SharedPreferencesSettings.setSharedPreferences(this, emailEt.getText().toString(),passwordEt.getText().toString());
        SharedPreferencesSettings.setSharedPreferences(this, emailEt.getText().toString()+"phone",phoneNumberEt.getText().toString());
        SharedPreferencesSettings.setSharedPreferences(this, "Account", true);
        startActivity(intent);
    }
}
