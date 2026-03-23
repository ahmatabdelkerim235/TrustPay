package com.example.trustpay.ui.profile;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trustpay.R;

public class ProfileActivity extends AppCompatActivity {

    TextView tvName, tvEmail, tvMobile, tvUpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvMobile = findViewById(R.id.tvMobile);
        tvUpi = findViewById(R.id.tvUpi);

        SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);

        String name = prefs.getString("name", "");
        String email = prefs.getString("email", "");
        String mobile = prefs.getString("mobile", "");
        String upi = prefs.getString("upi", "");

        tvName.setText("Name: " + name);
        tvEmail.setText("Email: " + email);
        tvMobile.setText("Mobile: " + mobile);
        tvUpi.setText("UPI ID: " + upi);
    }
}