package com.faisalyousaf777.clock.fragment_alarm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.faisalyousaf777.clock.R;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private List<Alarm> listOfAlarm;

    public AlarmAdapter() {
    }

    public void setListOfAlarm(List<Alarm> listOfAlarm) {
        this.listOfAlarm = listOfAlarm;
        notifyDataSetChanged();
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
        holder.timeTextView.setText();

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
            timeTextView = itemView.findViewById(R.id.tv_time);
            isAmTextView = itemView.findViewById(R.id.tv_is_am);
            daysTextView = itemView.findViewById(R.id.tv_days);
            alarmToggleSwitch = itemView.findViewById(R.id.switch_toggle_alarm);
        }
    }
}
