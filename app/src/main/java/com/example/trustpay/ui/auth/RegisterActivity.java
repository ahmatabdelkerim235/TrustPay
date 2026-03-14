package com.example.trustpay.ui.auth;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trustpay.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etName, etEmail, etMobile, etPassword, etUpiPin;
    MaterialButton btnRegister;

    String BASE_URL = "http://10.117.214.76:5000/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        etUpiPin = findViewById(R.id.etUpiPin);

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {

        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String mobile = etMobile.getText().toString();
        String password = etPassword.getText().toString();
        String upiPin = etUpiPin.getText().toString();

        if(upiPin.length() != 4){
            Toast.makeText(this, "UPI PIN must be 4 digits", Toast.LENGTH_SHORT).show();
            return;
        }

        try {

            JSONObject jsonBody = new JSONObject();

            jsonBody.put("name", name);
            jsonBody.put("email", email);
            jsonBody.put("mobile", mobile);
            jsonBody.put("password", password);
            jsonBody.put("upi_pin", upiPin);

            RequestQueue queue = Volley.newRequestQueue(this);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    BASE_URL,
                    jsonBody,
                    response -> {

                        Toast.makeText(this,
                                "Registered Successfully\nUPI: " + response.optString("upi_id"),
                                Toast.LENGTH_LONG).show();

                    },
                    error -> {

                        Toast.makeText(this,
                                "Registration Failed",
                                Toast.LENGTH_SHORT).show();

                    });

            queue.add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}