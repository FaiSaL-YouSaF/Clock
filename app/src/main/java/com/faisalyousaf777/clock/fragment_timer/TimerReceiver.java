package com.faisalyousaf777.clock.fragment_timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.faisalyousaf777.clock.AppDatabase;
import com.faisalyousaf777.clock.R;

public class TimerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int timerId = intent.getIntExtra("TIMER_ID", -1);
        String label = intent.getStringExtra("LABEL");

        // Show notification or play sound
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "timer_channel")
                .setSmallIcon(R.drawable.outline_timer_24)
                .setContentTitle("Timer Finished")
                .setContentText(label != null ? label : "Your timer is done!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(timerId, builder.build());

        // Update DB to mark timer as finished
        new Thread(() -> {
            TimerDao dao = AppDatabase.getInstance(context).timerDao();
            Timer timer = dao.getTimerById(timerId);
            if (timer != null) {
                timer.setRunning(false);
                dao.updateTimer(timer);
            }
        }).start();
    }
}

