package com.example.datve.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.datve.data.entity.Movie;
import com.example.datve.data.entity.ShowTime;
import com.example.datve.data.entity.Theater;
import com.example.datve.data.entity.User;

import java.util.Arrays;

public class DatabaseSeeder {
    private static final String PREF_NAME = "app_seed_pref";
    private static final String KEY_SEEDED = "is_seeded";

    public static void seedIfNeeded(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean seeded = pref.getBoolean(KEY_SEEDED, false);
        if (seeded) {
            return;
        }

        AppDatabase db = AppDatabase.getInstance(context);

        db.userDao().insertAll(Arrays.asList(
                new User("admin", "123456", "Admin User"),
                new User("student", "123456", "Student User")
        ));

        db.movieDao().insertAll(Arrays.asList(
                new Movie("Avengers: Endgame", "Action", 181),
                new Movie("Inception", "Sci-Fi", 148),
                new Movie("Interstellar", "Sci-Fi", 169)
        ));

        db.theaterDao().insertAll(Arrays.asList(
                new Theater("CGV Vincom", "District 1"),
                new Theater("Lotte Cinema", "District 7"),
                new Theater("Galaxy Cinema", "Thu Duc")
        ));

        db.showTimeDao().insertAll(Arrays.asList(
                new ShowTime(1, 1, "2026-03-27 09:00", 90000),
                new ShowTime(1, 2, "2026-03-27 14:00", 100000),
                new ShowTime(2, 1, "2026-03-27 16:00", 85000),
                new ShowTime(2, 3, "2026-03-27 19:30", 95000),
                new ShowTime(3, 2, "2026-03-28 10:15", 110000),
                new ShowTime(3, 3, "2026-03-28 20:00", 120000)
        ));

        pref.edit().putBoolean(KEY_SEEDED, true).apply();
    }
}
