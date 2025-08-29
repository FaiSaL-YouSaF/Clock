package com.faisalyousaf777.clock.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;

import com.faisalyousaf777.clock.R;
import com.google.android.material.color.MaterialColors;

import java.lang.reflect.Field;
import java.util.Locale;

public class MaterialNumberPicker extends NumberPicker {

    public static final int MODE_HOURS_12 = 0;
    public static final int MODE_MINUTES_SECONDS = 1;
    public static final int MODE_IS_AM = 2;
    public static final int MODE_HOURS_24 = 3;


    public MaterialNumberPicker(Context context) {
        super(context);
        init(context, null);
    }

    public MaterialNumberPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MaterialNumberPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        int dividerColor = MaterialColors.getColor(this, com.google.android.material.R.attr.colorPrimary);
        int pickerMode = -1;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomNumberPicker);
            dividerColor = a.getColor(R.styleable.CustomNumberPicker_dividerColor, dividerColor);
            pickerMode = a.getInt(R.styleable.CustomNumberPicker_pickerMode, -1);
            a.recycle();
        }

        applyMaterial3Style(dividerColor);

        // Set default ranges based on mode
        if (pickerMode != -1) {
            setupMode(pickerMode, context);
        }
    }

    private void applyMaterial3Style(int dividerColor) {
        // Change divider color with reflection
        try {
            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field field : pickerFields) {
                if ("mSelectionDivider".equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(this, new ColorDrawable(dividerColor));
                    break;
                }
            }
        } catch (Exception ignored) {}

        // Fix text appearance
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            if (getChildAt(i) instanceof EditText) {
                EditText editText = (EditText) getChildAt(i);
                editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // Material3 BodyLarge
                editText.setTextColor(MaterialColors.getColor(this, com.google.android.material.R.attr.colorOnSurface));
                editText.setFocusable(false);
            }
        }
    }

    private void setupMode(int mode, Context context) {
        switch (mode) {
            case MODE_HOURS_12:
                setMinValue(1);
                setMaxValue(12);
                break;

            case MODE_MINUTES_SECONDS:
                setMinValue(0);
                setMaxValue(59);
                setFormatter(value -> String.format(Locale.US, "%02d", value));
                break;

            case MODE_IS_AM:
                setMinValue(0);
                setMaxValue(1);
                String[] ampm = new String[]{
                        context.getString(R.string.txt_pm),
                        context.getString(R.string.txt_am)
                };
                setDisplayedValues(ampm);
                break;
            case MODE_HOURS_24:
                setMinValue(0);
                setMaxValue(23);
                setFormatter(value -> String.format(Locale.US, "%02d", value));
                break;
        }
    }
}

