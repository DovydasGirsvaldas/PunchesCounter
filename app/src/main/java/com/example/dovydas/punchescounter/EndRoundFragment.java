package com.example.dovydas.punchescounter;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.dovydas.punchescounter.model.Fight;


/**
 * A simple
 * Activities that contain this fragment must implement the
 */
public class EndRoundFragment extends DialogFragment {
    final private static String FIGHT= "fight";
    final private static String RED_SCORE= "redScore";
    final private static String BLUE_SCORE= "blueScore";

    private View view;
    private dialogDoneListener mListener;
    private TextView redName;
    private TextView blueName;
    private TextView RoundLabel;
    private TextView redLanded;
    private TextView blueLanded;
    private TextView scoreRed;
    private TextView scoreBlue;
    private Fight fight;

    int scoreR;
    int scoreB;

    int landedB;
    int landedR;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_end_round, container, false);
        if (savedInstanceState!=null){
            fight=(Fight) savedInstanceState.getSerializable(FIGHT);
            scoreR= savedInstanceState.getInt(RED_SCORE);
            scoreB= savedInstanceState.getInt(BLUE_SCORE);
        }
        RoundLabel = (TextView) view.findViewById(R.id.roundLabel);
        redName = (TextView) view.findViewById(R.id.redBoxerName);
        blueName = (TextView) view.findViewById(R.id.blueBoxerName);
        redLanded = (TextView) view.findViewById(R.id.redBoxerPunches);
        blueLanded = (TextView) view.findViewById(R.id.blueBoxerPunches);
        scoreRed = (TextView) view.findViewById(R.id.redScore);
        scoreBlue = (TextView) view.findViewById(R.id.blueScore);

        Button btnFinished= (Button) view.findViewById(R.id.btnOutcome);
        Button btnUpRed = (Button) view.findViewById(R.id.btnPlusRed);
        Button btnUpBlue = (Button) view.findViewById(R.id.btnPlusBlue);
        Button btnDownRed = (Button) view.findViewById(R.id.btnMinusRed);
        Button btnDownBlue = (Button) view.findViewById(R.id.btnMinusBlue);
        Button btnScore = (Button) view.findViewById(R.id.btnDoneRound);

        btnUpRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scoreR<10){
                    scoreR++;
                    scoreRed.setText(""+scoreR);
                }
            }
        });

        btnUpBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scoreB<10){
                    scoreB++;
                    scoreBlue.setText(""+scoreB);
                }
            }
        });

        btnDownBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scoreB>0){
                    scoreB--;
                    scoreBlue.setText(""+scoreB);
                }
            }
        });

        btnDownRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scoreR>0){
                    scoreR--;
                    scoreRed.setText(""+scoreR);
                }
            }
        });

        if (fight!=null) {
            redName.setText(fight.getRedFighter());
            blueName.setText(fight.getBlueFighter());
            RoundLabel.setText("Round: " + fight.getCurrentRound());
            landedB=fight.getBluePunches()[fight.getCurrentRound()];
            landedR=fight.getRedPunches()[fight.getCurrentRound()];
            redLanded.setText("" + landedR);
            blueLanded.setText("" + landedB);
            if (savedInstanceState==null) {
                if (landedR > landedB) {
                    scoreR = 10;
                    scoreB = 9;
                } else {
                    if (landedR < landedB) {
                        scoreR = 9;
                        scoreB = 10;
                    } else {
                        scoreR = 10;
                        scoreB = 10;
                    }
                }
            }
            scoreRed.setText(""+scoreR);
            scoreBlue.setText(""+scoreB);
            if (fight.getCurrentRound()==fight.getRoundCount())
                btnScore.setText("Go to scorecard");
        }
        Dialog dialog = getDialog();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fight.setRedPoints(scoreR);
                fight.setBluePoints(scoreB);
                if (fight.getCurrentRound()==fight.getRoundCount()){
                    Intent intent = new Intent(getActivity(), ScorecardActivity.class);
                    intent.putExtra("fight", fight);
                    startActivity(intent);
                }else {
                    System.out.println("#### increase called");
                    fight.increaseRound();
                    mListener.onDialogDone(true);
                }
                dismiss();
            }
        });

        btnFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle= new Bundle();
                bundle.putSerializable("fight", fight);

                OutcomeFragment outcomeFragment = new OutcomeFragment();
                outcomeFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction
                        .addToBackStack("endRound")
                        .add(0, outcomeFragment, "ShowOutcomesFragment")
                        .commit();
                // need to hide current fragment
            }
        });

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (dialogDoneListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement dialogDoneListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface dialogDoneListener {
        void onDialogDone(boolean state);
    }

    public void setFight(Fight fight){
        this.fight= fight;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(FIGHT, fight);
        savedInstanceState.putInt(RED_SCORE, scoreR);
        savedInstanceState.putInt(BLUE_SCORE, scoreB);
        super.onSaveInstanceState(savedInstanceState);
    }

}
