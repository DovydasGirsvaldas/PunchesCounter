package com.example.dovydas.punchescounter.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dovydas.punchescounter.model.Fight;

import java.util.ArrayList;

/**
 * Created by Dovydas on 7/6/2016.
 */
public class SqliteDatabaseAdapter {
    private Context context;
    private DatabaseOpenHelper helper;
    private Cursor cursor;
    private SQLiteDatabase database;

    private static final String DATABASE_NAME = "boxingScoring.db";
    private static final int DATABASE_VERSION = 1;
    private static final String  TABLE_SCORECARD= "tableScorecard";

    private static final String FIGHT_ID = "fightId";
    private static final String RED_FIGHTER_NAME = "redFighterName";
    private static final String BLUE_FIGHTER_NAME = "blueFighterName";
    private static final String ROUND_COUNT = "roundCount";
    private static final String ROUND_CURRENT = "roundCurrent";
    private static final String RED_PUNCHES = "redPunches";
    private static final String BLUE_PUNCHES = "bluePunches";
    private static final String RED_POINTS = "redPoints";
    private static final String BLUE_POINTS = "bluePoints";
    private static final String OUTCOME = "outcome";

    public SqliteDatabaseAdapter(Context context)
    {
        this.context = context;
        helper = new DatabaseOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public boolean open()
    {
        if(context != null)
        {
            if(helper == null)
            {
                helper = new DatabaseOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
            }
            database = helper.getWritableDatabase();
            return true;
        }
        return false;
    }

    public void close()
    {
        if (cursor!=null)
        cursor.close();
        database.close();
    }

    public boolean addScorecard(final Fight fight)
    {
            ContentValues values = new ContentValues();
            values.put(RED_FIGHTER_NAME, fight.getRedFighter());
            values.put(BLUE_FIGHTER_NAME, fight.getBlueFighter());
            values.put(ROUND_COUNT, fight.getRoundCount());
            values.put(ROUND_CURRENT, fight.getCurrentRound());
            values.put(RED_PUNCHES, convertArrayToString(fight.getRedPunches()));
            values.put(BLUE_PUNCHES, convertArrayToString(fight.getBluePunches()));
            values.put(RED_POINTS, convertArrayToString(fight.getRedPoints()));
            values.put(BLUE_POINTS, convertArrayToString(fight.getBluePoints()));
            values.put(OUTCOME, fight.getOutcome().ordinal());

            return database.insert(TABLE_SCORECARD, null, values) != -1;
    }

    public boolean deleteScorecard(final int scorecardId){
        boolean success = false;
        if (doesScorecardExist(scorecardId)){
            success = (database.delete(TABLE_SCORECARD, FIGHT_ID + " = " + scorecardId, null)) > -1;
        }
        return success;
    }

    private boolean doesScorecardExist(int scorecardId)
    {
        String[] param = new String[1];
        param[0] = ""+scorecardId;
        cursor = database.query(TABLE_SCORECARD, null, FIGHT_ID + "=?", param, null, null, null);
        return 0 < cursor.getCount();
    }

    public ArrayList<Fight> getScorecards()
    {
        ArrayList<Fight> fightScorecards = new ArrayList<>();
        cursor = database.query(TABLE_SCORECARD, null, null, null, null, null, null);
        if (cursor.moveToFirst())
        {
            Fight fight;
            do
            {
                fight = new Fight();
                fight.setFightId(cursor.getInt(cursor.getColumnIndex(FIGHT_ID)));
                fight.setRedFighter(cursor.getString(cursor.getColumnIndex(RED_FIGHTER_NAME)));
                fight.setBlueFighter(cursor.getString(cursor.getColumnIndex(BLUE_FIGHTER_NAME)));
                fight.setRoundCount(cursor.getInt(cursor.getColumnIndex(ROUND_COUNT)));
                fight.setRoundCurrent(cursor.getInt(cursor.getColumnIndex(ROUND_CURRENT)));
                fight.setRedPunchesArray(convertStringToArray(cursor.getString(cursor.getColumnIndex(RED_PUNCHES))));
                fight.setBluePunchesArray(convertStringToArray(cursor.getString(cursor.getColumnIndex(BLUE_PUNCHES))));
                fight.setRedPointsArray(convertStringToArray(cursor.getString(cursor.getColumnIndex(RED_POINTS))));
                fight.setBluePointsArray(convertStringToArray(cursor.getString(cursor.getColumnIndex(BLUE_POINTS))));
                fight.setOutcome(Fight.Outcome.values()[cursor.getInt(cursor.getColumnIndex(ROUND_CURRENT))-1]);
                fightScorecards.add(fight);

            } while (cursor.moveToNext());
        }

        return fightScorecards;
    }
    /**
     * Private class that handles the setup of the database
     */
    private static class DatabaseOpenHelper extends SQLiteOpenHelper
    {
        private static final String CREATE_TABLE_SCORECARD =
                "create table " +
                        TABLE_SCORECARD + " (" +
                        FIGHT_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT, " +
                RED_FIGHTER_NAME + " text not null, " +
                        BLUE_FIGHTER_NAME + " text not null, " +
                        ROUND_COUNT + " int not null, " +
                        ROUND_CURRENT + " int not null, " +
                        RED_PUNCHES + " text not null, " +
                        BLUE_PUNCHES + " text not null, " +
                        RED_POINTS + " text not null, " +
                        OUTCOME + " text not null, " +
                        BLUE_POINTS + " text not null "+ ");";


        public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_SCORECARD);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            //Drop the old table.
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORECARD);

            //Build new table.
            onCreate(db);
        }
    }

    // methods for storing arrays in database
    public static String strSeparator = "_,_";
    public static String convertArrayToString(int[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }
    public static int[] convertStringToArray(String str){
        String[] strings = str.split(strSeparator);
        final int[] ints = new int[strings.length];
        for (int i=0; i < strings.length; i++) {
            ints[i] = Integer.parseInt(strings[i]);
        }
        return ints;
    }
}
