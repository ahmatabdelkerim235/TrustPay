package com.example.trustpay.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trustpay.R;
import com.example.trustpay.ui.profile.ProfileActivity;
import com.example.trustpay.ui.transaction.TransactionActivity;
import com.example.trustpay.ui.history.HistoryActivity;
import com.google.android.material.card.MaterialCardView;

public class DashboardActivity extends AppCompatActivity {

    MaterialCardView cardNewTransaction, cardHistory;
    ImageView profileIcon;

    String name, email, mobile, upi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        cardNewTransaction = findViewById(R.id.cardNewTransaction);
        cardHistory = findViewById(R.id.cardHistory);
        profileIcon = findViewById(R.id.profileIcon);

        // Receive user details from LoginActivity
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        mobile = getIntent().getStringExtra("mobile");
        upi = getIntent().getStringExtra("upi_id");

        // Open Transaction Screen
        cardNewTransaction.setOnClickListener(v -> {

            Intent intent = new Intent(DashboardActivity.this, TransactionActivity.class);

            // PASS SENDER UPI
            intent.putExtra("upi_id", upi);

            startActivity(intent);
        });

        // Open History Screen
        cardHistory.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        // Open Profile Screen
        profileIcon.setOnClickListener(v -> {

            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);

            intent.putExtra("name", name);
            intent.putExtra("email", email);
            intent.putExtra("mobile", mobile);
            intent.putExtra("upi_id", upi);

            startActivity(intent);
        });
    }
}