package com.faisalyousaf777.clock.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faisalyousaf777.clock.AlarmDbHelper;
import com.faisalyousaf777.clock.R;
import com.faisalyousaf777.clock.UpdateAlarm;
import com.faisalyousaf777.clock.adapter.AlarmAdapter;
import com.faisalyousaf777.clock.utility.OnViewHolderClickListener;


public class AlarmFragment extends Fragment implements OnViewHolderClickListener {

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
            rvAlarms.setAdapter(new AlarmAdapter(AlarmDbHelper.getInstance(getContext()).getAllAlarms(), this));
        }
        return view;
    }

    @Override
    public void onViewHolderClick(int position) {
        Intent intent = new Intent(getContext(), UpdateAlarm.class);
        startActivity(intent);
    }

    @Override
    public void onViewHolderLongClick(int position) {
        Toast.makeText(getContext(), "Long Clicked on " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSwitchToggle(int position, boolean isChecked) {
        Toast.makeText(getContext(), "Switch Toggle on " + position + " is " + isChecked, Toast.LENGTH_SHORT).show();
    }

    public void createAlarm() {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_HOUR, 10);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, 30);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Wake up!");
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        intent.putExtra(AlarmClock.EXTRA_VIBRATE, true);
//        intent.putExtra(AlarmClock.EXTRA_RINGTONE, "content://media/internal/audio/media/44");
        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "No Alarm App Found", Toast.LENGTH_SHORT).show();
        }
    }
}