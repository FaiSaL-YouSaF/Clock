package com.faisalyousaf777.clock.fragment_alarm;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarm_table")
public class Alarm {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "alarm_id")
    private int alarmId;
    @ColumnInfo(name = "hours")
    private int hours;
    @ColumnInfo(name = "minutes")
    private int minutes;
    @ColumnInfo(name = "message")
    private String message;
    @ColumnInfo(name = "is_am")
    private boolean isAm;

    public Alarm() {
    }

    public Alarm(int hours, int minutes, boolean isAm) {
        this.hours = hours;
        this.minutes = minutes;
        this.isAm = isAm;
    }

    public Alarm(int alarmId, int hours, int minutes, boolean isAm, String message) {
        this.alarmId = alarmId;
        this.hours = hours;
        this.minutes = minutes;
        this.isAm = isAm;
        this.message = message;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAm() {
        return isAm;
    }

    public void setAm(boolean am) {
        isAm = am;
    }

    @NonNull
    @Override
    public String toString() {
        return "Alarm{" +
                "alarmId=" + alarmId +
                ", hours=" + hours +
                ", minutes=" + minutes +
                ", message='" + message + '\'' +
                ", isAm=" + isAm +
                '}';
    }
}
