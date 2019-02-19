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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.elis.DepEat.R;
import com.elis.DepEat.backend.SharedPreferencesSettings;
import com.elis.DepEat.services.RestController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, Response.Listener<String>, Response.ErrorListener {

    private EditText emailEt;
    private EditText passwordEt;
    private TextView errorMsgTv;
    private Button loginBtn;
    private Button registerBtn;

    boolean hasAccount = false;

    private final String LOGIN_END_POINT = "auth/local";

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

            RestController restController = new RestController(this);
            restController.postRequest(LOGIN_END_POINT, this, this, getParams());

    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.login_btn){
            doLogin();
        } else {
            Intent intent = new Intent(this, RegistrationActivity.class);
            intent.putExtra("email", emailEt.getText().toString());
            startActivity(intent);
            finish();
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
        SharedPreferencesSettings.setSharedPreferences(LoginActivity.this, "loggedIn", false);
    }

    @Override
    public void onResponse(String response) {
        try {
            SharedPreferencesSettings.setSharedPreferences(this,"jwt", new JSONObject(response).getString("jwt"));
        } catch (JSONException e) {
            Log.e("jwtError","Errore nel salvataggio del jwt");
        }
        SharedPreferencesSettings.setSharedPreferences(LoginActivity.this, "loggedIn", true);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        Toast.makeText(this,
                "Login effettuato con successo", Toast.LENGTH_LONG).show();
        finish();
    }

    protected Map<String, String> getParams()
    {
        Map<String, String> params = new HashMap<>();
        params.put("identifier", emailEt.getText().toString());
        params.put("password", passwordEt.getText().toString());

        return params;
    }
}
