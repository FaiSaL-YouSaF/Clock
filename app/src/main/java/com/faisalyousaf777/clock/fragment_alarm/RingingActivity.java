package com.faisalyousaf777.clock.fragment_alarm;

import android.app.KeyguardManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.faisalyousaf777.clock.R;
import com.faisalyousaf777.clock.utils.VibrationHelper;

public class RingingActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private PowerManager.WakeLock wakeLock;
    private int alarmId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringing);

        setupWindowProperties();
        acquireWakeLock();

        alarmId = getIntent().getIntExtra("ALARM_ID", -1);
        String alarmLabel = getIntent().getStringExtra("ALARM_MESSAGE");

        TextView labelTextView = findViewById(R.id.alarm_label);
        Button dismissButton = findViewById(R.id.dismiss_button);

        labelTextView.setText(alarmLabel != null && !alarmLabel.isEmpty() ? alarmLabel : "It's time!");

        startAlarmServices();

        dismissButton.setOnClickListener(v -> stopAlarmAndFinish());
    }

    private void setupWindowProperties() {
        // Turn on screen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setTurnScreenOn(true);
            setShowWhenLocked(true);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        }

        // Dismiss keyguard for devices that support it
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        if (keyguardManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            keyguardManager.requestDismissKeyguard(this, null);
        }
    }

    private void acquireWakeLock() {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        if (powerManager != null) {
            wakeLock = powerManager.newWakeLock(
                    PowerManager.FULL_WAKE_LOCK |
                            PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.ON_AFTER_RELEASE,
                    "AlarmApp::AlarmWakeLock"
            );
            wakeLock.acquire(10 * 60 * 1000L /*10 minutes*/);
        }
    }

    private void startAlarmServices() {
        startAlarmSound();
        VibrationHelper.startAlarmVibration(this);
        startForegroundService();
    }

    private void startAlarmSound() {
        try {
            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(this, alarmUri);
            mediaPlayer.setLooping(true);

            // Set up error listener
            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                stopAlarmSound();
                return true;
            });

            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (Exception e) {
            e.printStackTrace();
            // Fallback to system default
            try {
                RingtoneManager.getRingtone(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)).play();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void startForegroundService() {
        try {
            Intent serviceIntent = new Intent(this, AlarmForegroundService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void stopAlarmAndFinish() {
        stopAlarm();
        stopForegroundService();
        finishAndRemoveTask();
    }

    private void stopAlarm() {
        stopAlarmSound();
        VibrationHelper.stopVibration(this);
        releaseWakeLock();
    }

    private void stopAlarmSound() {
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mediaPlayer = null;
        }
    }

    private void stopForegroundService() {
        try {
            stopService(new Intent(this, AlarmForegroundService.class));
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void releaseWakeLock() {
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
            wakeLock = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Keep the alarm running in background
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAlarm();
    }
}


