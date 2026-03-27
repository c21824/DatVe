package com.example.datve.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datve.R;
import com.example.datve.data.entity.ShowTime;

import java.util.List;

public class ShowTimeAdapter extends RecyclerView.Adapter<ShowTimeAdapter.ShowTimeViewHolder> {
    public interface OnShowTimeClickListener {
        void onShowTimeClick(ShowTime showTime);
    }

    private final List<ShowTime> showTimes;
    private final OnShowTimeClickListener listener;

    public ShowTimeAdapter(List<ShowTime> showTimes, OnShowTimeClickListener listener) {
        this.showTimes = showTimes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShowTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
        return new ShowTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowTimeViewHolder holder, int position) {
        ShowTime showTime = showTimes.get(position);
        holder.tvTitle.setText(showTime.startTime + " | Giá: " + (int) showTime.price + " VND");
        holder.itemView.setOnClickListener(v -> listener.onShowTimeClick(showTime));
    }

    @Override
    public int getItemCount() {
        return showTimes.size();
    }

    static class ShowTimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public ShowTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvItemText);
        }
    }
}
