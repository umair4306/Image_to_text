package com.aniketjain.textscanner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aniketjain.textscanner.Adapter.HistoryAdapter;
import com.aniketjain.textscanner.Model.HistoryModel;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.sdk.AppLovinSdkUtils;

import java.util.ArrayList;

import io.paperdb.Paper;

public class History extends AppCompatActivity {

    ArrayList<String> list;
    RecyclerView tasbeehRv;
    ActionBar actionBar;
    TextView emptyHistory;
    private MaxAdView MRECAdview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        actionBar = getSupportActionBar();
        Paper.init(this);
        tasbeehRv = findViewById(R.id.historyRv);
        emptyHistory = findViewById(R.id.emptryhistory);
        actionBar.setTitle("History");

        list = Paper.book().read("history", new ArrayList<>());
        Log.d("1234567", list.toString());

        createMrecAd();


        if (list.isEmpty()) {
            emptyHistory.setText("Your List is Empty");
            emptyHistory.setVisibility(View.VISIBLE);
        } else {
            emptyHistory.setVisibility(View.GONE);
            HistoryAdapter adapter = new HistoryAdapter(this, list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            tasbeehRv.setLayoutManager(layoutManager);
            layoutManager.setReverseLayout(true);
            layoutManager.setStackFromEnd(true);
            tasbeehRv.setNestedScrollingEnabled(false);
            tasbeehRv.setAdapter(adapter);
        }
//        list.add(new HistoryModel("Umair"));

    }

    private void createMrecAd() {
        MRECAdview = new MaxAdView(Constant.MREC_ADD_KEY, MaxAdFormat.MREC, this);
        MRECAdview.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                Log.d("onAdLoaded", "onAdLoaded: ");
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {
                Log.d("onAdLoaded", "onAdDisplayed: ");
            }

            @Override
            public void onAdHidden(MaxAd ad) {
                Log.d("onAdLoaded", "onAdHidden: ");
            }

            @Override
            public void onAdClicked(MaxAd ad) {
                Log.d("onAdLoaded", "onAdClicked: ");
            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.d("onAdLoaded", "onAdLoadFailed: ");
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                Log.d("onAdLoaded", "onAdDisplayFailed: ");
            }
        });

        int width = AppLovinSdkUtils.dpToPx(this, 300);
        int height = AppLovinSdkUtils.dpToPx(this, 250);
        MRECAdview.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));

        MRECAdview.setBackgroundColor(Color.WHITE);

        FrameLayout layout = findViewById(R.id.mrec);
        layout.addView(MRECAdview);
        MRECAdview.loadAd();
        MRECAdview.startAutoRefresh();
    }


}