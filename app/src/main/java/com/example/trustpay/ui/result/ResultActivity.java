package com.example.trustpay.ui.result;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trustpay.R;
import com.example.trustpay.ui.dashboard.DashboardActivity;
import com.google.android.material.button.MaterialButton;

public class ResultActivity extends AppCompatActivity {

    MaterialButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        btnBack = findViewById(R.id.btnBackDashboard);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
