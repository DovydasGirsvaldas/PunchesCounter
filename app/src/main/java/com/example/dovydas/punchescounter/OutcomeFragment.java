package com.example.dovydas.punchescounter;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dovydas.punchescounter.model.Fight;

public class OutcomeFragment extends DialogFragment {

    private EndRoundFragment.dialogDoneListener mListener;
    private Fight fight;
    private View view;
    RadioGroup radioGroup;
    RadioButton redKO;
    RadioButton redDQ;
    RadioButton blueKO;
    RadioButton blueDQ;
    RadioButton nc;
    Button btnFinishFight;
    Button btnBack;

    Fight.Outcome selectedOutcome;


    public OutcomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_outcome, container, false);
        Bundle bundle = getArguments();
        fight= (Fight)bundle.getSerializable("fight");
        radioGroup= (RadioGroup) view.findViewById(R.id.radioGroup);
        redKO= (RadioButton) view.findViewById(R.id.redKO);
        redDQ= (RadioButton) view.findViewById(R.id.redDQ);
        blueKO= (RadioButton) view.findViewById(R.id.blueKO);
        blueDQ= (RadioButton) view.findViewById(R.id.blueDQ);
        nc= (RadioButton) view.findViewById(R.id.nc);
        btnFinishFight= (Button) view.findViewById(R.id.btnFinishFight);
        btnBack= (Button) view.findViewById(R.id.btnBack);

        redKO.setText(fight.getRedFighter()+" wins by KO/TKO");
        blueKO.setText(fight.getBlueFighter()+" wins by KO/TKO");
        redDQ.setText(fight.getRedFighter()+" wins by DQ");
        blueDQ.setText(fight.getBlueFighter()+" wins by DQ");
        nc.setText("No contest");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        btnFinishFight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId!=0) {
                    if (selectedId == redKO.getId()) {
                        selectedOutcome = Fight.Outcome.RED_KO;
                    } else if (selectedId == blueKO.getId()) {
                        selectedOutcome = Fight.Outcome.BLUE_KO;
                    } else if (selectedId == nc.getId()) {
                        selectedOutcome = Fight.Outcome.NC;
                    } else if (selectedId == redDQ.getId()) {
                        selectedOutcome = Fight.Outcome.RED_DQ;
                    } else if (selectedId == blueDQ.getId()) {
                        selectedOutcome = Fight.Outcome.BLUE_DQ;
                    }
                }
                fight.setOutcome(selectedOutcome);
                Intent intent = new Intent(getActivity(), ScorecardActivity.class);
                intent.putExtra("fight", fight);
                startActivity(intent);
            }
        });

        Dialog dialog = getDialog();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EndRoundFragment.dialogDoneListener) {
            mListener = (EndRoundFragment.dialogDoneListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



}
