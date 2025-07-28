package com.faisalyousaf777.clock;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.faisalyousaf777.clock.fragment.AlarmFragment;
import com.faisalyousaf777.clock.fragment.StopwatchFragment;
import com.faisalyousaf777.clock.fragment.TimerFragment;
import com.faisalyousaf777.clock.fragment.WorldClockFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
        bottomNavigation = findViewById(R.id.bottom_navigation);

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
    }

    private void loadContentFragment(Fragment contentFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.recyclerViewContainer, contentFragment).commit();
    }
}