package com.example.dovydas.punchescounter;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.PersistableBundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dovydas.punchescounter.model.Fight;

public class ScoringActivity extends AppCompatActivity implements EndRoundFragment.dialogDoneListener {
    final private static String RED_LANDED= "redLanded";
    final private static String BLUE_LANDED= "blueLanded";
    final private static String FIGHT= "fight";
    private Fight fight;
    EndRoundFragment dialog;
    int currentRound;

    int redLandedCount=0;
    int blueLandedCount=0;

    Button scoreForRed;
    Button scoreForBlue;

    TextView redLanded;
    TextView blueLanded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);

        if (savedInstanceState!=null){
            redLandedCount= savedInstanceState.getInt(RED_LANDED);
            blueLandedCount= savedInstanceState.getInt(BLUE_LANDED);
            fight= (Fight) savedInstanceState.getSerializable(FIGHT);
        }
        Bundle args = getIntent().getExtras();
        if (args != null && savedInstanceState==null) {
            fight = (Fight) args.getSerializable("fight");
        }
        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
        TextView redBoxer = (TextView) findViewById(R.id.redFighter);
        TextView blueBoxer = (TextView) findViewById(R.id.blueFighter);
        redLanded = (TextView) findViewById(R.id.redPunches);
        blueLanded = (TextView) findViewById(R.id.bluePunches);

        Button btnDecBlue= (Button) findViewById(R.id.btnMinusBlue);
        Button btnDecRed= (Button) findViewById(R.id.btnMinusRed);
        Button btnEndRound= (Button) findViewById(R.id.btnEndRound);
        scoreForRed= (Button) findViewById(R.id.scoreForRed);
        scoreForBlue= (Button) findViewById(R.id.scoreForBlue);

        redLanded.setText(""+redLandedCount);
        blueLanded.setText(""+blueLandedCount);
        redBoxer.setText(fight.getRedFighter());
        blueBoxer.setText(fight.getBlueFighter());
        dialog = new EndRoundFragment();
        dialog.setFight(fight);
        scoreForRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redLandedCount++;
                redLanded.setText(""+redLandedCount);
                vibe.vibrate(50);
            }
        });

        scoreForBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blueLandedCount++;
                blueLanded.setText(""+blueLandedCount);
                vibe.vibrate(50);
            }
        });

        btnDecBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blueLandedCount>0){
                    blueLandedCount--;
                    blueLanded.setText(""+blueLandedCount);
                }
            }
        });

        btnDecRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (redLandedCount>0){
                    redLandedCount--;
                    redLanded.setText(""+redLandedCount);
                }
            }
        });

        btnEndRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int punchesLandedRed=Integer.parseInt(redLanded.getText().toString());
                int punchesLandedBlue=Integer.parseInt(blueLanded.getText().toString());
                fight.setBluePunches(punchesLandedBlue);
                fight.setRedPunches(punchesLandedRed);
                dialog.show(getFragmentManager(), "endRound");
            }
        });
    }


    @Override
    public void onDialogDone(boolean status) {
        //renew round imageView using fight.currentRound
        redLandedCount=0;
        blueLandedCount=0;
        redLanded.setText("0");
        blueLanded.setText("0");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(RED_LANDED, redLandedCount);
        savedInstanceState.putInt(BLUE_LANDED, blueLandedCount);
        savedInstanceState.putSerializable(FIGHT, fight);
        super.onSaveInstanceState(savedInstanceState);
    }
}
