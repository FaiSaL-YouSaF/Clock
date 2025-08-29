package com.faisalyousaf777.clock.fragment_timer;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Timer;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.TimerViewHolder> {

    public TimerAdapter() {
        // Initialize your dataset here if needed
    }

    public void setTimers(List<Timer> timers) {
        // Update your dataset and notify the adapter
        notifyDataSetChanged();
    }

    @Override
    public TimerViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
        // Inflate your item layout and create ViewHolder
        android.view.View view = android.view.LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new TimerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimerViewHolder holder, int position) {
        // Bind data to your item views here
        ((android.widget.TextView) holder.itemView).setText("Timer " + (position + 1));
    }

    @Override
    public int getItemCount() {
        // Return the number of items in your dataset
        return 10; // Example: 10 timers
    }

    public static class TimerViewHolder extends RecyclerView.ViewHolder {
        public TimerViewHolder(android.view.View itemView) {
            super(itemView);
        }
    }
}
