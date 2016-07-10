package com.example.dovydas.punchescounter;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dovydas.punchescounter.model.Fight;
import com.example.dovydas.punchescounter.storage.MemoryManager;

public class ScorecardActivity extends AppCompatActivity implements RatingFragment.userFeedbackListener {
    int totalRedPoints=0;
    int totalBluePoints=0;
    int totalRedLanded=0;
    int totalBlueLanded=0;
    private Fight fight;
    Button btnQuit;
    Button btnSaveQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);
        Intent intent = getIntent();
        fight= (Fight)intent.getSerializableExtra("fight");
        btnQuit= (Button) findViewById(R.id.btnQuit);
        btnSaveQuit= (Button) findViewById(R.id.btnSaveQuit);

        TableLayout scorecardLayout = (TableLayout) findViewById(R.id.table_scorecard);
        for (int i=1; i<=fight.getRoundCount(); i++ ){
            scorecardLayout.addView(newLine(i));
            totalRedPoints=totalRedPoints+fight.getRedPoints()[i];
            totalBluePoints=totalBluePoints+fight.getBluePoints()[i];
            totalRedLanded=totalRedLanded+fight.getRedPunches()[i];
            totalBlueLanded=totalBlueLanded+fight.getBluePunches()[i];
        }
        scorecardLayout.addView(bottomLine());
        scorecardLayout.addView(outcomeLine());
        TextView redName= (TextView) findViewById(R.id.fighterR);
        redName.setText(fight.getRedFighter());
        TextView blueName= (TextView) findViewById(R.id.fighterB);
        blueName.setText(fight.getBlueFighter());
        TextView rnd= (TextView) findViewById(R.id.roundNr);
        rnd.setText(fight.getRoundCount()+" ROUND");

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // back to menu
            }
        });
        btnSaveQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemoryManager.getInstance(getBaseContext()).addScorecard(fight);
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        shouldAskForRating();
    }

    private void shouldAskForRating() {
        int times = MemoryManager.getInstance(this).getTimesStarted();
        boolean isAllowedToAsk = MemoryManager.getInstance(this).isAllowedToAskForRating();
        long installDate = MemoryManager.getInstance(this).getInstallDate();
        // also checks if three days passed since installation.
        if(isAllowedToAsk){
        //if (isAllowedToAsk && ((System.currentTimeMillis()-installDate)>259200000) && (times-1)%5==0){
            // ask user for feedback or rating, hide commercial
            RatingFragment ratingFragment= new RatingFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ratingFragment).commit();
        }
    }

    private View outcomeLine() {
        TableRow tr =   new TableRow(this);
        tr.setOrientation(TableRow.HORIZONTAL);

        TableRow.LayoutParams trParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        trParams.gravity = Gravity.CENTER;
        tr.setLayoutParams(trParams);
        Drawable background = getResources().getDrawable(R.drawable.border_top);
        tr.setBackgroundDrawable(background);

        String result="";
        if (fight.getOutcome()!=null) {
            switch (fight.getOutcome()) {
                case RED_KO:
                    result = fight.getRedFighter() + " wins by KO/TKO";
                    break;
                case BLUE_KO:
                    result = fight.getBlueFighter() + " wins by KO/TKO";
                    break;
                case RED_DQ:
                    result = fight.getRedFighter() + " wins, " + fight.getBlueFighter() + " was disqualified";
                    break;
                case BLUE_DQ:
                    result = fight.getBlueFighter() + " wins, " + fight.getRedFighter() + " was disqualified";
                    break;
                case NC:
                    result = "No contest";
                    break;
            }
        }else{
            if (fight.getCurrentRound()==fight.getRoundCount()){
                if (totalRedPoints>totalBluePoints){
                    result = fight.getRedFighter()+ " wins by decision";
                }else if(totalRedPoints<totalBluePoints){
                    result = fight.getBlueFighter()+ " wins by decision";
                }else{
                    result= "Draw";
                }
            }
        }
        String text = "outcome: "+result;
        final SpannableStringBuilder str = new SpannableStringBuilder("outcome: "+result);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 9, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView outcome= new TextView(this);
        outcome.setText(str);
        outcome.setTextColor(Color.BLACK);
        TableRow.LayoutParams params=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1);
        params.span=5;
        outcome.setLayoutParams(params);
        outcome.setTextSize(15);

        tr.addView(outcome);
        return tr;
    }

    private TableRow bottomLine() {
        TableRow tr =   new TableRow(this);
        tr.setOrientation(TableRow.HORIZONTAL);

        TableRow.LayoutParams trParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        trParams.gravity = Gravity.CENTER;
        tr.setLayoutParams(trParams);
        Drawable background = getResources().getDrawable(R.drawable.border_top);
        tr.setBackgroundDrawable(background);

        TextView punchesRed= new TextView(this);
        punchesRed.setText(""+totalRedLanded);
        punchesRed.setTextColor(Color.BLACK);
        punchesRed.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));
        punchesRed.setTextSize(15);
        punchesRed.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView scoreRed= new TextView(this);
        scoreRed.setText(""+totalRedPoints);
        scoreRed.setTextColor(Color.BLACK);
        scoreRed.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));
        scoreRed.setTextSize(15);
        scoreRed.setTypeface(null, Typeface.BOLD);
        scoreRed.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView punchesBlue= new TextView(this);
        punchesBlue.setText(""+totalBlueLanded);
        punchesBlue.setTextColor(Color.BLACK);
        punchesBlue.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));
        punchesBlue.setTextSize(15);
        punchesBlue.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView scoreBlue= new TextView(this);
        scoreBlue.setText(""+totalBluePoints);
        scoreBlue.setTextColor(Color.BLACK);
        scoreBlue.setTypeface(null, Typeface.BOLD);
        scoreBlue.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));
        scoreBlue.setTextSize(15);
        scoreBlue.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView roundNr= new TextView(this);
        roundNr.setText("x");
        roundNr.setTextColor(Color.BLACK);
        roundNr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));
        roundNr.setTextSize(15);
        roundNr.setGravity(Gravity.CENTER_HORIZONTAL);

        tr.addView(punchesRed);
        tr.addView(scoreRed);
        tr.addView(roundNr);
        tr.addView(scoreBlue);
        tr.addView(punchesBlue);
        return tr;
    }

    private TableRow newLine(int round){
        TableRow tr =   new TableRow(this);
        tr.setOrientation(TableRow.HORIZONTAL);

        TableRow.LayoutParams trParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        trParams.gravity = Gravity.CENTER;
        tr.setLayoutParams(trParams);
        Drawable backGround = getResources().getDrawable(R.drawable.border_top);
        tr.setBackgroundDrawable(backGround);
        //tr.setHorizontalGravity(Gravity.CENTER);

        TextView punchesRed= new TextView(this);
        punchesRed.setText(""+fight.getRedPunches()[round]);
        punchesRed.setTextColor(Color.BLACK);
        punchesRed.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));
        punchesRed.setTextSize(15);
        punchesRed.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView scoreRed= new TextView(this);
        scoreRed.setText(""+fight.getRedPoints()[round]);
        scoreRed.setTextColor(Color.BLACK);
        scoreRed.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));
        scoreRed.setTextSize(15);
        scoreRed.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView punchesBlue= new TextView(this);
        punchesBlue.setText(""+fight.getBluePunches()[round]);
        punchesBlue.setTextColor(Color.BLACK);
        punchesBlue.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));
        punchesBlue.setTextSize(15);
        punchesBlue.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView scoreBlue= new TextView(this);
        scoreBlue.setText(""+fight.getBluePoints()[round]);
        scoreBlue.setTextColor(Color.BLACK);
        scoreBlue.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));
        scoreBlue.setTextSize(15);
        scoreBlue.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView roundNr= new TextView(this);
        roundNr.setText(""+round);
        roundNr.setTextColor(Color.BLACK);
        roundNr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));
        roundNr.setTextSize(15);
        roundNr.setGravity(Gravity.CENTER_HORIZONTAL);

        tr.addView(punchesRed);
        tr.addView(scoreRed);
        tr.addView(roundNr);
        tr.addView(scoreBlue);
        tr.addView(punchesBlue);
        return tr;
    }

    @Override
    public void onFeedbackProvided(boolean isFeedbackPositive) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(RatingFragment.FEEDBACK, isFeedbackPositive);
        RatingOutcomeFragment ratingOutcomeFragment = new RatingOutcomeFragment();
        ratingOutcomeFragment.setArguments(bundle);
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, ratingOutcomeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_scorecard, menu);
        MenuItem saveEvent = menu.findItem(R.id.save);
        MenuItem removeEvent = menu.findItem(R.id.remove);
        MenuItem editEvent = menu.findItem(R.id.edit);
        MenuItem shareEvent = menu.findItem(R.id.share);
        saveEvent.setVisible(fight.getFightId()==0);
        removeEvent.setVisible(fight.getFightId()!=0);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.remove:
                supportInvalidateOptionsMenu();
                Toast.makeText(getApplicationContext(), "Scorecard removed from history", Toast.LENGTH_LONG).show();
                break;
            case R.id.save:
                supportInvalidateOptionsMenu();
                Toast.makeText(getApplicationContext(), "Scorecard saved to history", Toast.LENGTH_LONG).show();
                break;
            case R.id.edit:
                break;
            case R.id.share:
                break;
        }
        return true;
    }

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        savedInstanceState.putInt(RED_LANDED, redLandedCount);
//        savedInstanceState.putInt(BLUE_LANDED, blueLandedCount);
//        super.onSaveInstanceState(savedInstanceState);
//    }
}
