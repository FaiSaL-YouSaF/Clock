package com.faisalyousaf777.clock.fragment_timer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.faisalyousaf777.clock.R;
import com.faisalyousaf777.clock.utils.MaterialNumberPicker;
import com.google.android.material.appbar.MaterialToolbar;


public class TimerFragment extends Fragment {

    public TimerFragment() {
        // Required empty public constructor
    }

    public static TimerFragment newInstance() {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize your views
        MaterialToolbar toolbar = view.findViewById(R.id.toolbar_timer);
        MaterialNumberPicker npHours = view.findViewById(R.id.number_picker_hours);
        MaterialNumberPicker npMinutes = view.findViewById(R.id.number_picker_minutes);
        MaterialNumberPicker npSeconds = view.findViewById(R.id.number_picker_seconds);
        TextView tvRingtone = view.findViewById(R.id.tv_ringtone);

    }
}