package com.faisalyousaf777.clock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.faisalyousaf777.clock.entity.Alarm;

import java.util.ArrayList;
import java.util.List;

public class AlarmDbHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "alarm_table";
    public static final String COL_ID = "alarm_id";
    public static final String COL_HOURS = "hours";
    public static final String COL_MINUTES = "minutes";
    public static final String COL_LABEL = "label";
    public static final String COL_DAYS = "days";
    public static final String COL_RINGTONE = "ringtone";
    public static final String COL_VIBRATE = "vibrate";
    public static final String COL_AM_PM = "am_pm";

    public static final String CREATE_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s INTEGER, %s TEXT, %s TEXT, %s INTEGER)", TABLE_NAME, COL_ID, COL_HOURS, COL_MINUTES, COL_AM_PM, COL_LABEL, COL_RINGTONE, COL_VIBRATE);
    public static final String DROP_TABLE = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);


    public static final String DB_NAME = "clock_database";
    public static final int DB_VERSION = 1;

    private static AlarmDbHelper instance;

    public static AlarmDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new AlarmDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    private AlarmDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }
    }

    public void insertAlarm(final Alarm alarm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_HOURS, alarm.getHours());
        contentValues.put(COL_MINUTES, alarm.getMinutes());
        contentValues.put(COL_AM_PM, alarm.isAm() ? 1 : 0);
        contentValues.put(COL_LABEL, alarm.getLabel());
        contentValues.put(COL_RINGTONE, alarm.getRingtone());
        contentValues.put(COL_VIBRATE, alarm.isVibrate());

        try {
            db.insert(TABLE_NAME, null, contentValues);
        } catch (final Exception ex) {
            Log.d("Insert_Error", "insertAlarm: " + ex.getMessage());
        }
        db.close();
    }


    public Alarm getAlarmById(int alarmId) {
        Alarm alarm = null; // Initialize to null to indicate no alarm found
        try (
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(
                        TABLE_NAME,
                        new String[]{COL_ID, COL_HOURS, COL_MINUTES, COL_LABEL, COL_RINGTONE, COL_VIBRATE, COL_AM_PM},
                        COL_ID + " = ?", new String[]{String.valueOf(alarmId)},
                        null, null, null
                )
        ) {
            if (cursor != null && cursor.moveToFirst()) {
                alarm = new Alarm();
                alarm.setAlarmId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                alarm.setHours(cursor.getInt(cursor.getColumnIndexOrThrow(COL_HOURS)));
                alarm.setMinutes(cursor.getInt(cursor.getColumnIndexOrThrow(COL_MINUTES)));
                alarm.setLabel(cursor.getString(cursor.getColumnIndexOrThrow(COL_LABEL)));
                alarm.setRingtone(cursor.getString(cursor.getColumnIndexOrThrow(COL_RINGTONE)));
                alarm.setVibrate(cursor.getInt(cursor.getColumnIndexOrThrow(COL_VIBRATE)) == 1);
                alarm.setAm(cursor.getInt(cursor.getColumnIndexOrThrow(COL_AM_PM)) == 1);
            } else {
                Log.d("AlarmDbHelper", "No alarm found with ID: " + alarmId);
            }
        } catch (final Exception e) {
            Log.e("AlarmDbHelper", "Error retrieving alarm with ID: " + alarmId, e);
        }
        return alarm;
    }


    public List<Alarm> getAllAlarms() {
        List<Alarm> listOfAlarms = new ArrayList<>();

        try (
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(
                        TABLE_NAME,
                        new String[]{COL_ID, COL_HOURS, COL_MINUTES, COL_AM_PM, COL_LABEL, COL_RINGTONE, COL_VIBRATE}, // Explicit columns
                        null, null, null, null, null
                )
        ) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Alarm alarm = new Alarm();
                    alarm.setAlarmId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                    alarm.setHours(cursor.getInt(cursor.getColumnIndexOrThrow(COL_HOURS)));
                    alarm.setMinutes(cursor.getInt(cursor.getColumnIndexOrThrow(COL_MINUTES)));
                    alarm.setAm(cursor.getInt(cursor.getColumnIndexOrThrow(COL_AM_PM)) == 1);
                    alarm.setLabel(cursor.getString(cursor.getColumnIndexOrThrow(COL_LABEL)));
                    alarm.setRingtone(cursor.getString(cursor.getColumnIndexOrThrow(COL_RINGTONE)));
                    alarm.setVibrate(cursor.getInt(cursor.getColumnIndexOrThrow(COL_VIBRATE)) == 1);
                    listOfAlarms.add(alarm);
                }
            } else {
                Log.d("AlarmDbHelper", "No alarms found in the database");
            }
        } catch (final Exception e) {
            Log.e("AlarmDbHelper", "Error retrieving alarms from the database", e);
        }
        return listOfAlarms;
    }


}
