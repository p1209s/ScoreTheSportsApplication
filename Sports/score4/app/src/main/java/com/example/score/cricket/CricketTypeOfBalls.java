package com.example.score.cricket;

public enum CricketTypeOfBalls {
    NOTPALYED("nP"), DOT_BALL("•"), WIDEBALL("wd"), BYES("b"), LEGBYES("lb"), NO_BALL("nb"), WICKET("W"),
    ONE("1"), TWO("2"), THREE("3"), FOUR("4"), SIX("6");

    public String value;
    CricketTypeOfBalls(String value) {
        this.value = value;
    }
}