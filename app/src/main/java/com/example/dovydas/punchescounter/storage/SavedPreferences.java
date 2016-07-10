package com.example.dovydas.punchescounter.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.sql.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Dovydas on 7/6/2016.
 */
public class SavedPreferences {
    public static final String TIMES_STARTED = "timesStarted";
    public static final String INSTALL_DATE = "installDate";
    public static final String ASK_RATING = "askRating";

    private SharedPreferences sharedPref;

    public SavedPreferences(Context context)
    {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public void setTimesStarted(int times)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(TIMES_STARTED, times);
        editor.apply();
    }

    public int getTimesStarted(){
        return sharedPref.getInt(TIMES_STARTED,0);
    }

    public void setInstallDate(long date){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(INSTALL_DATE, date);
        editor.apply();
    }

    public long getInstallDate(){
        return sharedPref.getLong(INSTALL_DATE, 0);
    }

    public boolean getAskForRating() {
        return sharedPref.getBoolean(ASK_RATING,false);
    }

    public void setAskForRating(Boolean b){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(ASK_RATING, b);
        editor.apply();
    }
}
