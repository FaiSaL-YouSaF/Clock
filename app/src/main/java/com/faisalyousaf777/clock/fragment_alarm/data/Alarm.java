package com.faisalyousaf777.clock.fragment_alarm.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.faisalyousaf777.clock.utils.Converter;

import java.util.Calendar;

@Entity(tableName = "alarm_table")
@TypeConverters(Converter.class)
public class Alarm {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "alarm_id")
    private int alarmId;
    @ColumnInfo(name = "hours")
    private int hours;
    @ColumnInfo(name = "minutes")
    private int minutes;
    @ColumnInfo(name = "is_enabled")
    private boolean isEnabled;
    @ColumnInfo(name = "repeat_days")
    private boolean[] repeatDays;
    @ColumnInfo(name = "label")
    private String label;
    @ColumnInfo(name = "vibrate")
    private boolean vibrate;
    @ColumnInfo(name = "ringtone")
    private int soundUri;


    public Alarm() {
    }

    public Alarm(int hours, int minutes, boolean isEnabled, String label) {
        this.hours = hours;
        this.minutes = minutes;
        this.isEnabled = isEnabled;
        this.repeatDays = new boolean[7]; // Default: no repeat
        this.label = label;
        this.vibrate = false;
        this.soundUri = -1; // Default sound {
    }

    public Alarm(int alarmId, int hours, int minutes, boolean isEnabled, String label) {
        this.alarmId = alarmId;
        this.hours = hours;
        this.minutes = minutes;
        this.isEnabled = isEnabled;
        this.repeatDays = new boolean[7]; // Default: no repeat
        this.label = label;
        this.vibrate = false;
        this.soundUri = -1; // Default sound {

    }

    public Alarm(int hours, int minutes, boolean isEnabled, boolean[] repeatDays, String label, boolean vibrate, int soundUri) {
        this.hours = hours;
        this.minutes = minutes;
        this.isEnabled = isEnabled;
        this.repeatDays = repeatDays;
        this.label = label;
        this.vibrate = vibrate;
        this.soundUri = soundUri;
    }

    public Alarm(int alarmId, int hours, int minutes, boolean isEnabled, boolean[] repeatDays, String label, boolean vibrate, int soundUri) {
        this.alarmId = alarmId;
        this.hours = hours;
        this.minutes = minutes;
        this.isEnabled = isEnabled;
        this.repeatDays = repeatDays;
        this.label = label;
        this.vibrate = vibrate;
        this.soundUri = soundUri;
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

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean[] getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDays(boolean[] repeatDays) {
        this.repeatDays = repeatDays;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public int getSoundUri() {
        return soundUri;
    }

    public void setSoundUri(int soundUri) {
        this.soundUri = soundUri;
    }


    public long getNextAlarmTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long currentTime = System.currentTimeMillis();
        long alarmTime = calendar.getTimeInMillis();

        // If alarm time has passed today, set for next day
        if (alarmTime <= currentTime) {
            alarmTime += 24 * 60 * 60 * 1000; // Add one day
        }

        return alarmTime;
    }

    public boolean isRepeating() {
        if (repeatDays == null) return false;
        for (boolean day : repeatDays) {
            if (day) return true;
        }
        return false;
    }

    public String getRepeatDaysSummary() {
        if (repeatDays == null || !isRepeating()) {
            return "Once";
        }

        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        StringBuilder summary = new StringBuilder();
        for (int i = 0; i < repeatDays.length; i++) {
            if (repeatDays[i]) {
                if (summary.length() > 0) {
                    summary.append(", ");
                }
                summary.append(dayNames[i]);
            }
        }
        return summary.toString();
    }


}
