package com.faisalyousaf777.clock.fragment_timer;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.faisalyousaf777.clock.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimerRepository {

    private final TimerDao dao;
    private final ExecutorService exec = Executors.newSingleThreadExecutor();

    public TimerRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.dao = db.timerDao();
    }

    public LiveData<List<TimerEntity>> getAllTimersLive() {
        return dao.getAllTimersLive();
    }

    public void insert(TimerEntity timer, Callback<Long> callback) {
        exec.execute(() -> {
            long id = dao.insert(timer);
            if (callback != null) callback.onResult(id);
        });
    }

    public void update(TimerEntity timer) {
        exec.execute(() -> dao.update(timer));
    }

    public void delete(TimerEntity timer) {
        exec.execute(() -> dao.delete(timer));
    }

    public List<TimerEntity> getAllTimersSync() {
        return dao.getAllTimersSync();
    }

    public TimerEntity getTimerByIdSync(int id) {
        return dao.getTimerByIdSync(id);
    }

    public interface Callback<T> {
        void onResult(T value);
    }
}


