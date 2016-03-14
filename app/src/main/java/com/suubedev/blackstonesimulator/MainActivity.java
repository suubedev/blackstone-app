package com.suubedev.blackstonesimulator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView enchantButton;
    ImageView resetButton;
    ImageView historyButton;
    ImageView calcButton;
    TextView currentRank;
    TextView stackCount;
    TextView stoneCount;
    TextView successChance;
    Integer rank = 0;
    Integer stones = 0;
    Integer failStack = 0;
    CheckBox oneShotOrNot;

    InterstitialAd mInterstitialAd;

    static final String CURRENT_RANK = "0";
    static final String CURRENT_FAIL = "0";
    static final String CURRENT_STONE = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState != null) {
            rank = savedInstanceState.getInt(CURRENT_RANK);
            stones = savedInstanceState.getInt(CURRENT_STONE);
            failStack = savedInstanceState.getInt(CURRENT_FAIL);
        }

        enchantButton = (ImageView) findViewById(R.id.enchantButton);
        resetButton = (ImageView) findViewById(R.id.resetButton);
        currentRank = (TextView) findViewById(R.id.currentRank);
        stackCount = (TextView) findViewById(R.id.failstackCount);
        stoneCount  = (TextView) findViewById(R.id.stoneCount);
        successChance = (TextView) findViewById(R.id.successChance);
        historyButton = (ImageView) findViewById(R.id.historyButton);
        calcButton = (ImageView) findViewById(R.id.calcMain);
        oneShotOrNot = (CheckBox) findViewById(R.id.checkBox);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8239207983137121/8560338893");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                gotoHistory();
            }
        });

        requestNewInterstitial();


        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
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
                Intent i = new Intent(v.getContext(), CalculatorActivity.class);
                startActivity(i);
            }
        });

        enchantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!oneShotOrNot.isChecked()) {
                    enchant();
                } else {
                    if (rank == 15) {
                        reset();
                    }
                    oneShot();
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(CURRENT_FAIL, failStack);
        savedInstanceState.putInt(CURRENT_RANK, rank);
        savedInstanceState.putInt(CURRENT_STONE, stones);

        super.onSaveInstanceState(savedInstanceState);
    }



    public void oneShot() {
        while (rank < 15) {
            enchant();
        }
    }

    public void enchant() {
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

        final DecimalFormat precision = new DecimalFormat("0.00");

                stones++;
                stoneCount.setText(stones.toString());
                double chance = randomNumber();
                double currentChance = 0;

                if (rank < 7) {
                    rankUp();

                } else if (rank == 7) {
                    if (failStack >= failStackEightMax) {
                        currentChance = eightChance + (failStackEight * failStackEightMax);
                    } else if (failStack < failStackEightMax) {
                        currentChance = eightChance + (failStack * failStackEight);
                    }
                    if (chance <= currentChance) {
                        rankUp();
                        successChance.setText(precision.format(nineChance) + "% Chance");
                    } else {
                        failStack++;
                        stackCount.setText(failStack.toString());
                        currentChance = eightChance + (failStack * failStackEight);
                        successChance.setText(precision.format(currentChance) + "% Chance");

                    }

                } else if (rank == 8) {
                    if (failStack >= failStackNineMax) {
                        currentChance = nineChance + (failStackNine * failStackNineMax);
                    } else if (failStack <= failStackNineMax) {
                        currentChance = nineChance + (failStack * failStackNine);
                    }
                    if (chance <= currentChance) {
                        rankUp();
                        successChance.setText(precision.format(tenChance) + "% Chance");
                    } else {
                        failStack++;
                        stackCount.setText(failStack.toString());
                        currentChance = nineChance + (failStack * failStackNine);
                        successChance.setText(precision.format(currentChance) + "% Chance");
                    }

                } else if (rank == 9) {
                    if (failStack >= failStackTenMax) {
                        currentChance = tenChance + (failStackTen * failStackTenMax);
                    } else if (failStack <= failStackTenMax) {
                        currentChance = tenChance + (failStack * failStackTen);
                    }
                    if (chance <= currentChance) {
                        rankUp();
                        successChance.setText(precision.format(elevenChance) + "% Chance");
                    } else {
                        failStack++;
                        stackCount.setText(failStack.toString());
                        currentChance = tenChance + (failStack * failStackTen);
                        successChance.setText(precision.format(currentChance) + "% Chance");
                    }

                } else if (rank == 10) {
                    if (failStack >= failStackElevenMax) {
                        currentChance = elevenChance + (failStackEleven * failStackElevenMax);
                    } else if (failStack <= failStackElevenMax) {
                        currentChance = elevenChance + (failStack * failStackEleven);
                    }
                    if (chance <= currentChance) {
                        rankUp();
                        successChance.setText(precision.format(twelveChance) + "% Chance");
                    } else {
                        failStack++;
                        stackCount.setText(failStack.toString());
                        currentChance = elevenChance + (failStack * failStackEleven);
                        successChance.setText(precision.format(currentChance) + "% Chance");
                    }

                } else if (rank == 11) {
                    if (failStack >= failStackTwelveMax) {
                        currentChance = twelveChance + (failStackTwelve * failStackTwelveMax);
                    } else if (failStack <= failStackTwelveMax) {
                        currentChance = twelveChance + (failStack * failStackTwelve);
                    }
                    if (chance <= currentChance) {
                        rankUp();
                        successChance.setText(precision.format(thirteenChance) + "% Chance");
                    } else {
                        failStack++;
                        stackCount.setText(failStack.toString());
                        currentChance = twelveChance + (failStack * failStackTwelve);
                        successChance.setText(precision.format(currentChance) + "% Chance");
                    }

                } else if (rank == 12) {
                    if (failStack >= failStackThirteenMax) {
                        currentChance = thirteenChance + (failStackThirteen * failStackThirteenMax);
                    } else if (failStack <= failStackThirteenMax) {
                        currentChance = thirteenChance + (failStack * failStackThirteen);
                    }
                    if (chance <= currentChance) {
                        rankUp();
                        successChance.setText(precision.format(fourteenChance) + "% Chance");
                    } else {
                        failStack++;
                        stackCount.setText(failStack.toString());
                        currentChance = thirteenChance + (failStack * failStackThirteen);
                        successChance.setText(precision.format(currentChance) + "% Chance");
                    }

                } else if (rank == 13) {
                    if (failStack >= failStackFourteenMax) {
                        currentChance = fourteenChance + (failStackFourteen * failStackFourteenMax);
                    } else if (failStack <= failStackFourteenMax) {
                        currentChance = fourteenChance + (failStack * failStackFourteen);
                    }
                    if (chance <= currentChance) {
                        rankUp();
                        successChance.setText(precision.format(fifteenChance) + "% Chance");
                    } else {
                        failStack++;
                        stackCount.setText(failStack.toString());
                        currentChance = fourteenChance + (failStack * failStackFourteen);
                        successChance.setText(precision.format(currentChance) + "% Chance");
                    }

                } else if (rank == 14) {
                    if (failStack >= failStackFifteenMax) {
                        currentChance = fifteenChance + (failStackFifteen * failStackFifteenMax);
                    } else if (failStack <= failStackFifteenMax) {
                        currentChance = fifteenChance + (failStack * failStackFifteen);
                    }
                    if (chance <= currentChance) {
                        rankUp();
                        successChance.setText("Congratulations!");

                        if(!oneShotOrNot.isChecked()) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Congrats you're OP now! " + stones + " stones used.", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        DBAdapter adapter = new DBAdapter(getApplicationContext());
                        adapter.enterCount(stones);
                        adapter.close();
                    } else {
                        failStack++;
                        stackCount.setText(failStack.toString());
                        currentChance = fifteenChance + (failStack * failStackFifteen);
                        successChance.setText(precision.format(currentChance) + "% Chance");
                    }
                }
            }

    public void gotoHistory() {
        Intent i = new Intent(this, HistoryActivity.class);
        startActivity(i);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    public double randomNumber() {
        Random successRange = new Random();
        double value = successRange.nextDouble() * ((100 - 1) + 1);
        return value;
    }

    public void reset() {
        rank = 0;
        stones = 0;
        failStack = 0;
        currentRank.setText("0");
        stackCount.setText("0");
        stoneCount.setText("0");
        successChance.setText("Good Luck!");
    }

    public void rankUp() {
        rank++;
        currentRank.setText(rank.toString());
        stoneCount.setText(stones.toString());
        failStack = 0;
        stackCount.setText(failStack.toString());
    }
}