package com.faisalyousaf777.clock.fragment_timer;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "timers")
public class TimerEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    // total requested duration in milliseconds
    public long durationMillis;

    // when user started the timer (epoch millis). If not running, last start time or 0.
    public long startTimeMillis;

    // expected end time (startTimeMillis + remaining at start). If not running, 0.
    public long endTimeMillis;

    // remaining milliseconds when paused (useful for resume)
    public long remainingMillis;

    public String label;

    // true if timer is started and should count down
    public boolean isRunning;

    // created timestamp (epoch millis)
    public long createdAtMillis;
}

