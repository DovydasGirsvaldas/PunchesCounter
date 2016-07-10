package com.example.dovydas.punchescounter.storage;

import android.content.Context;

import com.example.dovydas.punchescounter.model.Fight;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Dovydas on 7/6/2016.
 */
public class MemoryManager {
    private static MemoryManager instance;
    private static SavedPreferences savedPreferences;
    private static SqliteDatabaseAdapter database;


    private MemoryManager(Context context) {
        savedPreferences = new SavedPreferences(context);
        database = new SqliteDatabaseAdapter(context);
    }

    public static MemoryManager getInstance(Context context) {
        if (instance == null) {
            instance = new MemoryManager(context);
        }
        return instance;
    }

    public int getTimesStarted() {
        return savedPreferences.getTimesStarted();
    }

    public void setTimesStarted(int times) {
        savedPreferences.setTimesStarted(times);
    }

    public long getInstallDate(){
        return savedPreferences.getInstallDate();
    }

    public void setInstallDate(long date){
        savedPreferences.setInstallDate(date);
    }

    public boolean isAllowedToAskForRating(){return savedPreferences.getAskForRating();}
    public void setAllowedToAskForRating(boolean choice){savedPreferences.setAskForRating(choice);}

    public ArrayList<Fight> getScorecards() {
        database.open();
        ArrayList<Fight> scorecards = database.getScorecards();
        database.close();
        return scorecards;
    }

    public boolean addScorecard(Fight fight) {
        database.open();
        boolean result = database.addScorecard(fight);
        database.close();
        return result;
    }

    public void updateScorecard(Fight fight) {
        database.open();
        database.deleteScorecard(fight.getFightId());
        database.addScorecard(fight);
        database.close();
    }

    public boolean deleteScorecard(int fightId) {
        database.open();
        boolean result = database.deleteScorecard(fightId);
        database.close();
        return result;
    }

}
