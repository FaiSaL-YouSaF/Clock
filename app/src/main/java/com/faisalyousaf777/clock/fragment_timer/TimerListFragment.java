package com.faisalyousaf777.clock.fragment_timer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faisalyousaf777.clock.R;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class TimerListFragment extends Fragment {

    private TimerViewModel timerViewModel;
    private TimerManager timerManager;

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

        timerManager = new TimerManager(requireContext());
        timerViewModel = new ViewModelProvider(this).get(TimerViewModel.class);

        rvTimers.setLayoutManager(new LinearLayoutManager(getContext()));
        TimerAdapter timerAdapter = new TimerAdapter();
        rvTimers.setAdapter(timerAdapter);

        timerViewModel.getTimers().observe(getViewLifecycleOwner(), timers -> {
            if (timers != null) {
                List<Timer> timerList = new ArrayList<>();
                for (TimerEntity entity : timers) {
                    Timer timer = new Timer(entity.id, entity.durationMillis, entity.label, entity.isRunning, LocalDateTime.parse(entity.createdAt));
                    timerList.add(timer);
                }
                timerAdapter.setTimers(timerList);
            }
        });


    }
}