package com.example.datve.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datve.R;
import com.example.datve.data.DatabaseSeeder;
import com.example.datve.util.SessionManager;

public class MainActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private TextView tvUserStatus;
    private Button btnLoginLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseSeeder.seedIfNeeded(this);
        sessionManager = new SessionManager(this);

        tvUserStatus = findViewById(R.id.tvUserStatus);
        btnLoginLogout = findViewById(R.id.btnLoginLogout);
        Button btnMovieList = findViewById(R.id.btnMovieList);
        Button btnTheaterList = findViewById(R.id.btnTheaterList);

        btnLoginLogout.setOnClickListener(v -> {
            if (sessionManager.isLoggedIn()) {
                sessionManager.logout();
                updateLoginState();
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });

        btnMovieList.setOnClickListener(v -> startActivity(new Intent(this, MovieListActivity.class)));
        btnTheaterList.setOnClickListener(v -> startActivity(new Intent(this, TheaterListActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLoginState();
    }

    private void updateLoginState() {
        if (sessionManager.isLoggedIn()) {
            tvUserStatus.setText("Đang đăng nhập: " + sessionManager.getUsername());
            btnLoginLogout.setText("Đăng xuất");
        } else {
            tvUserStatus.setText("Bạn chưa đăng nhập");
            btnLoginLogout.setText("Đăng nhập");
        }
    }
}
