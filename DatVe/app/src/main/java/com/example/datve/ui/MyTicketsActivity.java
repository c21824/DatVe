package com.example.datve.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datve.R;
import com.example.datve.data.AppDatabase;
import com.example.datve.data.model.TicketDetail;
import com.example.datve.ui.adapter.MyTicketAdapter;
import com.example.datve.util.SessionManager;

import java.util.List;

public class MyTicketsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewMyTickets);
        TextView tvEmpty = findViewById(R.id.tvMyTicketsEmpty);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int userId = new SessionManager(this).getUserId();
        List<TicketDetail> myTickets = AppDatabase.getInstance(this).ticketDao().getTicketDetailsByUserId(userId);

        if (myTickets == null || myTickets.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            return;
        }

        tvEmpty.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        MyTicketAdapter adapter = new MyTicketAdapter(myTickets, ticket -> {
            Intent intent = new Intent(this, TicketDetailActivity.class);
            intent.putExtra(TicketDetailActivity.EXTRA_TICKET_ID, ticket.ticketId);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }
}
