package com.faisalyousaf777.clock.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.faisalyousaf777.clock.AlarmDbHelper;
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
            rvAlarms.setAdapter(new AlarmAdapter(AlarmDbHelper.getInstance(getContext()).getAllAlarms()));
        }
        return view;
    }
}