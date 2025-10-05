package com.faisalyousaf777.clock.fragment_timer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TimerViewModel extends AndroidViewModel {

    private final TimerRepository repo;
    private final LiveData<List<TimerEntity>> timersLive;

    public TimerViewModel(@NonNull Application application) {
        super(application);
        repo = new TimerRepository(application);
        timersLive = repo.getAllTimersLive();
    }

    public LiveData<List<TimerEntity>> getTimersLive() {
        return timersLive;
    }

    public void startNewTimer(long durationMillis, String label, TimerRepository.Callback<Long> cb) {
        repo.insert(createTimerEntity(durationMillis, label), cb);
    }

    private TimerEntity createTimerEntity(long durationMillis, String label) {
        TimerEntity t = new TimerEntity();
        long now = System.currentTimeMillis();
        t.durationMillis = durationMillis;
        t.startTimeMillis = now;
        t.endTimeMillis = now + durationMillis;
        t.remainingMillis = durationMillis;
        t.label = label;
        t.isRunning = true;
        t.createdAtMillis = now;
        return t;
    }

    public void pauseTimer(TimerEntity timer) {
        repo.update(timer);
    }

    public void deleteTimer(TimerEntity timer) {
        repo.delete(timer);
    }
}


