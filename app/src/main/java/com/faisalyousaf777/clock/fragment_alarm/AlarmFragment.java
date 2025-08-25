package com.faisalyousaf777.clock.fragment_alarm;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faisalyousaf777.clock.R;

import java.util.Collections;


public class AlarmFragment extends Fragment {

    private AlarmViewModel alarmViewModel;

    public AlarmFragment() {
        // Required empty public constructor
    }


    public static AlarmFragment newInstance() {
        AlarmFragment fragment = new AlarmFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmViewModel = new ViewModelProvider(this).get(AlarmViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvAlarms = view.findViewById(R.id.rv_alarms);

        rvAlarms.setLayoutManager(new LinearLayoutManager(getContext()));
        AlarmAdapter adapter = new AlarmAdapter();
        rvAlarms.setAdapter(adapter);

        alarmViewModel.getAll().observe(getViewLifecycleOwner(), alarms -> {
            if (alarms != null && !alarms.isEmpty()) {
                adapter.setListOfAlarm(alarms);
            } else {
                adapter.setListOfAlarm(Collections.emptyList());
            }
        });

    }

//    public void createAlarm() {
//        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
//        intent.putExtra(AlarmClock.EXTRA_HOUR, 10);
//        intent.putExtra(AlarmClock.EXTRA_MINUTES, 30);
//        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Wake up!");
//        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
//        intent.putExtra(AlarmClock.EXTRA_VIBRATE, true);
////        intent.putExtra(AlarmClock.EXTRA_RINGTONE, "content://media/internal/audio/media/44");
//        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
//            startActivity(intent);
//        } else {
//            Toast.makeText(getContext(), "No Alarm App Found", Toast.LENGTH_SHORT).show();
//        }
//    }
}