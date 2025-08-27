package com.faisalyousaf777.clock;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.faisalyousaf777.clock.fragment.StopwatchFragment;
import com.faisalyousaf777.clock.fragment.TimerFragment;
import com.faisalyousaf777.clock.fragment.WorldClockFragment;
import com.faisalyousaf777.clock.fragment_alarm.AlarmFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigationrail.NavigationRailView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        NavigationRailView navigationRail = findViewById(R.id.navigation_rail);

        Configuration config = getResources().getConfiguration();
        if (config.screenWidthDp < 600) {
            bottomNavigation.setVisibility(NavigationBarView.VISIBLE);
            navigationRail.setVisibility(NavigationBarView.GONE);
        } else {
            bottomNavigation.setVisibility(NavigationBarView.GONE);
            navigationRail.setVisibility(NavigationBarView.VISIBLE);
        }

        NavigationBarView.OnItemSelectedListener itemSelectedListener = item -> {
            if (item.getItemId() == R.id.btnAlarmItem) {
                loadContentFragment(AlarmFragment.newInstance());
            } else if (item.getItemId() == R.id.btnWorldClockItem) {
                loadContentFragment(WorldClockFragment.newInstance());
            } else if (item.getItemId() == R.id.btnStopwatchItem) {
                loadContentFragment(StopwatchFragment.newInstance());
            } else if (item.getItemId() == R.id.btnTimerItem) {
                loadContentFragment(TimerFragment.newInstance());
            } else {
                return false;
            }
            return true;
        };

        bottomNavigation.setOnItemSelectedListener(itemSelectedListener);
        bottomNavigation.setSelectedItemId(R.id.btnAlarmItem);
        navigationRail.setOnItemSelectedListener(itemSelectedListener);
        navigationRail.setSelectedItemId(R.id.btnAlarmItem);
    }

    private void loadContentFragment(Fragment contentFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_fragment, contentFragment)
                .commit();
    }
}