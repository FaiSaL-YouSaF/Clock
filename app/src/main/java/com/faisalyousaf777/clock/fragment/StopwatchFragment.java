package com.faisalyousaf777.clock.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faisalyousaf777.clock.R;


public class StopwatchFragment extends Fragment {

    public StopwatchFragment() {
        // Required empty public constructor
    }

    public static StopwatchFragment newInstance() {
        StopwatchFragment fragment = new StopwatchFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @SuppressWarnings("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        TextView stopwatchTextView = view.findViewById(R.id.stopwatchTextView);
        stopwatchTextView.setText("Stopwatch Fragment");
        stopwatchTextView.setTextColor(Color.GREEN);
        return view;
    }
}