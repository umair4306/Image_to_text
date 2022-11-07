package com.aniketjain.textscanner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aniketjain.textscanner.Adapter.HistoryAdapter;
import com.aniketjain.textscanner.Model.HistoryModel;

import java.util.ArrayList;

import io.paperdb.Paper;

public class History extends AppCompatActivity {

    ArrayList<String> list;
    RecyclerView tasbeehRv;
    ActionBar actionBar;
    TextView emptyHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        actionBar = getSupportActionBar();
        Paper.init(this);
        tasbeehRv = findViewById(R.id.historyRv);
        emptyHistory = findViewById(R.id.emptryhistory);
        actionBar.setTitle("History");

        list =  Paper.book().read("history", new ArrayList<>());
        Log.d("1234567",list.toString());



        if (list.isEmpty()){
            emptyHistory.setText("Your List is Empty");
            emptyHistory.setVisibility(View.VISIBLE);
        }
        else {

            emptyHistory.setVisibility(View.GONE);
            HistoryAdapter adapter = new HistoryAdapter(this,list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            tasbeehRv.setLayoutManager(layoutManager);
            layoutManager.setReverseLayout(true);
            layoutManager.setStackFromEnd(true);
            tasbeehRv.setNestedScrollingEnabled(false);
            tasbeehRv.setAdapter(adapter);
        }
//        list.add(new HistoryModel("Umair"));




    }
}