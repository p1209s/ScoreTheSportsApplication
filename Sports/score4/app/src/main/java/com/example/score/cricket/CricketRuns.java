package com.example.score.cricket;

public class CricketRuns {

    private int runValue;
    private int runTotal;
    private CricketRunsAvailable runAvailable;

    public CricketRuns(){
        this.runValue = 0;
        this.runTotal = 0;
    }

    public void setNumberOfRuns(CricketRunsAvailable runsAvailable){
        this.runAvailable = runsAvailable;
        runValue = runsAvailable.value;
        setRunTotal(runValue);
    }

    public void setExtraRuns (int extraRuns){
        runValue = extraRuns;
        setRunTotal(runValue);
    }

    private void setRunTotal(int runValue){
        runTotal = runTotal + runValue ;
    }

    public int getRun(){
        return runValue;
    }

    public int getTotalRuns(){
        return runTotal;
    }

    public void removeLastRun(){
        runTotal = runTotal - runValue;
    }
}

