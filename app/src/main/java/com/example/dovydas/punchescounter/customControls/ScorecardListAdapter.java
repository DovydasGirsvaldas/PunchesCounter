package com.example.dovydas.punchescounter.customControls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dovydas.punchescounter.R;
import com.example.dovydas.punchescounter.model.Fight;

import java.util.ArrayList;

/**
 * Created by Dovydas on 7/8/2016.
 */
public class ScorecardListAdapter extends ArrayAdapter<Fight> {
    private final ArrayList<Fight> itemsArrayList;
    private LayoutInflater inflater;

    public ScorecardListAdapter(Context context, ArrayList<Fight> itemsArrayList) {
        super(context, R.layout.scorecard_list_row);
        this.itemsArrayList = itemsArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("##### getView:"+convertView);
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.scorecard_list_row, null);
            viewHolder = new ViewHolder();
            viewHolder.redFighter = (TextView) convertView.findViewById(R.id.redFighterListItem);
            viewHolder.blueFighter = (TextView) convertView.findViewById(R.id.blueFighterListItem);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.redFighter.setText(itemsArrayList.get(position).getRedFighter());
        viewHolder.blueFighter.setText(itemsArrayList.get(position).getBlueFighter());
        System.out.println("##### return view:"+convertView);
        return convertView;
    }

    @Override
    public int getCount() {
        return itemsArrayList.size();
    }


    static class ViewHolder {
        TextView redFighter, blueFighter;
    }
}
