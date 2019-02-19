package com.elis.DepEat.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.elis.DepEat.R;
import com.elis.DepEat.backend.SharedPreferencesSettings;

import org.w3c.dom.Text;

public class AccountActivity extends AppCompatActivity {

    TextView emailTv;
    TextView phoneNumberTv;
    Button logoutBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);

        emailTv = findViewById(R.id.email_tv);
        phoneNumberTv = findViewById(R.id.phonenumber_tv);
        logoutBtn = findViewById(R.id.logout_btn);

        emailTv.setText(SharedPreferencesSettings.getStringFromPreferences(this,"email"));
        phoneNumberTv.setText(SharedPreferencesSettings.getStringFromPreferences(this, "phoneNumber"));

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesSettings.setSharedPreferences(AccountActivity.this,"loggedIn",false);
                SharedPreferencesSettings.setSharedPreferences(AccountActivity.this, "jwt", "");
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
