package com.faisalyousaf777.clock.fragment_timer;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "timers_table")
public class Timer {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "timer_id")
    private int timerId;
    @ColumnInfo(name = "duration")
    private long duration;
    @ColumnInfo(name = "label")
    private String label;
    @ColumnInfo(name = "timer_state")
    private TimerState timerState;
    @ColumnInfo(name = "remaining_time")
    private long remainingTime;
    @ColumnInfo(name = "created_at")
    private LocalDateTime createdAt;

    public Timer() {
    }

    public Timer(long duration, String label, TimerState timerState, long remainingTime, LocalDateTime createdAt) {
        this.duration = duration;
        this.label = label;
        this.timerState = timerState;
        this.remainingTime = remainingTime;
        this.createdAt = createdAt;
    }

    public Timer(int timerId, long duration, String label, TimerState timerState, long remainingTime, LocalDateTime createdAt) {
        this.timerId = timerId;
        this.duration = duration;
        this.label = label;
        this.timerState = timerState;
        this.remainingTime = remainingTime;
        this.createdAt = createdAt;
    }

    public int getTimerId() {
        return timerId;
    }

    public void setTimerId(int timerId) {
        this.timerId = timerId;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public TimerState getTimerState() {
        return timerState;
    }

    public void setTimerState(TimerState timerState) {
        this.timerState = timerState;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(long remainingTime) {
        this.remainingTime = remainingTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @NonNull
    @Override
    public String toString() {
        return "Timer{" +
                "timerId=" + timerId +
                ", duration=" + duration +
                ", label='" + label + '\'' +
                ", timerState=" + timerState +
                ", remainingTime=" + remainingTime +
                ", createdAt=" + createdAt +
                '}';
    }
}
