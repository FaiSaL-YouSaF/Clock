package com.faisalyousaf777.clock.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.faisalyousaf777.clock.R;
import com.faisalyousaf777.clock.adapter.AlarmAdapter;
import com.faisalyousaf777.clock.entity.Alarm;

import java.util.List;


public class AlarmFragment extends Fragment {

    RecyclerView rvAlarms;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        if (getArguments() != null) {
            rvAlarms = view.findViewById(R.id.rvAlarms);
            rvAlarms.setLayoutManager(new LinearLayoutManager(getContext()));
            rvAlarms.setAdapter(new AlarmAdapter(sampleData()));
            Toast.makeText(getContext(), "AdapterSet", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    public List<Alarm> sampleData() {
        return List.of(
                new Alarm(5, 34, true),
                new Alarm(1, 48, false),
                new Alarm(2, 48, false),
                new Alarm(6, 48, true),
                new Alarm(3, 48, false),
                new Alarm(4, 28, false),
                new Alarm(3, 38, true),
                new Alarm(7, 12, false),
                new Alarm(11, 10, false),
                new Alarm(10, 25, true),
                new Alarm(2, 32, false),
                new Alarm(8, 59, false),
                new Alarm(4, 49, true),
                new Alarm(5, 44, false),
                new Alarm(7, 18, false),
                new Alarm(9, 20, true),
                new Alarm(4, 48, false),
                new Alarm(3, 48, false),
                new Alarm(2, 13, true),
                new Alarm(12, 23, false)
        );
    }
}