package com.example.score.cricket;

public class BallBowled {

    private CricketTypeOfBalls mTypeOfBall;
    private String mBallValue;

    public BallBowled(CricketTypeOfBalls typeOfBall){
        this.mTypeOfBall = typeOfBall;
        mBallValue = mTypeOfBall.value;
    }

    public void setBallPlayed(CricketTypeOfBalls ballPlayed){
        this.mTypeOfBall = ballPlayed;
        mBallValue = mTypeOfBall.value;
    }

    public void setBallPlayedRunScored(int runs){
        this.mBallValue = String.valueOf(runs);
    }

    public String getBallPlayed(){
        return mBallValue;
    }
}

