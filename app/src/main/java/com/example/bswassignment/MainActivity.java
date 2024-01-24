package com.example.bswassignment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bswassignment.databinding.ActivityMainBinding;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding = null;
    private GameViewModel viewModel;

    private int previousBox = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this).get(GameViewModel.class);

        binding.topLeftBox.setOnClickListener(this);
        binding.topRightBox.setOnClickListener(this);
        binding.bottomLeftBox.setOnClickListener(this);
        binding.bottomRightBox.setOnClickListener(this);

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(
                new GreyBoxTask(),
                0, 1, TimeUnit.SECONDS
        );

        viewModel.getCurrentScore().observe(
                this,
                score -> {
                    if (score < 1) {
                        viewModel.getCurrentScore().removeObservers(this);
                        executor.shutdown();
                        binding.scoreTextView.setText("GAME OVER");
                    } else
                        binding.scoreTextView.setText("Score: " + score);
                }
        );
    }

    @Override
    public void onClick(View box) {
        int color = Color.TRANSPARENT;
        Drawable background = box.getBackground();
        if (background instanceof ColorDrawable) {
            color = ((ColorDrawable) background).getColor();
        }

        viewModel.updateCurrentScore(color == Color.parseColor("#808080"));
    }

    class GreyBoxTask extends Thread {
        @Override
        public void run() {
            randomBox();
        }
    }

    private void randomBox() {
        int min = 1, max = 4;
        int nextBox = ((int) (Math.random() * (max - min)) + min);

        if (binding != null && nextBox != previousBox) {
            if (nextBox == 1) {
                binding.topLeftBox.setBackgroundColor(0xFF808080);
                binding.topRightBox.setBackgroundColor(0xFF6F9CDE);
                binding.bottomLeftBox.setBackgroundColor(0xFFFFFF00);
                binding.bottomRightBox.setBackgroundColor(0xFF2AAA8A);

            } else if (nextBox == 2) {
                binding.topLeftBox.setBackgroundColor(0xFFFFA500);
                binding.topRightBox.setBackgroundColor(0xFF808080);
                binding.bottomLeftBox.setBackgroundColor(0xFFFFFF00);
                binding.bottomRightBox.setBackgroundColor(0xFF2AAA8A);

            } else if (nextBox == 3) {
                binding.topLeftBox.setBackgroundColor(0xFFFFA500);
                binding.topRightBox.setBackgroundColor(0xFF6F9CDE);
                binding.bottomLeftBox.setBackgroundColor(0xFF808080);
                binding.bottomRightBox.setBackgroundColor(0xFF2AAA8A);

            } else if (nextBox == 4) {
                binding.topLeftBox.setBackgroundColor(0xFFFFA500);
                binding.topRightBox.setBackgroundColor(0xFF6F9CDE);
                binding.bottomLeftBox.setBackgroundColor(0xFFFFFF00);
                binding.bottomRightBox.setBackgroundColor(0xFF808080);

            } else {
                throw new IllegalArgumentException(nextBox + " - box number out of range!");
            }
        }

        previousBox = nextBox;
    }
}