package com.faisalyousaf777.clock.fragment_timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Re-schedule any running timers
            ExecutorService exec = Executors.newSingleThreadExecutor();
            exec.execute(() -> {
                TimerRepository repo = new TimerRepository(context);
                TimerManager manager = new TimerManager(context);
                List<TimerEntity> timers = repo.getAllTimersSync();
                long now = System.currentTimeMillis();

                for (TimerEntity t : timers) {
                    if (t.isRunning && t.endTimeMillis > now) {
                        manager.scheduleExistingTimer(t);
                    } else if (t.isRunning && t.endTimeMillis <= now) {
                        // expired while phone was off -> mark finished and optionally notify
                        t.isRunning = false;
                        t.remainingMillis = 0L;
                        t.startTimeMillis = 0L;
                        t.endTimeMillis = 0L;
                        repo.update(t);
                    }
                }
            });
        }
    }
}


