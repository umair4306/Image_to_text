package com.aniketjain.textscanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAppOpenAd;
import com.applovin.sdk.AppLovinSdk;
import com.github.ybq.android.spinkit.SpinKitView;

public class SplashScreen extends AppCompatActivity {

    private ExampleAppOpenManager appOpenManager;
    private SpinKitView progressBar;
    private TextView adLoadingTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        progressBar = findViewById(R.id.spin_kit);
        adLoadingTv = findViewById(R.id.ad_loading_tv);

        AppLovinSdk.initializeSdk(this, configuration ->
                appOpenManager = new ExampleAppOpenManager(SplashScreen.this));

    }

    public class ExampleAppOpenManager implements MaxAdListener {
        private final MaxAppOpenAd appOpenAd;
        private final Context context;

        public ExampleAppOpenManager(final Context context) {
            this.context = context;
            appOpenAd = new MaxAppOpenAd(Constant.APP_OPEN_KEY, context);
            appOpenAd.setListener(this);
            appOpenAd.loadAd();
        }

        private void showAdIfReady() {
            if (appOpenAd == null || !AppLovinSdk.getInstance(context).isInitialized()) return;
            if (appOpenAd.isReady()) {
                progressBar.setVisibility(View.GONE);
                adLoadingTv.setVisibility(View.GONE);
                appOpenAd.showAd();
            }
        }


        @Override
        public void onAdLoaded(final MaxAd ad) {
            Log.d("testAds", "ad loaded");
            showAdIfReady();
        }

        @Override
        public void onAdLoadFailed(final String adUnitId, final MaxError error) {
            Log.d("testAds", "ad load fail");
        }

        @Override
        public void onAdDisplayed(final MaxAd ad) {
            Log.d("testAds", "ad display");
        }

        @Override
        public void onAdClicked(final MaxAd ad) {
            Log.d("testAds", "ad clicked");
        }

        @Override
        public void onAdHidden(final MaxAd ad) {
//            appOpenAd.loadAd();
            Log.d("testAds", "ad hiddedn");
            startActivity(new Intent(SplashScreen.this, OnboardingScreen.class));
            finish();
        }

        @Override
        public void onAdDisplayFailed(final MaxAd ad, final MaxError error) {
            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            appOpenAd.loadAd();
        }
    }

}
