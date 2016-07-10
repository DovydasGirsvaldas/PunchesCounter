package com.example.dovydas.punchescounter.model;

import java.io.Serializable;

/**
 * Created by Dovydas on 7/2/2016.
 */
public class Fight implements Serializable{
    public enum Outcome{
        RED_KO, RED_DQ, BLUE_KO, BLUE_DQ, NC
    }

    private String redFighter;
    private String blueFighter;
    private int roundCount;
    private int roundCurrent;
    private int[] redPunches;
    private int[] bluePunches;
    private int[] redPoints;
    private int[] bluePoints;
    private int fightId;
    private Outcome outcome;

    public Fight(String redName, String blueName, int count){
        this.redFighter= redName;
        this.blueFighter= blueName;
        this.roundCount= count;
        redPunches = new int[count+1];
        bluePunches = new int[count+1];
        redPoints = new int[count+1];
        bluePoints = new int[count+1];
        roundCurrent = 1;
    }
    
    public Fight(){

    }

    public void increaseRound(){
        roundCurrent++;
        System.out.println("##### current"+roundCurrent);
    }

    public int getCurrentRound(){
        return roundCurrent;
    }

    public String getRedFighter() {
        return redFighter;
    }

    public void setRedFighter(String redFighter) {
        this.redFighter = redFighter;
    }

    public String getBlueFighter() {
        return blueFighter;
    }

    public void setBlueFighter(String blueFighter) {
        this.blueFighter = blueFighter;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public int[] getRedPunches() {
        return redPunches;
    }

    public void setRedPunches(int punches) {
        redPunches[roundCurrent]=punches;
    }

    public int[] getBluePunches() {
        return bluePunches;
    }

    public void setBluePunches(int punches) {
        bluePunches[roundCurrent]=punches;
    }

    public int[] getRedPoints() {
        return redPoints;
    }


    public int[] getBluePoints() {
        return bluePoints;
    }

    public void setBluePoints(int points) {
        bluePoints[roundCurrent]= points;
    }

    public void setRedPoints(int points) {
        redPoints[roundCurrent]= points;
    }

    public void setRoundCurrent(int roundCurrent) {
        this.roundCurrent = roundCurrent;
    }

    public void setRedPointsArray(int[] redPointsArray) {
        this.redPoints = redPointsArray;
    }

    public void setBluePointsArray(int[] bluePointsArray) {
        this.bluePoints = bluePointsArray;
    }

    public void setBluePunchesArray(int[] bluePunchesArray) {
        this.bluePunches = bluePunchesArray;
    }

    public void setRedPunchesArray(int[] redPunchesArray) {
        this.redPunches = redPunchesArray;
    }

    public int getFightId() {
        return fightId;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public void setFightId(int fightId) {
        this.fightId = fightId;
    }
}
