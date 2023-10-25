package com.example.score.cricket;

public enum CricketWicketsType {
    CAUGHT("Caught"), BOWLED("Bowled"), LBW("LBW"), RUN_OUT("Run OUT"), STUMPED("Stumped"),
    HIT_WICKET("Hit Wicket");

    public String value;

    CricketWicketsType(String value) {
        this.value = value;
    }
}




