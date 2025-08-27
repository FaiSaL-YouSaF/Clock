package com.faisalyousaf777.clock.fragment_alarm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.faisalyousaf777.clock.fragment_alarm.Alarm;
import com.faisalyousaf777.clock.fragment_alarm.AlarmRepository;

import java.util.List;

public class AlarmViewModel extends AndroidViewModel {

    private final AlarmRepository repository;

    public AlarmViewModel(@NonNull Application application) {
        super(application);
        repository = new AlarmRepository(application);
    }

    public LiveData<List<Alarm>> getAll() {
        return repository.getAll();
    }

    public Alarm findById(int alarmId) {
        return repository.findById(alarmId);
    }

    public void insert(Alarm alarm) {
        repository.insert(alarm);
    }

    public void update(Alarm alarm) {
        repository.update(alarm);
    }

    public void deleteById(int alarmId) {
        repository.deleteById(alarmId);
    }

}
