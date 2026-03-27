package com.example.datve.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datve.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;
import java.util.Set;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    public interface OnSeatClickListener {
        void onSeatClick(String seatName);
    }

    private final List<String> seatList;
    private final Set<String> bookedSeats;
    private final OnSeatClickListener listener;
    private String selectedSeat;

    public SeatAdapter(List<String> seatList, Set<String> bookedSeats, OnSeatClickListener listener) {
        this.seatList = seatList;
        this.bookedSeats = bookedSeats;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        String seatName = seatList.get(position);
        holder.tvSeatName.setText(seatName);

        boolean isBooked = bookedSeats.contains(seatName);
        boolean isSelected = seatName.equals(selectedSeat);

        holder.itemView.setEnabled(!isBooked);
        holder.itemView.setAlpha(isBooked ? 0.45f : 1f);
        holder.itemView.setSelected(isSelected);
        MaterialCardView cardView = (MaterialCardView) holder.itemView;
        if (isSelected) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.md_secondary));
        } else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.white));
        }
        holder.tvSeatName.setTextColor(isBooked
                ? ContextCompat.getColor(holder.itemView.getContext(), android.R.color.darker_gray)
                : ContextCompat.getColor(holder.itemView.getContext(), android.R.color.black));

        holder.itemView.setOnClickListener(v -> {
            if (isBooked) {
                return;
            }
            selectedSeat = seatName;
            notifyDataSetChanged();
            listener.onSeatClick(seatName);
        });
    }

    @Override
    public int getItemCount() {
        return seatList.size();
    }

    static class SeatViewHolder extends RecyclerView.ViewHolder {
        TextView tvSeatName;

        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSeatName = itemView.findViewById(R.id.tvSeatName);
        }
    }
}
