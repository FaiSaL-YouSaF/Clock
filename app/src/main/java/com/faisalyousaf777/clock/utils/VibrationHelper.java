package com.faisalyousaf777.clock.utils;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;

public class VibrationHelper {

    public static void startAlarmVibration(Context context) {
        try {
            Vibrator vibrator = getVibrator(context);

            if (vibrator != null && vibrator.hasVibrator() && hasVibrationPermission(context)) {
                long[] pattern = {0, 1000, 1000}; // Wait 0, vibrate 1000, sleep 1000

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    VibrationEffect effect = VibrationEffect.createWaveform(pattern, 0);
                    vibrator.vibrate(effect);
                } else {
                    // For Android 7.1 and below, use the deprecated method as fallback
                    vibrator.vibrate(pattern, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopVibration(Context context) {
        try {
            Vibrator vibrator = getVibrator(context);
            if (vibrator != null) {
                vibrator.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Vibrator getVibrator(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                VibratorManager vibratorManager = (VibratorManager) context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
                return vibratorManager != null ? vibratorManager.getDefaultVibrator() : null;
            } else {
                return (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean hasVibrationPermission(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                return context.checkSelfPermission(android.Manifest.permission.VIBRATE)
                        == android.content.pm.PackageManager.PERMISSION_GRANTED;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

