package com.example.datve.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datve.R;
import com.example.datve.data.AppDatabase;
import com.example.datve.data.entity.Theater;
import com.example.datve.ui.adapter.TheaterAdapter;

import java.util.List;

public class TheaterListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewTheaters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Theater> theaters = AppDatabase.getInstance(this).theaterDao().getAll();
        TheaterAdapter adapter = new TheaterAdapter(theaters, theater -> {
            Intent intent = new Intent(this, ShowTimeListActivity.class);
            intent.putExtra(ShowTimeListActivity.EXTRA_FILTER_TYPE, ShowTimeListActivity.FILTER_THEATER);
            intent.putExtra(ShowTimeListActivity.EXTRA_FILTER_ID, theater.id);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }
}
