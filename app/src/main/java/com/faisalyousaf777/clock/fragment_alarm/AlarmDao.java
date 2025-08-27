package com.faisalyousaf777.clock.fragment_alarm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDao {

    @Query("SELECT * FROM alarm_table")
    LiveData<List<Alarm>> getAll();
    @Query("SELECT * FROM alarm_table WHERE alarm_id = :alarmId")
    Alarm findById(int alarmId);

    @Insert
    long insert(Alarm alarm);

    @Insert
    List<Long> insertAll(List<Alarm> alarms);

    @Update
    int update(Alarm alarm);

    @Query("DELETE FROM alarm_table WHERE alarm_id = :alarmId")
    int deleteById(int alarmId);
}
