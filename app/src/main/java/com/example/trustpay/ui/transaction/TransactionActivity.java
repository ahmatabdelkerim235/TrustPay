package com.example.trustpay.ui.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trustpay.R;
import com.example.trustpay.ui.verification.PinActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class TransactionActivity extends AppCompatActivity {

    TextInputEditText etReceiver, etAmount;
    MaterialButton btnPay;

    String senderUpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        etReceiver = findViewById(R.id.etReceiver);
        etAmount = findViewById(R.id.etAmount);
        btnPay = findViewById(R.id.btnPay);

        // Receive sender UPI from DashboardActivity
        senderUpi = getIntent().getStringExtra("upi_id");

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String receiverUpi = etReceiver.getText().toString().trim();
                String amount = etAmount.getText().toString().trim();

                if(receiverUpi.isEmpty() || amount.isEmpty()){
                    Toast.makeText(TransactionActivity.this,"Enter all fields",Toast.LENGTH_SHORT).show();
                    return;
                }

                // Open PIN screen instead of calling backend
                Intent intent = new Intent(TransactionActivity.this, PinActivity.class);

                intent.putExtra("sender_upi", senderUpi);
                intent.putExtra("receiver_upi", receiverUpi);
                intent.putExtra("amount", amount);

                startActivity(intent);
            }
        });
    }
}