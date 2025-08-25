package com.faisalyousaf777.clock;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddNewAlarm extends AppCompatActivity {

    private AppCompatButton discardButton, saveButton;
    private NumberPicker hourNumberPicker, minuteNumberPicker, amPmNumberPicker;
    private AppCompatEditText labelEditText;
    private AppCompatTextView ringtoneTextView, alertModeTextView;
    private AlarmDbHelper alarmDbHelper;

    @SuppressWarnings("MissingInflatedId")
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
        discardButton = findViewById(R.id.discardButton);
        saveButton = findViewById(R.id.saveButton);
        hourNumberPicker = findViewById(R.id.hourNumberPicker);
        minuteNumberPicker = findViewById(R.id.minuteNumberPicker);
        amPmNumberPicker = findViewById(R.id.amPmNumberPicker);
        labelEditText = findViewById(R.id.labelEditText);
        ringtoneTextView = findViewById(R.id.ringtoneTextView);
        alertModeTextView = findViewById(R.id.alertModeTextView);

        String [] arrayOfMinutes = {
                "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
                "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
                "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
        String [] arrayOfAmPm = {"PM", "AM"};

        hourNumberPicker.setMinValue(1);
        hourNumberPicker.setMaxValue(12);
        minuteNumberPicker.setDisplayedValues(arrayOfMinutes);
        minuteNumberPicker.setMinValue(0);
        minuteNumberPicker.setMaxValue(59);
        amPmNumberPicker.setDisplayedValues(arrayOfAmPm);
        amPmNumberPicker.setMinValue(0);
        amPmNumberPicker.setMaxValue(1);

        ActivityResultLauncher<Intent> ringtonePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result != null && result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    try {
                        Uri ringtoneUri = result.getData().getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                        if (ringtoneUri != null) {
                            Ringtone ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
                            ringtoneTextView.setText(ringtone.getTitle(this));
                        }
                    } catch (final Exception ex) {
                        Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ringtoneTextView.setOnClickListener(v -> {
            Intent ringtoneIntent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
            ringtoneIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
            ringtonePickerLauncher.launch(ringtoneIntent);
        });


        saveButton.setOnClickListener(v -> {
//            Alarm alarm = new Alarm(hourNumberPicker.getValue(), minuteNumberPicker.getValue(), amPmNumberPicker.getValue() == 1, labelEditText.getText().toString(), "Default", false, );
//            alarmDbHelper = AlarmDbHelper.getInstance(this);
//            alarmDbHelper.insertAlarm(alarm);
            Toast.makeText(this, "Alarm saved to DB", Toast.LENGTH_SHORT).show();
            finish();
        });

        discardButton.setOnClickListener(v -> {
            finish();
        });
    }
}