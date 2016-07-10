package com.example.dovydas.punchescounter;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.dovydas.punchescounter.storage.MemoryManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class RatingOutcomeFragment extends Fragment {


    public RatingOutcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        boolean isFeedbackpositive= getArguments().getBoolean(RatingFragment.FEEDBACK);
        View view;
        if (isFeedbackpositive){
            view=inflater.inflate(R.layout.fragment_rating_outcome_yes, container, false);
            Button btnYes= (Button) view.findViewById(R.id.answerYes);
            Button btnNo= (Button) view.findViewById(R.id.answerNo);
            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MemoryManager.getInstance(getContext()).setAllowedToAskForRating(false);
                    ((LinearLayout)v.getParent().getParent()).setVisibility(View.GONE); //hide fragment
                    Uri uri = Uri.parse("market://details?id=com.geeksoft.screenshot");
                    Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        startActivity(myAppLinkToMarket);
                    } catch (ActivityNotFoundException e) {
                        //
                    }
                }
            });
            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((LinearLayout)v.getParent().getParent()).setVisibility(View.GONE);
                }
            });
        }else{
           view=inflater.inflate(R.layout.fragment_rating_outcome_no, container, false);
            MemoryManager.getInstance(getContext()).setAllowedToAskForRating(false);
            EditText comment= (EditText) view.findViewById(R.id.comment);
            CheckBox cbDesign= (CheckBox) view.findViewById(R.id.cbDesign);
            CheckBox cbUsability= (CheckBox) view.findViewById(R.id.cbUsability);
            CheckBox cbAdds= (CheckBox) view.findViewById(R.id.cbAdds);
            Button btnSubmit= (Button) view.findViewById(R.id.btnSubmit);
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((LinearLayout)v.getParent()).setVisibility(View.GONE);
                    // Upload feedback to server
                }
            });
        }

        return view;
    }


}
