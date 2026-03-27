package com.example.datve.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datve.R;
import com.example.datve.data.AppDatabase;
import com.example.datve.data.entity.ShowTime;
import com.example.datve.data.entity.Ticket;
import com.example.datve.ui.adapter.SeatAdapter;
import com.example.datve.util.SessionManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SeatSelectionActivity extends AppCompatActivity {
    public static final String EXTRA_SHOWTIME_ID = "extra_showtime_id";

    private int showTimeId;
    private String selectedSeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        TextView tvInfo = findViewById(R.id.tvSeatInfo);
        TextView tvSelectedSeat = findViewById(R.id.tvSelectedSeat);
        RecyclerView recyclerViewSeats = findViewById(R.id.recyclerViewSeats);
        Button btnBookTicket = findViewById(R.id.btnBookTicket);

        showTimeId = getIntent().getIntExtra(EXTRA_SHOWTIME_ID, -1);
        ShowTime showTime = AppDatabase.getInstance(this).showTimeDao().getById(showTimeId);
        if (showTime == null) {
            Toast.makeText(this, "Không tìm thấy lịch chiếu", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvInfo.setText("Suất chiếu: " + showTime.startTime + "\nGiá: " + (int) showTime.price + " VND");

        List<String> allSeats = generateSeatList();
        List<String> bookedSeats = AppDatabase.getInstance(this).ticketDao().getBookedSeatsByShowTime(showTimeId);
        Set<String> bookedSeatSet = new HashSet<>(bookedSeats);

        recyclerViewSeats.setLayoutManager(new GridLayoutManager(this, 4));
        SeatAdapter seatAdapter = new SeatAdapter(allSeats, bookedSeatSet, seatName -> {
            selectedSeat = seatName;
            tvSelectedSeat.setText("Ghế đã chọn: " + seatName);
        });
        recyclerViewSeats.setAdapter(seatAdapter);

        btnBookTicket.setOnClickListener(v -> {
            if (selectedSeat == null || selectedSeat.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ghế từ danh sách", Toast.LENGTH_SHORT).show();
                return;
            }
            if (bookedSeatSet.contains(selectedSeat)) {
                Toast.makeText(this, "Ghế đã được đặt, vui lòng chọn ghế khác", Toast.LENGTH_SHORT).show();
                return;
            }

            SessionManager sessionManager = new SessionManager(this);
            int userId = sessionManager.getUserId();
            Ticket ticket = new Ticket(userId, showTimeId, selectedSeat, String.valueOf(System.currentTimeMillis()));
            long ticketId = AppDatabase.getInstance(this).ticketDao().insert(ticket);

            Toast.makeText(this, "Đặt vé thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, TicketDetailActivity.class);
            intent.putExtra(TicketDetailActivity.EXTRA_TICKET_ID, (int) ticketId);
            startActivity(intent);
            finish();
        });
    }

    private List<String> generateSeatList() {
        List<String> seats = new ArrayList<>();
        char[] rows = {'A', 'B', 'C', 'D'};
        for (char row : rows) {
            for (int col = 1; col <= 5; col++) {
                seats.add(row + String.valueOf(col));
            }
        }
        return seats;
    }
}
