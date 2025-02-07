package com.faisalyousaf777.clock.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.faisalyousaf777.clock.R;
import com.faisalyousaf777.clock.entity.Alarm;
import com.faisalyousaf777.clock.utility.OnViewHolderClickListener;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private List<Alarm> listOfAlarm;
    private OnViewHolderClickListener listener;

    public AlarmAdapter(List<Alarm> listOfAlarm, OnViewHolderClickListener listener) {
        this.listOfAlarm = listOfAlarm;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AlarmAdapter.AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_view_holder, parent, false);
        return new AlarmViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmAdapter.AlarmViewHolder holder, int position) {
        Alarm alarm = listOfAlarm.get(position);
        String timeStr = String.format("%s:%s", alarm.getHours(), alarm.getMinutes());
        holder.timeTextView.setText(timeStr);
        holder.isAmTextView.setText(alarm.isAm() ? "AM" : "PM");
//        holder.daysTextView.setText();
        holder.alarmToggleSwitch.setActivated(false);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onViewHolderClick(position);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onViewHolderLongClick(position);
                return true;
            } else {
                return false;
            }
        });

        holder.alarmToggleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onSwitchToggle(position, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfAlarm.size();
    }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView timeTextView, isAmTextView, daysTextView;
        MaterialSwitch alarmToggleSwitch;
        public AlarmViewHolder(@NonNull View itemView, OnViewHolderClickListener listener) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            isAmTextView = itemView.findViewById(R.id.isAmTextView);
            daysTextView = itemView.findViewById(R.id.daysTextView);
            alarmToggleSwitch = itemView.findViewById(R.id.alarmToggleSwitch);
        }
    }
}
