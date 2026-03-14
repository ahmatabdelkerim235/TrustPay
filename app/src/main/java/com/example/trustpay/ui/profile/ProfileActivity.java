package com.example.trustpay.ui.profile;

import android.os.Bundle;
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

        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String mobile = getIntent().getStringExtra("mobile");
        String upi = getIntent().getStringExtra("upi_id");

        tvName.setText("Name: " + name);
        tvEmail.setText("Email: " + email);
        tvMobile.setText("Mobile: " + mobile);
        tvUpi.setText("UPI ID: " + upi);
    }
}