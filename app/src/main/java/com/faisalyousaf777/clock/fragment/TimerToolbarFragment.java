package com.faisalyousaf777.clock.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.faisalyousaf777.clock.R;
import com.google.android.material.appbar.MaterialToolbar;


public class TimerToolbarFragment extends Fragment {

    MaterialToolbar toolbar;

    public TimerToolbarFragment() {
        // Required empty public constructor
    }

    public static TimerToolbarFragment newInstance() {
        TimerToolbarFragment fragment = new TimerToolbarFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer_toolbar, container, false);
        if (getArguments() != null) {
            toolbar = view.findViewById(R.id.toolbar);
            toolbar.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.btnAddItem) {
                    Toast.makeText(getContext(), "Add Timer", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            });
        }
        return view;
    }
}