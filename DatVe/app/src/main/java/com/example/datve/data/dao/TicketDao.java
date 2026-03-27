package com.example.datve.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.datve.data.entity.Ticket;
import com.example.datve.data.model.TicketDetail;

import java.util.List;

@Dao
public interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Ticket ticket);

    @Query("SELECT * FROM tickets WHERE userId = :userId ORDER BY id DESC")
    List<Ticket> getByUserId(int userId);

    @Query("SELECT seatNumber FROM tickets WHERE showTimeId = :showTimeId")
    List<String> getBookedSeatsByShowTime(int showTimeId);

    @Query("SELECT " +
            "t.id AS ticketId, u.username AS username, m.title AS movieTitle, " +
            "th.name AS theaterName, st.startTime AS startTime, " +
            "t.seatNumber AS seatNumber, t.bookedAt AS bookedAt, st.price AS price " +
            "FROM tickets t " +
            "INNER JOIN users u ON u.id = t.userId " +
            "INNER JOIN show_times st ON st.id = t.showTimeId " +
            "INNER JOIN movies m ON m.id = st.movieId " +
            "INNER JOIN theaters th ON th.id = st.theaterId " +
            "WHERE t.userId = :userId " +
            "ORDER BY t.id DESC")
    List<TicketDetail> getTicketDetailsByUserId(int userId);

    @Query("SELECT " +
            "t.id AS ticketId, u.username AS username, m.title AS movieTitle, " +
            "th.name AS theaterName, st.startTime AS startTime, " +
            "t.seatNumber AS seatNumber, t.bookedAt AS bookedAt, st.price AS price " +
            "FROM tickets t " +
            "INNER JOIN users u ON u.id = t.userId " +
            "INNER JOIN show_times st ON st.id = t.showTimeId " +
            "INNER JOIN movies m ON m.id = st.movieId " +
            "INNER JOIN theaters th ON th.id = st.theaterId " +
            "WHERE t.id = :ticketId LIMIT 1")
    TicketDetail getTicketDetailById(int ticketId);
}
