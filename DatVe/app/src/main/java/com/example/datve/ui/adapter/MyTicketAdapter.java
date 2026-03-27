package com.example.datve.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datve.R;
import com.example.datve.data.model.TicketDetail;

import java.util.List;

public class MyTicketAdapter extends RecyclerView.Adapter<MyTicketAdapter.MyTicketViewHolder> {
    public interface OnTicketClickListener {
        void onTicketClick(TicketDetail ticket);
    }

    private final List<TicketDetail> tickets;
    private final OnTicketClickListener listener;

    public MyTicketAdapter(List<TicketDetail> tickets, OnTicketClickListener listener) {
        this.tickets = tickets;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_ticket, parent, false);
        return new MyTicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTicketViewHolder holder, int position) {
        TicketDetail ticket = tickets.get(position);
        holder.tvMovieTitle.setText(ticket.movieTitle + " - Ghế " + ticket.seatNumber);
        holder.tvMeta.setText(ticket.theaterName + " | " + ticket.startTime);
        holder.itemView.setOnClickListener(v -> listener.onTicketClick(ticket));
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    static class MyTicketViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieTitle;
        TextView tvMeta;

        public MyTicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tvTicketMovieTitle);
            tvMeta = itemView.findViewById(R.id.tvTicketMeta);
        }
    }
}
