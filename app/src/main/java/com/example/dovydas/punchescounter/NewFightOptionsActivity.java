package com.example.dovydas.punchescounter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dovydas.punchescounter.model.Fight;

public class NewFightOptionsActivity extends AppCompatActivity {
    final private static String ROUND_COUNT= "roundCount";

    int rounds=12;
    TextView roundCount;

    LinearLayout parentLayout;
    EditText redFighterName;
    EditText blueFighterName;

    Button btnMinusRound;
    Button btnPlusRound;
    Button btnStartScoring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            rounds= savedInstanceState.getInt(ROUND_COUNT);
        }
        setContentView(R.layout.activity_new_fight_options);
        initViews();
        initClickListeners();
        setupUI(parentLayout);
        roundCount.setText(""+rounds);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initClickListeners() {
        btnMinusRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rounds>1){
                    rounds--;
                    roundCount.setText(""+rounds);
                }
            }
        });

        btnPlusRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rounds<15){
                    rounds++;
                    roundCount.setText(""+rounds);
                }
            }
        });

        btnStartScoring.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fight fight= createNewFight();
            if (fight!=null) {
                Intent intent = new Intent(getBaseContext(), ScoringActivity.class);
                intent.putExtra("fight", fight);
                startActivity(intent);
            }
        }
    });

        redFighterName.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int start, int before,
                                      int count) {
                if (arg0.length() == 0) {
                    // No entered text so will show hint
                    redFighterName.setTypeface(null, Typeface.NORMAL);
                } else {
                    redFighterName.setTypeface(null, Typeface.BOLD);
                }
            }
        });

        blueFighterName.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int start, int before,
                                      int count) {
                if (arg0.length() == 0) {
                    blueFighterName.setTypeface(null, Typeface.NORMAL);
                } else {
                    blueFighterName.setTypeface(null, Typeface.BOLD);
                }
            }
        });
    }

    private Fight createNewFight() {
        String redName= redFighterName.getText().toString();
        String blueName= blueFighterName.getText().toString();
        if (redName.length()>0 && blueName.length()>0){
            return new Fight(redName, blueName, rounds);
        }else{
            return null;
        }
    }

    private void initViews() {
        roundCount= (TextView) findViewById(R.id.roundCount);
        redFighterName= (EditText) findViewById(R.id.redFighterName);
        blueFighterName= (EditText) findViewById(R.id.blueFighterName);
        btnMinusRound= (Button) findViewById(R.id.minusRound);
        btnPlusRound= (Button) findViewById(R.id.plusRound);
        btnStartScoring= (Button) findViewById(R.id.btnStart);
        parentLayout= (LinearLayout) findViewById(R.id.parent);
    }

    public void setupUI(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }
            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(ROUND_COUNT, rounds);
        super.onSaveInstanceState(savedInstanceState);
    }
}
