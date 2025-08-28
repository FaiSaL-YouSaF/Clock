package com.faisalyousaf777.clock.fragment_alarm.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.faisalyousaf777.clock.R;
import com.faisalyousaf777.clock.fragment_alarm.data.Alarm;
import com.faisalyousaf777.clock.fragment_alarm.viewmodel.AlarmViewModel;
import com.faisalyousaf777.clock.utils.MaterialNumberPicker;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class AddEditAlarmActivity extends AppCompatActivity {

    public static final String EXTRA_ALARM_ID = "alarm_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_alarm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        MaterialToolbar toolbar = findViewById(R.id.toolbar_add_edit_alarm);
        MaterialNumberPicker npHours = findViewById(R.id.number_picker_hours);
        MaterialNumberPicker npMinutes = findViewById(R.id.number_picker_minutes);
        MaterialNumberPicker npIsAm = findViewById(R.id.number_picker_is_am);
        TextInputEditText etLabel = findViewById(R.id.et_label);


        AlarmViewModel alarmViewModel = new ViewModelProvider(this).get(AlarmViewModel.class);
        int alarmId = getIntent().getIntExtra(EXTRA_ALARM_ID, -1);
        if (alarmId != -1) {
            toolbar.setTitle("Update Alarm");

            alarmViewModel.findById(alarmId).observe(this, alarm -> {
                if (alarm != null) {
                    npHours.setValue(alarm.getHours());
                    npMinutes.setValue(alarm.getMinutes());
                    etLabel.setText(alarm.getLabel());
                }
            });
        }


        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_add_alarm) {
                int hours = npHours.getValue();
                int minutes = npMinutes.getValue();
                String label = etLabel.getText() != null ? etLabel.getText().toString() : "";
                if (npIsAm.getValue() == 0 && hours != 12) {
                    hours += 12;
                } else if (npIsAm.getValue() == 1 && hours == 12) {
                    hours = 0;
                }

                if (alarmId == -1) {
                    // Insert new alarm
                    Alarm alarm = new Alarm(hours, minutes, true, label);
                    alarmViewModel.insert(alarm);
                } else {
                    // Update existing alarm
                    Alarm alarm = new Alarm(alarmId, hours, minutes, true, label);
                    alarmViewModel.update(alarm);
                }
                finish();
                return true;
            }
            return false;
        });


    }
}