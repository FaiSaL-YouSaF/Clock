package com.faisalyousaf777.clock.fragment_alarm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faisalyousaf777.clock.R;
import com.faisalyousaf777.clock.fragment_alarm.viewmodel.AlarmViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Collections;


public class AlarmFragment extends Fragment {

    private AlarmViewModel alarmViewModel;

    private final ActivityResultLauncher<String> notificationPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Log.d("Permission", "Notification permission granted");
                } else {
                    Log.e("Permission", "Notification permission denied");
                }
            });

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
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        // Ask for notification permission when Alarm tab opens
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialToolbar toolbar = view.findViewById(R.id.toolbar_alarms);
        RecyclerView rvAlarms = view.findViewById(R.id.rv_alarms);

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_add_alarm) {
                // Handle add alarm action
                Log.d("AlarmFragment", "Add Alarm clicked");
                return true;
            } else if (item.getItemId() == R.id.action_edit_alarm) {
                Log.d("AlarmFragment", "Add Alarm clicked");
                return true;
            } else if (item.getItemId() == R.id.action_settings) {
                Log.d("AlarmFragment", "Settings clicked");
                return true;
            }
            return false;
        });

        rvAlarms.setLayoutManager(new LinearLayoutManager(view.getContext()));
        AlarmAdapter adapter = new AlarmAdapter(alarmViewModel);
        rvAlarms.setAdapter(adapter);

        alarmViewModel.getAll().observe(getViewLifecycleOwner(), alarms -> {
            if (alarms != null && !alarms.isEmpty()) {
                adapter.setListOfAlarm(alarms);
                Log.d("Load Alarms", "onViewCreated: Alarms loaded: " + alarms.size());
            } else {
                adapter.setListOfAlarm(Collections.emptyList());
                Log.e("Load Alarms", "onViewCreated: No alarms found");
            }
        });
    }
}