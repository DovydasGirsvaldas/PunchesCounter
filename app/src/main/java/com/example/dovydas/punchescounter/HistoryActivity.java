package com.example.dovydas.punchescounter;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dovydas.punchescounter.customControls.ScorecardListAdapter;
import com.example.dovydas.punchescounter.model.Fight;
import com.example.dovydas.punchescounter.storage.MemoryManager;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private ScorecardListAdapter scorecardListAdapter;
    private ListView scorecardListView;
    private ArrayList<Fight> fightList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        scorecardListView= (ListView) findViewById(R.id.scorecardListView);
        fightList= MemoryManager.getInstance(this).getScorecards();
        scorecardListAdapter= new ScorecardListAdapter(getBaseContext(), fightList);
        scorecardListView.setAdapter(scorecardListAdapter);
        System.out.println("##### list size:"+fightList.size());

        scorecardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                Intent intent= new Intent(getBaseContext(), ScorecardActivity.class);
                intent.putExtra("fight", fightList.get(position));
                startActivity(intent);
            }
        });

        scorecardListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return true;
            }
        });
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_context, menu);
//    }
//
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        switch(item.getItemId()) {
//            case R.id.delete:
//                Service.getProductLists().remove(selectedPosition);
//                Toast.makeText(this, "List removed",
//                        Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return super.onContextItemSelected(item);
//        }
//    }
//
//    @Override
//    public void onContextMenuClosed(Menu menu){
//        ( (ProductListAdapter)listView.getAdapter()).notifyDataSetChanged();
//    }
//
//
//
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if(id == android.R.id.home){
//            NavUtils.navigateUpFromSameTask(this);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
