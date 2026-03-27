package com.example.datve.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.datve.data.entity.ShowTime;

import java.util.List;

@Dao
public interface ShowTimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ShowTime showTime);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ShowTime> showTimes);

    @Query("SELECT * FROM show_times WHERE movieId = :movieId ORDER BY startTime ASC")
    List<ShowTime> getByMovieId(int movieId);

    @Query("SELECT * FROM show_times WHERE theaterId = :theaterId ORDER BY startTime ASC")
    List<ShowTime> getByTheaterId(int theaterId);

    @Query("SELECT * FROM show_times WHERE id = :id LIMIT 1")
    ShowTime getById(int id);
}
