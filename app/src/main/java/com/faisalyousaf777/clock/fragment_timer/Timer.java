package com.faisalyousaf777.clock.fragment_timer;

import java.time.LocalDateTime;

public class Timer {
    private final int id;
    private final long durationMillis;
    private final String label;
    private final boolean running;
    private final long endTimeMillis;
    private final LocalDateTime createdAt;

    public Timer(int id, long durationMillis, String label, boolean running,
                 long endTimeMillis, LocalDateTime createdAt) {
        this.id = id;
        this.durationMillis = durationMillis;
        this.label = label;
        this.running = running;
        this.endTimeMillis = endTimeMillis;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public long getDurationMillis() {
        return durationMillis;
    }

    public String getLabel() {
        return label;
    }

    public boolean isRunning() {
        return running;
    }

    public long getEndTimeMillis() {
        return endTimeMillis;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

