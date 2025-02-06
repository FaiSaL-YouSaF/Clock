package com.faisalyousaf777.clock.utility;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomRingtoneManager {
    public static final String TAG = "CustomRingtoneManager";
    public static final String RINGTONE_DIRECTORY = "/custom_ringtones";
    public static final String RINGTONE_EXTENSION = ".mp3";

    private final Context context;
    private final Map<String, Uri> ringtoneCache;

    public CustomRingtoneManager(Context context) {
        this.context = context.getApplicationContext();
        this.ringtoneCache = new HashMap<>();
    }

    public Uri saveCustomUri(Uri ringtoneUri, String ringtoneTitle) {
        File ringtoneFile = createRingtoneFile(ringtoneTitle);
        if (ringtoneFile == null) {
            Log.e(TAG, "saveCustomUri: Failed to create ringtone file.");
            return null;
        }
        try (
                InputStream inputStream = context.getContentResolver().openInputStream(ringtoneUri);
                FileOutputStream fileOutputStream = new FileOutputStream(ringtoneFile)
                ){
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            Log.d(TAG, "saveCustomUri: Ringtone file saved successfully." + ringtoneFile.getAbsolutePath());
            Uri savedUri = Uri.fromFile(ringtoneFile);
            ringtoneCache.put(ringtoneTitle, savedUri);
            return savedUri;
        } catch (final IOException ex) {
            Log.e(TAG, "saveCustomUri: Failed to save ringtone file." + ex.getMessage(), ex);
            return null;
        }
    }


    public Uri getCustomRingtoneUri(String ringtoneName) {
        if (ringtoneCache.containsKey(ringtoneName)) {
            return ringtoneCache.get(ringtoneName); // Return cached URI
        }

        File ringtoneFile = new File(getRingtoneDirectory(), ringtoneName + ".mp3");
        if (ringtoneFile.exists()) {
            Uri ringtoneUri = Uri.fromFile(ringtoneFile);
            ringtoneCache.put(ringtoneName, ringtoneUri); // Cache the URI
            return ringtoneUri;
        }
        return null;
    }
    

    public boolean deleteCustomRingtone(String ringtoneName) {
        File ringtoneFile = new File(getRingtoneDirectory(), ringtoneName + ".mp3");
        if (ringtoneFile.exists()) {
            boolean deleted = ringtoneFile.delete();
            if (deleted) {
                ringtoneCache.remove(ringtoneName); // Remove from cache
                Log.d(TAG, "Ringtone deleted: " + ringtoneFile.getAbsolutePath());
                return true;
            }
        }
        return false;
    }


    public List<String> getAllCustomRingtoneNames() {
        List<String> ringtoneNames = new ArrayList<>();
        File directory = getRingtoneDirectory();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(RINGTONE_EXTENSION)) {
                    ringtoneNames.add(file.getName().replace(RINGTONE_EXTENSION, ""));
                }
            }
        }
        return ringtoneNames;
    }


    private File createRingtoneFile(String ringtoneName) {
        File directory = getRingtoneDirectory();
        if (!directory.exists() && !directory.mkdirs()) {
            Log.e(TAG, "Failed to create ringtone directory.");
            return null;
        }
        return new File(directory, ringtoneName + RINGTONE_EXTENSION);
    }


    private File getRingtoneDirectory() {
        return new File(context.getFilesDir(), RINGTONE_DIRECTORY);
    }


}
