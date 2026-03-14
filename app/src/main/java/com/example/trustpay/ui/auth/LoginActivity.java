package com.example.trustpay.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trustpay.R;
import com.example.trustpay.ui.dashboard.DashboardActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etEmail, etPassword;
    MaterialButton btnLogin;
    TextView tvRegister;

    String LOGIN_URL = "http://10.117.214.76:5000/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Login button
        btnLogin.setOnClickListener(v -> loginUser());

        // Register text click
        tvRegister.setOnClickListener(v -> {

            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);

        });
    }

    private void loginUser() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        try {

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email);
            jsonBody.put("password", password);

            RequestQueue queue = Volley.newRequestQueue(this);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    LOGIN_URL,
                    jsonBody,

                    response -> {

                        Toast.makeText(this,
                                "Login Successful",
                                Toast.LENGTH_SHORT).show();

                        String name = response.optString("name");
                        String userEmail = response.optString("email");
                        String mobile = response.optString("mobile");
                        String upi = response.optString("upi_id");

                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);

                        intent.putExtra("name", name);
                        intent.putExtra("email", userEmail);
                        intent.putExtra("mobile", mobile);
                        intent.putExtra("upi_id", upi);

                        startActivity(intent);
                        finish();

                    },

                    error -> {

                        Toast.makeText(this,
                                "Login Failed",
                                Toast.LENGTH_SHORT).show();

                    });

            queue.add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}