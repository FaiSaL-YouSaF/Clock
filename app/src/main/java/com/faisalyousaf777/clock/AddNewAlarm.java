package com.faisalyousaf777.clock;

import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddNewAlarm extends AppCompatActivity {

    private NumberPicker hourNumberPicker, minuteNumberPicker, amPmNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_alarm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        hourNumberPicker = findViewById(R.id.hourNumberPicker);
        minuteNumberPicker = findViewById(R.id.minuteNumberPicker);
        amPmNumberPicker = findViewById(R.id.amPmNumberPicker);

        String [] arrayOfMinutes = {
                "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
                "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
                "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
        String [] arrayOfAmPm = {"AM", "PM"};

        hourNumberPicker.setMinValue(1);
        hourNumberPicker.setMaxValue(12);
        minuteNumberPicker.setDisplayedValues(arrayOfMinutes);
        minuteNumberPicker.setMinValue(0);
        minuteNumberPicker.setMaxValue(59);
        amPmNumberPicker.setDisplayedValues(arrayOfAmPm);
        amPmNumberPicker.setMinValue(0);
        amPmNumberPicker.setMaxValue(1);
    }
}