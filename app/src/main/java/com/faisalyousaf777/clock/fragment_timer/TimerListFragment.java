package com.faisalyousaf777.clock.fragment_timer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faisalyousaf777.clock.R;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class TimerListFragment extends Fragment {

    public TimerListFragment() {
        // Required empty public constructor
    }

    public static TimerListFragment newInstance() {
        TimerListFragment fragment = new TimerListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize your views
        RecyclerView rvTimers = view.findViewById(R.id.rv_timers);

        rvTimers.setLayoutManager(new LinearLayoutManager(getContext()));
        TimerAdapter timerAdapter = new TimerAdapter();
        rvTimers.setAdapter(timerAdapter);

        timerAdapter.setTimers(getSampleTimers());
    }

    // Sample data for timers
    public static List<Timer> getSampleTimers() {
        List<Timer> timers = new ArrayList<>();
        timers.add(new Timer(1, 60000, "Workout", TimerState.FINISHED, 60000, LocalDateTime.now().minusDays(1)));
        timers.add(new Timer(2, 300000, "Study", TimerState.RUNNING, 250000, LocalDateTime.now().minusHours(2)));
        timers.add(new Timer(3, 120000, "Break", TimerState.PAUSED, 60000, LocalDateTime.now().minusMinutes(30)));
        timers.add(new Timer(4, 90000, "Meditation", TimerState.FINISHED, 90000, LocalDateTime.now().minusDays(2)));
        timers.add(new Timer(5, 180000, "Cooking", TimerState.PAUSED, 120000, LocalDateTime.now().minusHours(5)));
        timers.add(new Timer(6, 240000, "Reading", TimerState.RUNNING, 200000, LocalDateTime.now().minusMinutes(90)));
        timers.add(new Timer(7, 360000, "Exercise", TimerState.FINISHED, 360000, LocalDateTime.now().minusDays(3)));
        timers.add(new Timer(8, 150000, "Yoga", TimerState.PAUSED, 100000, LocalDateTime.now().minusHours(6)));
        timers.add(new Timer(9, 210000, "Nap", TimerState.RUNNING, 180000, LocalDateTime.now().minusMinutes(45)));
        timers.add(new Timer(10, 60000, "Tea Break", TimerState.FINISHED, 60000, LocalDateTime.now().minusDays(4)));
        timers.add(new Timer(11, 300000, "Project", TimerState.PAUSED, 250000, LocalDateTime.now().minusHours(8)));
        timers.add(new Timer(12, 120000, "Call", TimerState.RUNNING, 60000, LocalDateTime.now().minusMinutes(15)));
        timers.add(new Timer(13, 180000, "Cleaning", TimerState.FINISHED, 180000, LocalDateTime.now().minusDays(5)));
        return timers;
    }
}