package com.example.score.cricket;

import java.util.ArrayList;

public class CricketInnings {
    private int mRunScored;
    private final CricketRuns mRunType;
    private final String mInningOf;
    private String mWicketFalenType;
    private final int mTotalOvers;
    private int mCurrentOver = 0;
    private int mNumOfBallsPlayed = 0;
    private final int mWicketNeeded;
    private int mCurrentNumOfWickets;
    private int mExtraRunOfBall;
    private final ArrayList<String>[] mOvers;
    private boolean overDone;
    private boolean inningDone;
    private final boolean teamChasing;
    private final int[] runsOfThatOver;
    private final int[] runsAfterOver;
    private int mRunsNeededToWin;

    public CricketInnings(String inningOf, int numOfOvers, int numOfPlayers, boolean teamChasing){
        this.mInningOf = inningOf;
        this.mTotalOvers = numOfOvers;
        this.mWicketNeeded = numOfPlayers - 1;
        this.teamChasing = teamChasing;

        mRunType = new CricketRuns();
        mOvers = new ArrayList[numOfOvers];
        runsOfThatOver = new int[numOfOvers];
        runsAfterOver = new int[numOfOvers];
        for(int i = 0; i < mOvers.length; i++) {
            runsOfThatOver[i] = 0;
            runsAfterOver[i] = 0;
        }
        inningDone = false;
        for(int i = 0; i < mOvers.length; i++) mOvers[i] = new ArrayList<>();
    }

    public void setRunsNeededToWin(int runsMade){
        if(teamChasing) {
            this.mRunsNeededToWin = runsMade + 1;
        }
    }

    public void setThisOverOverview(int overNum, BallBowled ballBowled){
        switch (ballBowled.getBallPlayed()) {
            case "b":
            case "lb":
                switch (mExtraRunOfBall){
                    case 2:
                    case 3:
                    case 4:
                        mOvers[overNum].add(getExtraRunOfBall() + ballBowled.getBallPlayed());
                        break;
                    default:
                        mOvers[overNum].add(ballBowled.getBallPlayed());
                }
                break;
            case "nb":
            case "wd":
                switch (mExtraRunOfBall){
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        mOvers[overNum].add(ballBowled.getBallPlayed() + "+" + getExtraRunOfBall());
                        break;
                    default:
                        mOvers[overNum].add(ballBowled.getBallPlayed());
                }
                break;
            case "W":
                mOvers[overNum].add(ballBowled.getBallPlayed() + "(" + getFallenWicketType() + ")");
                break;
            default:
                mOvers[overNum].add(ballBowled.getBallPlayed());
        }

    }

    public String getOverOverview(int over, boolean totalVersion){
        StringBuilder thisOver = new StringBuilder();

        if (totalVersion) {
            thisOver.append("");
            String spliter = "";

            for (String i : mOvers[over]) {
                thisOver.append(spliter).append(i);
                spliter = " + ";
            }
            thisOver.append("");
        }else{
            thisOver.append("[ ");
            String spliter = "";

            //try {
                for (String i : mOvers[over]) {
                    thisOver.append(spliter).append(i);
                    spliter = ", ";
                }
                thisOver.append(" ]");
            //}catch(Exception e){ }
        }

        return thisOver.toString();
    }

    public void setExtraRunOfBall(CricketRunsAvailable runOfBall){
        this.mExtraRunOfBall = runOfBall.value;
    }

    private int getExtraRunOfBall(){
        return mExtraRunOfBall;
    }

    public void setFallenWicket(CricketWicketsType wicket){
        mWicketFalenType = wicket.value;
        mCurrentNumOfWickets++;
    }

    private String getFallenWicketType(){
        return mWicketFalenType;
    }

    public void setRunScored(CricketRunsAvailable getRun){
        mRunType.setNumberOfRuns(getRun);
        runsOfThatOver[mCurrentOver] = runsOfThatOver[mCurrentOver] + mRunType.getRun();
    }

    public void setExtraRuns(int extra){
        mRunType.setExtraRuns(extra);
    }

    public int getTotalRunScored(){
        return mRunType.getTotalRuns();
    }

    public int getNumOfBallsPlayed(){
        return mNumOfBallsPlayed;
    }

    public int getCurrentOver(){
        return mCurrentOver;
    }

    public int getCurrentNumOfWickets(){
        return mCurrentNumOfWickets;
    }

    public void legalBallsPlayed(){
        if(mNumOfBallsPlayed < 6) {
            mNumOfBallsPlayed++;
            overDone = false;
        }
        if(mNumOfBallsPlayed == 6){
            overDone = true;
        }
    }

    public void setOverDone(boolean done){
        if(done) {
            mNumOfBallsPlayed = 0;
            runsAfterOver[mCurrentOver] = getTotalRunScored();
            mCurrentOver++;
        }else {
            mNumOfBallsPlayed = 5;
        }
        overDone = false;
    }

    public boolean isOverComplete(){
        return overDone;
    }

    public String getCertainBallOfOver(int overNum, int index){
        return mOvers[overNum].get(index);
    }

    public int getHowManyBallsBowled(int overNum){
        return mOvers[overNum].size();
    }

    public int getRunsOfThatOver(int overNum){
        return runsOfThatOver[overNum];
    }

    public int getRunsAfterOver(int overNum){
        if(!isOverComplete() && !isInningDone()){
            runsAfterOver[mCurrentOver] = getTotalRunScored();
        }else if(isInningDone() && overNum == mCurrentOver){
            runsAfterOver[overNum] = getTotalRunScored();
        }
        return runsAfterOver[overNum];
    }

    public int getTotalBallsBowled(){
        return mCurrentOver * 6 + mNumOfBallsPlayed;
    }

    public void undo(int overNum, boolean ballLegal, boolean wasLastBallWicket){
        if(ballLegal){
            if (mNumOfBallsPlayed != 0) {
                mNumOfBallsPlayed--;
            }
            if(mOvers[overNum].size() == 0){
                overNum--;
            }
        }
        if(wasLastBallWicket){
            if(mCurrentNumOfWickets != 0) {
                mCurrentNumOfWickets--;
            }
        }else{
            if(mRunType.getTotalRuns() != 0) {
                mRunType.removeLastRun();
                runsOfThatOver[mCurrentOver] = runsOfThatOver[mCurrentOver] - mRunType.getRun();
            }
        }
        if(!mOvers[overNum].isEmpty()) {
            mOvers[overNum].remove(mOvers[overNum].size() - 1);
        }
    }

    public boolean isInningDone(){
        checkInningDone();
        return inningDone;
    }

    private void checkInningDone(){
        if(mCurrentOver == mTotalOvers){
            inningDone = true;
        }else if(mCurrentNumOfWickets == mWicketNeeded){
            inningDone = true;
        }else inningDone = ( mRunsNeededToWin <= mRunType.getTotalRuns() ) && teamChasing;

    }
}
