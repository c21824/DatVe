package com.example.datve.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datve.R;
import com.example.datve.data.AppDatabase;
import com.example.datve.data.model.TicketDetail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TicketDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TICKET_ID = "extra_ticket_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        TextView tvTicketInfo = findViewById(R.id.tvTicketInfo);
        int ticketId = getIntent().getIntExtra(EXTRA_TICKET_ID, -1);
        TicketDetail detail = AppDatabase.getInstance(this).ticketDao().getTicketDetailById(ticketId);

        if (detail == null) {
            Toast.makeText(this, "Không tìm thấy vé", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String bookedAtText;
        try {
            long millis = Long.parseLong(detail.bookedAt);
            bookedAtText = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                    .format(new Date(millis));
        } catch (NumberFormatException e) {
            bookedAtText = detail.bookedAt;
        }

        String text = "Mã vé: #" + detail.ticketId +
                "\nNgười đặt: " + detail.username +
                "\nPhim: " + detail.movieTitle +
                "\nRạp: " + detail.theaterName +
                "\nGiờ chiếu: " + detail.startTime +
                "\nGhế: " + detail.seatNumber +
                "\nGiá: " + (int) detail.price + " VND" +
                "\nĐặt lúc: " + bookedAtText;

        tvTicketInfo.setText(text);
    }
}
