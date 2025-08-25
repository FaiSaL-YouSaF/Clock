package com.faisalyousaf777.clock.fragment_alarm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.faisalyousaf777.clock.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AlarmRepository {

    private final AlarmDao alarmDao;
    private final ExecutorService executorService;

    public AlarmRepository(@NonNull Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        alarmDao = database.alarmDao();
        executorService = Executors.newFixedThreadPool(2);
    }

    public LiveData<List<Alarm>> getAll() {
        return alarmDao.getAll();
    }

    public Alarm findById(int alarmId) {
        return alarmDao.findById(alarmId);
    }

    public void insert(Alarm alarm) {
        executorService.execute(() -> alarmDao.insert(alarm));
    }

    public void update(Alarm alarm) {
        executorService.execute(() -> alarmDao.update(alarm));
    }

    public void deleteById(int alarmId) {
        executorService.execute(() -> alarmDao.deleteById(alarmId));
    }
}
