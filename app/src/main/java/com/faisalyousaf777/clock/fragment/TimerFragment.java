package com.faisalyousaf777.clock.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.faisalyousaf777.clock.R;


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

    @SuppressWarnings("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        AppCompatTextView timerTextView = view.findViewById(R.id.timerTextView);
        timerTextView.setText("Timer Fragment");
        timerTextView.setTextColor(Color.CYAN);
        return view;
    }
}