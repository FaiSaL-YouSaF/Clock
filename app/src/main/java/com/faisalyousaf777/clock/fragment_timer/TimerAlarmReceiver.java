package com.faisalyousaf777.clock.fragment_timer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.faisalyousaf777.clock.AppDatabase;
import com.faisalyousaf777.clock.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimerAlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "timer_channel";
    private final ExecutorService exec = Executors.newSingleThreadExecutor();

    @Override
    public void onReceive(Context context, Intent intent) {
        int timerId = intent.getIntExtra(TimerManager.EXTRA_TIMER_ID, -1);
        String label = intent.getStringExtra(TimerManager.EXTRA_TIMER_LABEL);

        // create notification channel
        createChannel(context);

        // Build notification
        Intent tapIntent = new Intent(context, /* target Activity to open */ context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName()).getClass());
        // fallback: open main activity
        Intent openApp = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        PendingIntent pendingOpen = PendingIntent.getActivity(context, timerId, openApp,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.outline_timer_24)
                .setContentTitle(label != null && !label.isEmpty() ? label : "Timer finished")
                .setContentText("Time is up")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingOpen);

        NotificationManagerCompat.from(context).notify(timerId, builder.build());

        // update DB: mark as not running and remaining 0
        exec.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(context);
            TimerDao dao = db.timerDao();
            TimerEntity timer = dao.getTimerByIdSync(timerId);
            if (timer != null) {
                timer.isRunning = false;
                timer.remainingMillis = 0L;
                timer.startTimeMillis = 0L;
                timer.endTimeMillis = 0L;
                dao.update(timer);
            }
        });
    }

    private void createChannel(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel(CHANNEL_ID, "Timers", NotificationManager.IMPORTANCE_HIGH);
            ch.setDescription("Timer notifications");
            NotificationManager nm = c.getSystemService(NotificationManager.class);
            if (nm != null) nm.createNotificationChannel(ch);
        }
    }
}

