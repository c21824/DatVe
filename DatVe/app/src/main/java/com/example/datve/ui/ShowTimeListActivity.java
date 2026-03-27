package com.example.datve.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datve.R;
import com.example.datve.data.AppDatabase;
import com.example.datve.data.entity.ShowTime;
import com.example.datve.ui.adapter.ShowTimeAdapter;
import com.example.datve.util.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ShowTimeListActivity extends AppCompatActivity {
    public static final String EXTRA_FILTER_TYPE = "extra_filter_type";
    public static final String EXTRA_FILTER_ID = "extra_filter_id";
    public static final String FILTER_MOVIE = "movie";
    public static final String FILTER_THEATER = "theater";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtime_list);

        TextView tvTitle = findViewById(R.id.tvShowTimeTitle);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewShowTimes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String filterType = getIntent().getStringExtra(EXTRA_FILTER_TYPE);
        int filterId = getIntent().getIntExtra(EXTRA_FILTER_ID, -1);

        List<ShowTime> showTimes = new ArrayList<>();
        if (FILTER_MOVIE.equals(filterType)) {
            tvTitle.setText("Lịch chiếu theo phim");
            showTimes = AppDatabase.getInstance(this).showTimeDao().getByMovieId(filterId);
        } else if (FILTER_THEATER.equals(filterType)) {
            tvTitle.setText("Lịch chiếu theo rạp");
            showTimes = AppDatabase.getInstance(this).showTimeDao().getByTheaterId(filterId);
        }

        ShowTimeAdapter adapter = new ShowTimeAdapter(showTimes, showTime -> {
            SessionManager sessionManager = new SessionManager(this);
            if (!sessionManager.isLoggedIn()) {
                Toast.makeText(this, "Vui lòng đăng nhập để đặt vé", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(this, LoginActivity.class);
                loginIntent.putExtra(LoginActivity.EXTRA_REDIRECT_SHOWTIME_ID, showTime.id);
                startActivity(loginIntent);
                return;
            }

            Intent seatIntent = new Intent(this, SeatSelectionActivity.class);
            seatIntent.putExtra(SeatSelectionActivity.EXTRA_SHOWTIME_ID, showTime.id);
            startActivity(seatIntent);
        });
        recyclerView.setAdapter(adapter);
    }
}
