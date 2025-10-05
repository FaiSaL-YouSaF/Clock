package com.faisalyousaf777.clock.fragment_timer;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimerDao {

    @Insert
    long insert(TimerEntity timer);

    @Update
    void update(TimerEntity timer);

    @Delete
    void delete(TimerEntity timer);

    @Query("SELECT * FROM timers ORDER BY createdAtMillis DESC")
    LiveData<List<TimerEntity>> getAllTimersLive();

    // sync query used on BootReceiver (must not be called on main thread)
    @Query("SELECT * FROM timers")
    List<TimerEntity> getAllTimersSync();

    @Query("SELECT * FROM timers WHERE id = :id LIMIT 1")
    TimerEntity getTimerByIdSync(int id);
}

