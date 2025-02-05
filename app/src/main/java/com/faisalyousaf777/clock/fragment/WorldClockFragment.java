package com.faisalyousaf777.clock.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faisalyousaf777.clock.R;


public class WorldClockFragment extends Fragment {

    public WorldClockFragment() {
        // Required empty public constructor
    }

    public static WorldClockFragment newInstance() {
        WorldClockFragment fragment = new WorldClockFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @SuppressWarnings("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_world_clock, container, false);
        TextView worldClockTextView = view.findViewById(R.id.worldClockTextView);
        worldClockTextView.setText("World Clock Fragment");
        worldClockTextView.setTextColor(Color.BLUE);
        return view;
    }
}