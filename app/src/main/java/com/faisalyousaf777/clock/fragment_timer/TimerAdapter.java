package com.faisalyousaf777.clock.fragment_timer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.faisalyousaf777.clock.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.TimerViewHolder> {

    private List<Timer> timerList = new ArrayList<>();

    public TimerAdapter() {
    }

    public void setTimers(List<Timer> timers) {
        timerList = timers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TimerAdapter.TimerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.timer_view_holder, parent, false);
        return new TimerViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimerAdapter.TimerViewHolder holder, int position) {
        Timer timer = timerList.get(position);
        long duration = timer.getDuration();                     // in milliseconds
        int hours = (int) (duration / 3600000);                  // 1 hour = 3600000 milliseconds
        int minutes = (int) (duration % 3600000) / 60000;        // 1 minute = 60000 milliseconds
        int seconds = (int) (duration % 60000) / 1000;           // 1 second = 1000 milliseconds
        String timeFormatted = String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds);
        String timerTitle = String.format("Timer: %sh %sm %ss", hours, minutes, seconds);
        holder.tvTimerTitle.setText(timerTitle);
        holder.tvDuration.setText(timeFormatted);

        holder.btnDelete.setOnClickListener(v -> {
            int holderPosition = holder.getLayoutPosition();
            Log.d("POSITION", "onBindViewHolder: Timer deleted at position : " + holderPosition + " Index: " + (holderPosition));
            if (holderPosition != RecyclerView.NO_POSITION && holderPosition < timerList.size()) {
                timerList.remove(holderPosition);
                notifyItemRemoved(holderPosition);
            }
        });

        holder.btnStartStop.setOnClickListener(v -> {
            // Toggle timer state between RUNNING and PAUSED
            if (timer.getTimerState() == TimerState.RUNNING) {
                timer.setTimerState(TimerState.PAUSED);
                holder.btnStartStop.setIcon(AppCompatResources.getDrawable(v.getContext(), R.drawable.pause_24dp));
            } else {
                timer.setTimerState(TimerState.RUNNING);
                holder.btnStartStop.setIcon(AppCompatResources.getDrawable(v.getContext(), R.drawable.play_arrow_24dp));
            }
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return timerList.size();
    }


    public static class TimerViewHolder extends RecyclerView.ViewHolder {
        TextView tvTimerTitle, tvDuration;
        MaterialButton btnDelete, btnStartStop;

        public TimerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimerTitle = itemView.findViewById(R.id.tv_timer_title);
            tvDuration = itemView.findViewById(R.id.tv_time);
            btnDelete = itemView.findViewById(R.id.btn_delete_timer);
            btnStartStop = itemView.findViewById(R.id.btn_play_pause_timer);
        }
    }
}
