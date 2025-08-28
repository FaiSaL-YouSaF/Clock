package com.faisalyousaf777.clock;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.faisalyousaf777.clock.fragment_alarm.data.Alarm;
import com.faisalyousaf777.clock.fragment_alarm.data.AlarmDao;
import com.faisalyousaf777.clock.utils.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {Alarm.class}, version = 1, exportSchema = false)
@TypeConverters(Converter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "clock_database";
    private static volatile AppDatabase INSTANCE;

    public abstract AlarmDao alarmDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static final Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(() -> {

                AlarmDao alarmDao = INSTANCE.alarmDao();
                alarmDao.insertAll(prepopulateData());
                Log.d("DB", "onCreate: Database prepopulated successfully");
            });
        }
    };

    private static List<Alarm> prepopulateData() {
        List<Alarm> alarms = new ArrayList<>();
        alarms.add(new Alarm(11, 28, true, "Morning Alarm"));
        alarms.add(new Alarm(6, 0, true, "Workout Alarm"));
        alarms.add(new Alarm(7, 30, false, "Meeting Reminder"));
        alarms.add(new Alarm(9, 45, true, "Medication Reminder"));
        alarms.add(new Alarm(14, 0, false, "Afternoon Nap"));
        alarms.add(new Alarm(16, 15, true, "Evening Walk"));
        alarms.add(new Alarm(19, 0, false, "Dinner Time"));
        alarms.add(new Alarm(21, 30, true, "Bedtime Reminder"));
        alarms.add(new Alarm(8, 30, false, "Yoga Session"));
        alarms.add(new Alarm(7, 0, true, "Project Deadline"));
        alarms.add(new Alarm(12, 15, false, "Lunch Break"));
        alarms.add(new Alarm(18, 45, true, "Grocery Shopping"));
        alarms.add(new Alarm(22, 0, false, "Read a Book"));
        alarms.add(new Alarm(20, 8, false, "Call Family"));
        alarms.add(new Alarm(1, 15, true, "Midnight Snack"));
        for (int i = 0; i < 20; i++) {
            alarms.add(new Alarm(3 + i, (i * 5) % 60, i % 2 == 0, "Alarm " + (i + 1)));
        }
        return alarms;
    }
}
