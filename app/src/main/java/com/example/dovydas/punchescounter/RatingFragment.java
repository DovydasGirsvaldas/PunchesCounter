package com.example.dovydas.punchescounter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RatingFragment extends Fragment {

    final public static String FEEDBACK= "feedback";
    private userFeedbackListener mListener;

    public RatingFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_rating, container, false);
        Button btnNo= (Button) view.findViewById(R.id.answerNo);
        Button btnYes= (Button) view.findViewById(R.id.answerYes);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFeedbackProvided(false);
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFeedbackProvided(true);
            }
        });
        return view;

    }

    public interface userFeedbackListener {
        void onFeedbackProvided(boolean isFeedbackPositive);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof userFeedbackListener) {
            mListener = (userFeedbackListener) context;
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
