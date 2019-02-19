package com.elis.DepEat.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ClientError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.elis.DepEat.R;
import com.elis.DepEat.backend.SharedPreferencesSettings;
import com.elis.DepEat.backend.Utils;
import com.elis.DepEat.services.RestController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener  {

    private EditText emailEt;
    private EditText passwordEt;
    private EditText confirmPwdEt;
    private EditText phoneNumberEt;
    private TextView errorMsgTv;
    private Button registerBtn;

    private final String REGISTER_END_POINT = "auth/local/register";

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
        /*SharedPreferencesSettings.setSharedPreferences(this, emailEt.getText().toString(),passwordEt.getText().toString());
        SharedPreferencesSettings.setSharedPreferences(this, emailEt.getText().toString()+"phone",phoneNumberEt.getText().toString());
        SharedPreferencesSettings.setSharedPreferences(this, "Account", true);*/
        RestController restController = new RestController(this);
        restController.postRequest(REGISTER_END_POINT, this, this, getParams() );

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        JSONObject object = null;
        try {
            String body =new String(error.networkResponse.data, "UTF-8");
            object = new JSONObject(body);
            Toast.makeText(this,
                    object.getString("message"), Toast.LENGTH_LONG).show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(String response) {
        try {
            SharedPreferencesSettings.setSharedPreferences(this,"jwt", new JSONObject(response).getString("jwt"));
        } catch (JSONException e) {
            Log.e("jwtError","Errore nel salvataggio del jwt");
        }
        Toast.makeText(this,
                "Registrazione effettuata con successo", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    protected Map<String, String> getParams()
    {
        Map<String, String> params = new HashMap<>();
        params.put("username", phoneNumberEt.getText().toString());
        params.put("email", emailEt.getText().toString());
        params.put("password", passwordEt.getText().toString());

        return params;
    }
}
