package com.faisalyousaf777.clock.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.faisalyousaf777.clock.AddNewAlarm;
import com.faisalyousaf777.clock.R;
import com.google.android.material.appbar.MaterialToolbar;


public class AlarmToolbarFragment extends Fragment {

    private MaterialToolbar toolbar;

    public AlarmToolbarFragment() {
    }

    public static AlarmToolbarFragment newInstance() {
        AlarmToolbarFragment fragment = new AlarmToolbarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm_toolbar, container, false);
        if (getArguments() != null) {
            toolbar = view.findViewById(R.id.toolbar);
            toolbar.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.btnAddItem) {
                    Intent intent = new Intent(getContext(), AddNewAlarm.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.btnEditItem) {
                    // Open Edit Alarms Fragment
                    Toast.makeText(getContext(), "Edit Alarms", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.btnSettings) {
                    // Open Settings Fragment
                    Toast.makeText(getContext(), "Settings", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            });
        }
        return view;
    }
}