package com.example.dovydas.punchescounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dovydas.punchescounter.storage.MemoryManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        timesApplicationBeenUsed();
    }

    private void timesApplicationBeenUsed() {
        int times= MemoryManager.getInstance(this).getTimesStarted();
        switch (times){
            case 0:
                MemoryManager.getInstance(this).setInstallDate(System.currentTimeMillis());
                MemoryManager.getInstance(this).setTimesStarted(1);
                MemoryManager.getInstance(this).setAllowedToAskForRating(true);
                // run Welcome, tutorial.
        }
    }

    private void initViews() {
        Button btnNewFight= (Button) findViewById(R.id.btnNewFight);
        Button btnHistory= (Button) findViewById(R.id.btnHistory);
        Button btnHowToScore= (Button) findViewById(R.id.btnHowTo);

        btnNewFight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getBaseContext(), NewFightOptionsActivity.class);
                startActivity(intent);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getBaseContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

}
