package com.example.bswassignment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {

    private MutableLiveData<Integer> currentScore = new MutableLiveData<>(1);

    public MutableLiveData<Integer> getCurrentScore() {
        return currentScore;
    }

    public void updateCurrentScore(boolean increase) {
        if (currentScore != null && currentScore.getValue() != null) {
            int updatedScore = 0;
            if (increase)
                updatedScore = currentScore.getValue() + 1;
            currentScore.setValue(updatedScore);
        }
    }

}
