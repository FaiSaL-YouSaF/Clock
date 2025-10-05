package com.faisalyousaf777.clock.fragment_timer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.faisalyousaf777.clock.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;


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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize your views
        MaterialToolbar toolbar = view.findViewById(R.id.toolbar_timer);

        durationInput = v.findViewById(R.id.editDurationSeconds);
        labelInput = v.findViewById(R.id.editLabel);

        view.findViewById(R.id.buttonStart).setOnClickListener(v -> {
            String durStr = durationInput.getText().toString().trim();
            if (TextUtils.isEmpty(durStr)) {
                Toast.makeText(requireContext(), "Enter duration (seconds)", Toast.LENGTH_SHORT).show();
                return;
            }
            long seconds = Long.parseLong(durStr);
            long millis = seconds * 1000L;
            String label = labelInput.getText().toString().trim();

            // Use TimerManager directly so it schedules and persists
            timerManager.startNewTimer(millis, label, id -> {
                // Optionally clear UI on main thread
                requireActivity().runOnUiThread(() -> {
                    durationInput.setText("");
                    labelInput.setText("");
                });
            });
        });

        // Observe Room timers
        viewModel.getTimersLive().observe(getViewLifecycleOwner(), entities -> {
            if (entities == null || entities.isEmpty()) {
                // show Add Timer fragment or view
                showAddTimerView(true);
                adapter.submitList(new ArrayList<>());
            } else {
                showAddTimerView(false);
                // convert list to mutable copy and pass to adapter
                adapter.submitList(entities);
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.container_timer_fragments, fragment)
                .commit();
    }

    private void showAddTimerView(boolean showAdd) {
        View addContainer = requireView().findViewById(R.id.addTimerContainer);
        View listContainer = requireView().findViewById(R.id.recyclerViewTimers);
        if (addContainer != null && listContainer != null) {
            addContainer.setVisibility(showAdd ? View.VISIBLE : View.GONE);
            listContainer.setVisibility(showAdd ? View.GONE : View.VISIBLE);
        }
    }

}