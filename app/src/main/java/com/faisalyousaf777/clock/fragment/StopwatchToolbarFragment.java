package com.faisalyousaf777.clock.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.faisalyousaf777.clock.R;
import com.google.android.material.appbar.MaterialToolbar;


public class StopwatchToolbarFragment extends Fragment {

    public StopwatchToolbarFragment() {
        // Required empty public constructor
    }

    public static StopwatchToolbarFragment newInstance() {
        StopwatchToolbarFragment fragment = new StopwatchToolbarFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stopwatch_toolbar, container, false);
    }
}