package com.faisalyousaf777.clock.entity;

import androidx.annotation.NonNull;

import java.time.DayOfWeek;
import java.util.List;

public class Alarm {
    private int alarmId;
    private int hours;
    private int minutes;
    private boolean isAm;
    private String label;
    private String ringtone;
    private boolean isVibrate;
    private List<DayOfWeek> repeatDays;

    public Alarm() {
    }

    public Alarm(int hours, int minutes, boolean isAm) {
        this.hours = hours;
        this.minutes = minutes;
        this.isAm = isAm;
    }

    //All args constructor excluding the ID
    public Alarm(int hours, int minutes, boolean isAm, String label, String ringtone, boolean isVibrate, List<DayOfWeek> repeatDays) {
        this.hours = hours;
        this.minutes = minutes;
        this.isAm = isAm;
        this.label = label;
        this.ringtone = ringtone;
        this.isVibrate = isVibrate;
        this.repeatDays = repeatDays;
    }

    // All args constructor
    public Alarm(int alarmId, int hours, int minutes, boolean isAm, String label, String ringtone, boolean isVibrate, List<DayOfWeek> repeatDays) {
        this.alarmId = alarmId;
        this.hours = hours;
        this.minutes = minutes;
        this.isAm = isAm;
        this.label = label;
        this.ringtone = ringtone;
        this.isVibrate = isVibrate;
        this.repeatDays = repeatDays;
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

    public boolean isAm() {
        return isAm;
    }

    public void setAm(boolean am) {
        isAm = am;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRingtone() {
        return ringtone;
    }

    public void setRingtone(String ringtone) {
        this.ringtone = ringtone;
    }

    public boolean isVibrate() {
        return isVibrate;
    }

    public void setVibrate(boolean vibrate) {
        isVibrate = vibrate;
    }

    public List<DayOfWeek> getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDays(List<DayOfWeek> repeatDays) {
        this.repeatDays = repeatDays;
    }

    @NonNull
    @Override
    public String toString() {
        return "Alarm{" +
                "alarmId=" + alarmId +
                ", hours=" + hours +
                ", minutes=" + minutes +
                ", isAm=" + isAm +
                ", label='" + label + '\'' +
                ", ringtone='" + ringtone + '\'' +
                ", isVibrate=" + isVibrate +
                ", repeatDays=" + repeatDays +
                '}';
    }
}
