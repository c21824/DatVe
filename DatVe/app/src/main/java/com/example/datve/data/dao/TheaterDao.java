package com.example.datve.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.datve.data.entity.Theater;

import java.util.List;

@Dao
public interface TheaterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Theater theater);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Theater> theaters);

    @Query("SELECT * FROM theaters ORDER BY id ASC")
    List<Theater> getAll();

    @Query("SELECT * FROM theaters WHERE id = :id LIMIT 1")
    Theater getById(int id);
}
