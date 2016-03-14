package com.suubedev.blackstonesimulator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.DecimalFormat;

public class CalculatorActivity extends AppCompatActivity {

    EditText currentRank;
    EditText currentFail;
    ImageView calcButton;
    ImageView backButton;
    TextView currentChance;
    double currentChanceValue;

    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

        currentRank = (EditText) findViewById(R.id.enterCurrentRank);
        currentFail = (EditText) findViewById(R.id.enterCurrentFail);
        calcButton = (ImageView) findViewById(R.id.calcButton);
        backButton = (ImageView) findViewById(R.id.backButtonCalc);
        currentChance = (TextView) findViewById(R.id.currentChance);
        currentChanceValue = 0;

        final DecimalFormat precision = new DecimalFormat("0.00");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8239207983137121/8560338893");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                gotoMain();
            }
        });

        requestNewInterstitial();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });

        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentRankEntered = currentRank.getText().toString();
                String currentFailEntered = currentFail.getText().toString();

                if (currentRankEntered.isEmpty()) {
                    currentRank.setText("0");
                    currentRankEntered = "0";
                }

                if (currentFailEntered.isEmpty()) {
                    currentFail.setText("0");
                    currentFailEntered = "0";
                }
                currentChance.setText("Rank " + currentRankEntered + " with " + currentFailEntered + " fails, " + precision.format(getChance()) + "% chance to succeed.");
            }
        });
    }

    public void gotoMain() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    public double getChance() {
        final double failStackEight = 2.5;
        final double failStackNine = 2.0;
        final double failStackTen = 1.5;
        final double failStackEleven = 1.25;
        final double failStackTwelve = 0.75;
        final double failStackThirteen = 0.63;
        final double failStackFourteen = 0.5;
        final double failStackFifteen = 0.5;

        final double failStackEightMax = 13;
        final double failStackNineMax = 14;
        final double failStackTenMax = 15;
        final double failStackElevenMax = 16;
        final double failStackTwelveMax = 18;
        final double failStackThirteenMax = 20;
        final double failStackFourteenMax = 25;
        final double failStackFifteenMax = 25;

        final double eightChance = 20.00;
        final double nineChance = 17.50;
        final double tenChance = 15.00;
        final double elevenChance = 12.50;
        final double twelveChance = 10.00;
        final double thirteenChance = 7.50;
        final double fourteenChance = 5.00;
        final double fifteenChance = 2.50;

        String currentRankEntered = currentRank.getText().toString();
        String currentFailEntered = currentFail.getText().toString();

        double rank = Double.parseDouble(currentRankEntered);
        double fail = Double.parseDouble(currentFailEntered);

        if (rank < 7) {
            currentChanceValue = 100;
        } else if (rank == 7) {
            if (fail >= failStackEightMax) {
                currentChanceValue = eightChance + (failStackEight * failStackEightMax);
            } else if (fail < failStackEightMax) {
                currentChanceValue = eightChance + (fail * failStackEight);
            }
        } else if (rank == 8) {
            if (fail >= failStackNineMax) {
                currentChanceValue = nineChance + (failStackNine * failStackNineMax);
            } else if (fail < failStackNineMax) {
                currentChanceValue = nineChance + (fail * failStackNine);
            }
        } else if (rank == 9) {
            if (fail >= failStackTenMax) {
                currentChanceValue = tenChance + (failStackTen * failStackTenMax);
            } else if (fail < failStackTenMax) {
                currentChanceValue = tenChance + (fail * failStackTen);
            }
        } else if (rank == 10) {
            if (fail >= failStackElevenMax) {
                currentChanceValue = elevenChance + (failStackEleven * failStackElevenMax);
            } else if (fail < failStackElevenMax) {
                currentChanceValue = elevenChance + (fail * failStackEleven);
            }
        } else if (rank == 11) {
            if (fail >= failStackTwelveMax) {
                currentChanceValue = twelveChance + (failStackTwelve * failStackTwelveMax);
            } else if (fail < failStackTwelveMax) {
                currentChanceValue = twelveChance + (fail * failStackTwelve);
            }
        } else if (rank == 12) {
            if (fail >= failStackThirteenMax) {
                currentChanceValue = thirteenChance + (12.50);
            } else if (fail < failStackThirteenMax) {
                currentChanceValue = thirteenChance + (fail * failStackThirteen);
            }
        } else if (rank == 13) {
            if (fail >= failStackFourteenMax) {
                currentChanceValue = fourteenChance + (failStackFourteen * failStackFourteenMax);
            } else if (fail < failStackFourteenMax) {
                currentChanceValue = fourteenChance + (fail * failStackFourteen);
            }
        } else if (rank == 14) {
            if (fail >= failStackFifteenMax) {
                currentChanceValue = fifteenChance + (failStackFifteen * failStackFifteenMax);
            } else if (fail < failStackFifteenMax) {
                currentChanceValue = fifteenChance + (fail * failStackFifteen);
            }
        } else if (rank >= 15) {
            currentChanceValue = 0;
        } else if (rank <= 0) {
            currentChanceValue = 0;
        }
        return currentChanceValue;
    }
}