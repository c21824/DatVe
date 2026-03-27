package com.example.datve.ui;

import android.content.Intent;
import android.os.Bundle;
import android.graphics.Rect;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datve.R;
import com.example.datve.data.AppDatabase;
import com.example.datve.data.entity.Movie;
import com.example.datve.ui.adapter.MovieAdapter;

import java.util.List;

public class MovieListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(12));

        List<Movie> movies = AppDatabase.getInstance(this).movieDao().getAll();
        MovieAdapter adapter = new MovieAdapter(movies, movie -> {
            Intent intent = new Intent(this, ShowTimeListActivity.class);
            intent.putExtra(ShowTimeListActivity.EXTRA_FILTER_TYPE, ShowTimeListActivity.FILTER_MOVIE);
            intent.putExtra(ShowTimeListActivity.EXTRA_FILTER_ID, movie.id);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    private static class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int spaceDp;

        VerticalSpaceItemDecoration(int spaceDp) {
            this.spaceDp = spaceDp;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            float density = view.getResources().getDisplayMetrics().density;
            int spacePx = (int) (spaceDp * density);
            outRect.bottom = spacePx;
        }
    }
}
