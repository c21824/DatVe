package com.example.datve.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "tickets",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = ShowTime.class,
                        parentColumns = "id",
                        childColumns = "showTimeId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("userId"), @Index("showTimeId")}
)
public class Ticket {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int userId;
    public int showTimeId;
    public String seatNumber;
    public String bookedAt;

    public Ticket(int userId, int showTimeId, String seatNumber, String bookedAt) {
        this.userId = userId;
        this.showTimeId = showTimeId;
        this.seatNumber = seatNumber;
        this.bookedAt = bookedAt;
    }
}
