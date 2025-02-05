package com.faisalyousaf777.clock.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.faisalyousaf777.clock.R;
import com.faisalyousaf777.clock.entity.Alarm;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private List<Alarm> listOfAlarm;

    public AlarmAdapter(List<Alarm> listOfAlarm) {
        this.listOfAlarm = listOfAlarm;
    }

    @NonNull
    @Override
    public AlarmAdapter.AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_view_holder, parent, false);
        return new AlarmViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmAdapter.AlarmViewHolder holder, int position) {
        Alarm alarm = listOfAlarm.get(position);
        String timeStr = String.format("%s:%s", alarm.getHours(), alarm.getMinutes());
        holder.timeTextView.setText(timeStr);
        holder.isAmTextView.setText(alarm.isAm() ? "AM" : "PM");
//        holder.daysTextView.setText();
        holder.alarmToggleSwitch.setActivated(false);
    }

    @Override
    public int getItemCount() {
        return listOfAlarm.size();
    }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView timeTextView, isAmTextView, daysTextView;
        MaterialSwitch alarmToggleSwitch;
        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            isAmTextView = itemView.findViewById(R.id.isAmTextView);
            daysTextView = itemView.findViewById(R.id.daysTextView);
            alarmToggleSwitch = itemView.findViewById(R.id.alarmToggleSwitch);
        }
    }
}
