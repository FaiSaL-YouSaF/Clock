package com.faisalyousaf777.clock;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.faisalyousaf777.clock.fragment.AlarmFragment;
import com.faisalyousaf777.clock.fragment.AlarmToolbarFragment;
import com.faisalyousaf777.clock.fragment.StopwatchFragment;
import com.faisalyousaf777.clock.fragment.StopwatchToolbarFragment;
import com.faisalyousaf777.clock.fragment.TimerFragment;
import com.faisalyousaf777.clock.fragment.TimerToolbarFragment;
import com.faisalyousaf777.clock.fragment.WorldClockFragment;
import com.faisalyousaf777.clock.fragment.WorldClockToolbarFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bottomNavigation = findViewById(R.id.bottomNavigation);

        loadToolbarFragment(AlarmToolbarFragment.newInstance());
        loadContentFragment(AlarmFragment.newInstance());


        bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.btnAlarmItem) {
                loadToolbarFragment(AlarmToolbarFragment.newInstance());
                loadContentFragment(AlarmFragment.newInstance());
            } else if (item.getItemId() == R.id.btnWorldClockItem) {
                loadToolbarFragment(WorldClockToolbarFragment.newInstance());
                loadContentFragment(WorldClockFragment.newInstance());
            } else if (item.getItemId() == R.id.btnStopwatchItem) {
                loadToolbarFragment(StopwatchToolbarFragment.newInstance());
                loadContentFragment(StopwatchFragment.newInstance());
            } else if (item.getItemId() == R.id.btnTimerItem) {
                loadToolbarFragment(TimerToolbarFragment.newInstance());
                loadContentFragment(TimerFragment.newInstance());
            } else {
                return false;
            }
            return true;
        });
    }

    private void loadToolbarFragment(Fragment toolbarFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.toolbarContainer, toolbarFragment).commit();
    }

    private void loadContentFragment(Fragment contentFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.recyclerViewContainer, contentFragment).commit();
    }
}