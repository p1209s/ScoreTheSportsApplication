package com.example.score.cricket;

public enum CricketRunsAvailable {
    DOT_BALL(0), SINGLE_RUN(1), DOUBLE_RUN(2), TRIPLE_RUN(3), FOUR_RUN(4), SIX_RUN(6);
    public int value;

    CricketRunsAvailable(int value){
        this.value = value;
    }
}