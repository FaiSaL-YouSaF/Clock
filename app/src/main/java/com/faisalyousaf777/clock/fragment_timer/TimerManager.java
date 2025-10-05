package com.faisalyousaf777.clock.fragment_timer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class TimerManager {

    private final Context context;
    private final AlarmManager alarmManager;
    private final TimerRepository repository;
    private final ExecutorService exec = Executors.newSingleThreadExecutor();

    public static final String EXTRA_TIMER_ID = "EXTRA_TIMER_ID";
    public static final String EXTRA_TIMER_LABEL = "EXTRA_TIMER_LABEL";

    public TimerManager(Context ctx) {
        this.context = ctx.getApplicationContext();
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.repository = new TimerRepository(context);
    }

    /**
     * Create and start a timer.
     * durationMillis - total duration in ms
     * callback will return generated id when insertion completes
     */
    public void startNewTimer(long durationMillis, String label, TimerRepository.Callback<Long> callback) {
        long now = System.currentTimeMillis();
        TimerEntity timer = new TimerEntity();
        timer.durationMillis = durationMillis;
        timer.startTimeMillis = now;
        timer.endTimeMillis = now + durationMillis;
        timer.remainingMillis = durationMillis;
        timer.label = label;
        timer.isRunning = true;
        timer.createdAtMillis = now;

        repository.insert(timer, id -> {
            // DB inserted - schedule alarm (id may be > Integer.MAX for long; use int cast carefully)
            int intId = (int) id;
            scheduleAlarm(intId, timer.endTimeMillis, timer.label);
            if (callback != null) callback.onResult(id);
        });
    }

    /**
     * Pause an already-running timer: cancel alarm and persist remainingMillis
     */
    public void pauseTimer(TimerEntity timer) {
        cancelAlarm(timer.id);
        long now = System.currentTimeMillis();
        long remaining = Math.max(0L, timer.endTimeMillis - now);
        timer.remainingMillis = remaining;
        timer.isRunning = false;
        timer.startTimeMillis = 0L;
        timer.endTimeMillis = 0L;
        repository.update(timer);
    }

    /**
     * Resume a paused timer
     */
    public void resumeTimer(TimerEntity timer) {
        long now = System.currentTimeMillis();
        timer.startTimeMillis = now;
        timer.endTimeMillis = now + timer.remainingMillis;
        timer.isRunning = true;
        repository.update(timer);
        scheduleAlarm(timer.id, timer.endTimeMillis, timer.label);
    }

    public void cancelTimer(TimerEntity timer) {
        cancelAlarm(timer.id);
        repository.delete(timer);
    }

    /**
     * Schedule the alarm using AlarmManager (RTC_WAKEUP)
     */
    public void scheduleAlarm(int timerId, long triggerAtMillis, String label) {
        Intent intent = new Intent(context, TimerAlarmReceiver.class);
        intent.putExtra(EXTRA_TIMER_ID, timerId);
        intent.putExtra(EXTRA_TIMER_LABEL, label);

        PendingIntent pi = PendingIntent.getBroadcast(
                context,
                timerId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Use exact set for timers
        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pi);
        }
    }

    public void scheduleExistingTimer(TimerEntity timer) {
        scheduleAlarm(timer.id, timer.endTimeMillis, timer.label);
    }

    public void cancelAlarm(int timerId) {
        Intent intent = new Intent(context, TimerAlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(
                context,
                timerId,
                intent,
                PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE
        );
        if (pi != null && alarmManager != null) {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }
}
