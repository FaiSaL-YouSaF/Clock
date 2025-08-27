package com.faisalyousaf777.clock;

import androidx.room.TypeConverter;

public class Converter {

    @TypeConverter
    public static String fromBooleanArray(boolean[] array) {
        if (array == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (boolean b : array) {
            sb.append(b ? '1' : '0');
        }
        return sb.toString();
    }

    @TypeConverter
    public static boolean[] toBooleanArray(String data) {
        if (data == null) {
            return new boolean[0];
        }
        boolean[] array = new boolean[data.length()];
        for (int i = 0; i < data.length(); i++) {
            array[i] = data.charAt(i) == '1';
        }
        return array;
    }

    @TypeConverter
    public static String fromIntArray(int[] array) {
        if (array == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    @TypeConverter
    public static int[] toIntArray(String data) {
        if (data == null || data.isEmpty()) {
            return new int[0];
        }
        String[] parts = data.split(",");
        int[] array = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            array[i] = Integer.parseInt(parts[i]);
        }
        return array;
    }
}
