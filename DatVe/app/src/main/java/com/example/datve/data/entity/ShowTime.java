package com.example.datve.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "show_times",
        foreignKeys = {
                @ForeignKey(
                        entity = Movie.class,
                        parentColumns = "id",
                        childColumns = "movieId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Theater.class,
                        parentColumns = "id",
                        childColumns = "theaterId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("movieId"), @Index("theaterId")}
)
public class ShowTime {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int movieId;
    public int theaterId;
    public String startTime;
    public double price;

    public ShowTime(int movieId, int theaterId, String startTime, double price) {
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.startTime = startTime;
        this.price = price;
    }
}
