package com.example.datve.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datve.R;
import com.example.datve.data.AppDatabase;
import com.example.datve.data.entity.User;
import com.example.datve.util.SessionManager;

public class LoginActivity extends AppCompatActivity {
    public static final String EXTRA_REDIRECT_SHOWTIME_ID = "extra_redirect_showtime_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText edtUsername = findViewById(R.id.edtUsername);
        EditText edtPassword = findViewById(R.id.edtPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = AppDatabase.getInstance(this).userDao().login(username, password);
            if (user == null) {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            SessionManager sessionManager = new SessionManager(this);
            sessionManager.saveLogin(user.id, user.username);
            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

            int showTimeId = getIntent().getIntExtra(EXTRA_REDIRECT_SHOWTIME_ID, -1);
            if (showTimeId > 0) {
                Intent intent = new Intent(this, SeatSelectionActivity.class);
                intent.putExtra(SeatSelectionActivity.EXTRA_SHOWTIME_ID, showTimeId);
                startActivity(intent);
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }
            finish();
        });
    }
}
