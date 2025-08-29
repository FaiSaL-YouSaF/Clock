package com.faisalyousaf777.clock.fragment_alarm;

import static com.faisalyousaf777.clock.fragment_alarm.view.AddEditAlarmActivity.EXTRA_ALARM_ID;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.faisalyousaf777.clock.R;
import com.faisalyousaf777.clock.fragment_alarm.data.Alarm;
import com.faisalyousaf777.clock.fragment_alarm.view.AddEditAlarmActivity;
import com.faisalyousaf777.clock.fragment_alarm.viewmodel.AlarmViewModel;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private List<Alarm> listOfAlarm = Collections.emptyList();
    private final AlarmViewModel alarmViewModel;

    public AlarmAdapter(AlarmViewModel alarmViewModel) {
        this.alarmViewModel = alarmViewModel;
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
        holder.tvIsAm.setText(alarm.isAm() ? "AM" : "PM");
        int hours = alarm.getHours();
        if (alarm.isAm()) {
            if (hours == 0) {
                hours = 12; // Convert 0 hours to 12 for AM
            }
        } else {
            if (hours > 12) {
                hours -= 12;
            }
        }
        holder.tvTime.setText(LocalTime.of(hours, alarm.getMinutes()).toString());

        holder.switchToggleAlarm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Turn ON the alarm
                AlarmScheduler.scheduleAlarm(buttonView.getContext(), alarm);
                LocalTime alarmTime = LocalTime.of(alarm.getHours(), alarm.getMinutes());
                LocalTime now = LocalTime.now();
                Duration duration = Duration.between(alarmTime, now);
                long minutes = Math.abs(duration.toMinutes());
                Toast.makeText(buttonView.getContext(), String.format("Alarm will remind in %s Hours %s Minutes", (minutes / 60), (minutes % 60)), Toast.LENGTH_SHORT).show();
            } else {
                AlarmScheduler.cancelAlarm(buttonView.getContext(), alarm);
                Toast.makeText(buttonView.getContext(),
                        "Alarm turned off", Toast.LENGTH_SHORT).show();

                // TODO: if you want actual cancel → use AlarmManager.cancel(PendingIntent)
            }
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddEditAlarmActivity.class);
            intent.putExtra(EXTRA_ALARM_ID, alarm.getAlarmId());
            v.getContext().startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            Toast.makeText(v.getContext(), "Long Clicked : " + alarm.getAlarmId(), Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return listOfAlarm.size();
    }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvTime, tvIsAm, tvDays;
        MaterialSwitch switchToggleAlarm;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvIsAm = itemView.findViewById(R.id.tv_is_am);
            tvDays = itemView.findViewById(R.id.tv_days);
            switchToggleAlarm = itemView.findViewById(R.id.switch_toggle_alarm);
        }
    }
}
