package com.faisalyousaf777.clock.fragment_timer;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.faisalyousaf777.clock.R;

import java.util.Locale;

public class TimerAdapter extends ListAdapter<TimerEntity, TimerAdapter.TimerVH> {

    private final TimerManager timerManager;

    public TimerAdapter(TimerManager manager) {
        super(DIFF);
        this.timerManager = manager;
    }

    @NonNull
    @Override
    public TimerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timer_view_holder, parent, false);
        return new TimerVH(v, timerManager);
    }

    @Override
    public void onBindViewHolder(@NonNull TimerVH holder, int position) {
        holder.bind(getItem(position));
    }

    static final DiffUtil.ItemCallback<TimerEntity> DIFF = new DiffUtil.ItemCallback<TimerEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull TimerEntity oldItem, @NonNull TimerEntity newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull TimerEntity oldItem, @NonNull TimerEntity newItem) {
            return oldItem.durationMillis == newItem.durationMillis
                    && oldItem.isRunning == newItem.isRunning
                    && oldItem.remainingMillis == newItem.remainingMillis
                    && oldItem.endTimeMillis == newItem.endTimeMillis;
        }
    };

    static class TimerVH extends RecyclerView.ViewHolder {

        private final TextView tvLabel;
        private final TextView tvCountdown;
        private final ImageButton btnPlayPause;
        private final ImageButton btnDelete;
        private CountDownTimer liveTimer;
        private TimerEntity current;
        private final TimerManager timerManager;

        TimerVH(@NonNull View itemView, TimerManager manager) {
            super(itemView);
            tvLabel = itemView.findViewById(R.id.tv_timer_title);
            tvCountdown = itemView.findViewById(R.id.tv_time);
            btnPlayPause = itemView.findViewById(R.id.btn_play_pause_timer);
            btnDelete = itemView.findViewById(R.id.btn_delete_timer);
            this.timerManager = manager;
        }

        void bind(TimerEntity entity) {
            // Cancel previous liveTimer
            cancelLiveTimer();

            this.current = entity;
            tvLabel.setText(entity.label != null && !entity.label.isEmpty() ? entity.label : "Timer");
            updateCountdownText(calculateRemaining(entity));

            // Set icon based on running state
            btnPlayPause.setImageResource(entity.isRunning ? R.drawable.pause_24dp : R.drawable.play_arrow_24dp);

            btnPlayPause.setOnClickListener(v -> {
                if (current.isRunning) {
                    // pause
                    timerManager.pauseTimer(current);
                    cancelLiveTimer();
                    btnPlayPause.setImageResource(R.drawable.play_arrow_24dp);
                    updateCountdownText(current.remainingMillis); // remainingMillis updated in DB via manager
                } else {
                    // resume
                    timerManager.resumeTimer(current);
                    startLiveCountdown(current);
                    btnPlayPause.setImageResource(R.drawable.pause_24dp);
                }
            });

            btnDelete.setOnClickListener(v -> {
                timerManager.cancelTimer(current);
            });

            // If entity indicates running and endTime in future -> start live countdown
            if (entity.isRunning) {
                startLiveCountdown(entity);
            } else {
                // show remaining if paused
                updateCountdownText(entity.remainingMillis);
            }
        }

        private long calculateRemaining(TimerEntity e) {
            if (e.isRunning) {
                long now = System.currentTimeMillis();
                long remaining = e.endTimeMillis - now;
                return Math.max(0L, remaining);
            } else {
                return e.remainingMillis;
            }
        }

        private void updateCountdownText(long millis) {
            long seconds = millis / 1000;
            long hrs = seconds / 3600;
            long mins = (seconds % 3600) / 60;
            long secs = seconds % 60;
            if (hrs > 0) {
                tvCountdown.setText(String.format(Locale.getDefault(), "%d:%02d:%02d", hrs, mins, secs));
            } else {
                tvCountdown.setText(String.format(Locale.getDefault(), "%02d:%02d", mins, secs));
            }
        }

        private void startLiveCountdown(TimerEntity e) {
            cancelLiveTimer();
            long remaining = calculateRemaining(e);
            if (remaining <= 0) {
                updateCountdownText(0);
                return;
            }

            liveTimer = new CountDownTimer(remaining, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateCountdownText(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    updateCountdownText(0);
                    btnPlayPause.setImageResource(R.drawable.play_arrow_24dp); // finished
                }
            };
            liveTimer.start();
        }

        private void cancelLiveTimer() {
            if (liveTimer != null) {
                liveTimer.cancel();
                liveTimer = null;
            }
        }
    }
}
