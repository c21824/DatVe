package com.example.datve.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.datve.data.dao.MovieDao;
import com.example.datve.data.dao.ShowTimeDao;
import com.example.datve.data.dao.TheaterDao;
import com.example.datve.data.dao.TicketDao;
import com.example.datve.data.dao.UserDao;
import com.example.datve.data.entity.Movie;
import com.example.datve.data.entity.ShowTime;
import com.example.datve.data.entity.Theater;
import com.example.datve.data.entity.Ticket;
import com.example.datve.data.entity.User;

@Database(entities = {User.class, Movie.class, Theater.class, ShowTime.class, Ticket.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;

    public abstract UserDao userDao();
    public abstract MovieDao movieDao();
    public abstract TheaterDao theaterDao();
    public abstract ShowTimeDao showTimeDao();
    public abstract TicketDao ticketDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "movie_ticket_db"
                            )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
