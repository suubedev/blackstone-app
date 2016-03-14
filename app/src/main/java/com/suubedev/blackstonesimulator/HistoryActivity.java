package com.suubedev.blackstonesimulator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    ImageView backButton;
    ImageView clearButton;
    TextView avaerageStones;
    DBAdapter adapter;
    ListView history;
    ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_list);

        adapter = new DBAdapter(this);
        backButton = (ImageView) findViewById(R.id.backButton);
        clearButton = (ImageView) findViewById(R.id.clearButton);
        history = (ListView) findViewById(R.id.listView);
        avaerageStones = (TextView) findViewById(R.id.averageStones);
        listAdapter = new ArrayAdapter<>(this, R.layout.stone_history, setCounts());
        history.setAdapter(listAdapter);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeAll();
                finish();
                startActivity(getIntent());
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (v.getContext(), MainActivity.class);
                startActivity(i);
            }
        });
        avaerageStones.setText("Your average stone count is: " + averageStoneCount() + " Blackstones");
    }

    public int averageStoneCount() {
        List<Integer> intCounts = adapter.getAllCounts();
        int count = 0;
        int average = 0;
        for (int i = 0; i < intCounts.size(); i++) {
            count = count + intCounts.get(i);
        }
        if (intCounts.size() == 0) {
            average = 0;
        } else {
            average = count / intCounts.size();
        }
        return average;
    }

    public List<String> setCounts() {
        List<Integer> intCounts = adapter.getAllCounts();
        List<String> getCounts = new ArrayList<>();
        for (int i = 0; i < intCounts.size(); i++) {
            getCounts.add((i + 1) + ". "+ intCounts.get(i) + " Blackstones used to get to +15.");
        }
        return getCounts;
    }

}