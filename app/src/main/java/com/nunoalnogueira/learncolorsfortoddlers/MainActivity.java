package com.nunoalnogueira.learncolorsfortoddlers;

import android.content.DialogInterface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
// 22.05.2022 import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener {

    Integer[] images_numbers_1 = {
            R.drawable.a_black,
            R.drawable.a_blue,
            R.drawable.a_brown,
            R.drawable.a_green,
            R.drawable.a_grey,
            R.drawable.a_orange,
            R.drawable.a_pink,
            R.drawable.a_purple,
            R.drawable.a_red,
            R.drawable.a_white,
            R.drawable.a_yellow,
    };

    Integer[] images_objects_1 = {
            R.drawable.a_black_img,
            R.drawable.a_blue_img,
            R.drawable.a_brown_img,
            R.drawable.a_green_img,
            R.drawable.a_grey_img,
            R.drawable.a_orange_img,
            R.drawable.a_pink_img,
            R.drawable.a_purple_img,
            R.drawable.a_red_img,
            R.drawable.a_white_img,
            R.drawable.a_yellow_img,

    };

    Button button_banner, button_interstitial, button_reward;

    ImageView image1, image2, image3, image4, imageMain, imageMain1, img_next;

    pl.droidsonroids.gif.GifImageView img_gif_unlock;

    MediaPlayer music_background;
    //MediaPlayer music_background, sound_yeah, sound_no, sound_next;


    int reward, turn, correctAnswer, score, num_fails, num_games = 0, num_images = 9, check_interstitial = 0;

    Integer[] vector_images_numbers_1 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    //Integer[] images_numbers_bonus = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    int sound_yeah, sound_no, sound_next, b_black, b_blue, b_brown, b_green, b_grey, b_orange, b_pink, b_purple, b_red, b_white, b_yellow;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;
    private SoundPool soundPool;

    // 22.05.2022 private FirebaseAnalytics mFirebaseAnalytics;


    //AudioAttributes attributes;
    //AudioAttributes.Builder attributesBuilder;

    //AudioManager audioManager;
    //float curVolume, maxVolume, volume;


    // DISABLE BACK BUTTON FUNCTION
    @Override
    public void onBackPressed() {
        //Toast.makeText(MainActivity.this, "sdfg", Toast.LENGTH_LONG).show();
    }

//##################################################################################################
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 22.05.2022 mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // public void initializeMobileAds() {

        // TEST: ca-app-pub-3940256099942544~3347511713
        // TRUE: ca-app-pub-xxxxx
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        //}

        //initializeMobileAds();
        //initializeRewardAd();

        button_banner = (Button) findViewById(R.id.button_banner);
        button_interstitial = (Button) findViewById(R.id.button_interstitial);
        button_reward = (Button) findViewById(R.id.button_reward);

        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        imageMain = (ImageView) findViewById(R.id.imageMain);
        imageMain1 = (ImageView) findViewById(R.id.imageMain1);
        img_next = (ImageView) findViewById(R.id.img_next);

        img_gif_unlock = (pl.droidsonroids.gif.GifImageView) findViewById(R.id.img_gift_unlock);


        //sound_yeah = MediaPlayer.create(MainActivity.this, R.raw.sound_yeah);
        //sound_no = MediaPlayer.create(MainActivity.this, R.raw.sound_no);
        //sound_next = MediaPlayer.create(MainActivity.this, R.raw.sound_next);

        //setVolumeControlStream(AudioManager.STREAM_MUSIC);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
            soundPool = new SoundPool.Builder().setMaxStreams(4).setAudioAttributes(audioAttributes).build();
        } else {
            soundPool = new SoundPool(29, AudioManager.STREAM_MUSIC, 0);
        }

        sound_yeah = soundPool.load(this, R.raw.sound_yeah, 1);
        sound_no = soundPool.load(this, R.raw.sound_no, 1);
        sound_next = soundPool.load(this, R.raw.sound_next, 1);
        b_black = soundPool.load(this, R.raw.b_black, 1);
        b_blue = soundPool.load(this, R.raw.b_blue, 1);
        b_brown = soundPool.load(this, R.raw.b_brown, 1);
        b_green = soundPool.load(this, R.raw.b_green, 1);
        b_grey = soundPool.load(this, R.raw.b_grey, 1);
        b_orange = soundPool.load(this, R.raw.b_orange, 1);
        b_pink = soundPool.load(this, R.raw.b_pink, 1);
        b_purple = soundPool.load(this, R.raw.b_purple, 1);
        b_red = soundPool.load(this, R.raw.b_red, 1);
        b_white = soundPool.load(this, R.raw.b_white, 1);
        b_yellow = soundPool.load(this, R.raw.b_yellow, 1);

        /*audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        curVolume = (float)audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = (float)audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = curVolume/maxVolume;*/

        music_background = MediaPlayer.create(MainActivity.this, R.raw.music_background);
        music_background.setLooping(true);
        music_background.setVolume(0.3f, 0.3f);


        startGame();

        //setImages();

//##################################################################################################

        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // CRASHLYTICS CRASH TEST
                //Crashlytics.getInstance().crash();


                turn++;

                if (turn >= 10) {
                    checkEnd();
                } else {
                    //sound_next.start();
                    soundPool.play(sound_next, 1, 1, 0, 0, 1);
                    if (num_images == 9) {
                        imageMain1.setVisibility(View.INVISIBLE);
                        setImages();
                    } else {
                        //setImagesBonus();

                    }
                }
            }
        });

        imageMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (vector_images_numbers_1[turn] == 0)
                    soundPool.play(b_black, 1, 1, 0, 0, 1);
                else if (vector_images_numbers_1[turn] == 1)
                    soundPool.play(b_blue, 1, 1, 0, 0, 1);
                else if (vector_images_numbers_1[turn] == 2)
                    soundPool.play(b_brown, 1, 1, 0, 0, 1);
                else if (vector_images_numbers_1[turn] == 3)
                    soundPool.play(b_green, 1, 1, 0, 0, 1);
                else if (vector_images_numbers_1[turn] == 4)
                    soundPool.play(b_grey, 1, 1, 0, 0, 1);
                else if (vector_images_numbers_1[turn] == 5)
                    soundPool.play(b_orange, 1, 1, 0, 0, 1);
                else if (vector_images_numbers_1[turn] == 6)
                    soundPool.play(b_pink, 1, 1, 0, 0, 1);
                else if (vector_images_numbers_1[turn] == 7)
                    soundPool.play(b_purple, 1, 1, 0, 0, 1);
                else if (vector_images_numbers_1[turn] == 8)
                    soundPool.play(b_red, 1, 1, 0, 0, 1);
                else if (vector_images_numbers_1[turn] == 9)
                    soundPool.play(b_white, 1, 1, 0, 0, 1);
                else if (vector_images_numbers_1[turn] == 10)
                    soundPool.play(b_yellow, 1, 1, 0, 0, 1);

            }
        });

        /*img_gif_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRewardAd();
            }
        });*/

        /*img_unlock_bonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRewardAd();
            }
        });*/

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (correctAnswer == 1) {
                    //sound_yeah.start();
                    //soundPool.play(sound_yeah, 1, 1, 0, 0, 1);
                    score++;
                    if (num_images == 9) {
                        imageMain1.setVisibility(View.VISIBLE);
                        imageMain1.setImageResource(images_numbers_1[vector_images_numbers_1[turn]]);

                        if (vector_images_numbers_1[turn] == 0)
                            soundPool.play(b_black, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 1)
                            soundPool.play(b_blue, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 2)
                            soundPool.play(b_brown, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 3)
                            soundPool.play(b_green, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 4)
                            soundPool.play(b_grey, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 5)
                            soundPool.play(b_orange, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 6)
                            soundPool.play(b_pink, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 7)
                            soundPool.play(b_purple, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 8)
                            soundPool.play(b_red, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 9)
                            soundPool.play(b_white, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 10)
                            soundPool.play(b_yellow, 1, 1, 0, 0, 1);

                    } else {
                        // imageMain.setImageResource(images_numbers_1[images_numbers_bonus[turn]]);
                    }
                    img_next.setVisibility(View.VISIBLE);
                } else {
                    //sound_no.start();
                    soundPool.play(sound_no, 1, 1, 0, 0, 1);
                    imageMain1.setVisibility(View.INVISIBLE);
                    img_next.setVisibility(View.INVISIBLE);
                    if (num_images == 9) {
                        imageMain1.setImageResource(images_objects_1[vector_images_numbers_1[turn]]);
                    } else {
                        //imageMain.setImageResource(images_objects_1[images_numbers_bonus[turn]]);
                    }
                    num_fails++;
                }
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (correctAnswer == 2) {
                    //sound_yeah.start();
                    //soundPool.play(sound_yeah, 1, 1, 0, 0, 1);
                    score++;
                    if (num_images == 9) {
                        imageMain1.setVisibility(View.VISIBLE);
                        imageMain1.setImageResource(images_numbers_1[vector_images_numbers_1[turn]]);

                        if (vector_images_numbers_1[turn] == 0)
                            soundPool.play(b_black, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 1)
                            soundPool.play(b_blue, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 2)
                            soundPool.play(b_brown, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 3)
                            soundPool.play(b_green, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 4)
                            soundPool.play(b_grey, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 5)
                            soundPool.play(b_orange, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 6)
                            soundPool.play(b_pink, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 7)
                            soundPool.play(b_purple, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 8)
                            soundPool.play(b_red, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 9)
                            soundPool.play(b_white, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 10)
                            soundPool.play(b_yellow, 1, 1, 0, 0, 1);

                    } else {
                        //imageMain.setImageResource(images_numbers_1[images_numbers_bonus[turn]]);
                    }
                    img_next.setVisibility(View.VISIBLE);
                } else {
                    //sound_no.start();
                    soundPool.play(sound_no, 1, 1, 0, 0, 1);
                    imageMain1.setVisibility(View.INVISIBLE);
                    img_next.setVisibility(View.INVISIBLE);
                    if (num_images == 9) {
                        imageMain1.setImageResource(images_objects_1[vector_images_numbers_1[turn]]);
                    } else {
                        //imageMain.setImageResource(images_objects_1[images_numbers_bonus[turn]]);
                    }
                    num_fails++;
                }
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (correctAnswer == 3) {
                    //sound_yeah.start();
                    //soundPool.play(sound_yeah, 1, 1, 0, 0, 1);
                    score++;
                    if (num_images == 9) {
                        imageMain1.setVisibility(View.VISIBLE);
                        imageMain1.setImageResource(images_numbers_1[vector_images_numbers_1[turn]]);

                        if (vector_images_numbers_1[turn] == 0)
                            soundPool.play(b_black, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 1)
                            soundPool.play(b_blue, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 2)
                            soundPool.play(b_brown, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 3)
                            soundPool.play(b_green, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 4)
                            soundPool.play(b_grey, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 5)
                            soundPool.play(b_orange, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 6)
                            soundPool.play(b_pink, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 7)
                            soundPool.play(b_purple, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 8)
                            soundPool.play(b_red, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 9)
                            soundPool.play(b_white, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 10)
                            soundPool.play(b_yellow, 1, 1, 0, 0, 1);

                    } else {
                        //imageMain.setImageResource(images_numbers_1[images_numbers_bonus[turn]]);
                    }
                    img_next.setVisibility(View.VISIBLE);
                } else {
                    //sound_no.start();
                    soundPool.play(sound_no, 1, 1, 0, 0, 1);
                    imageMain1.setVisibility(View.INVISIBLE);
                    img_next.setVisibility(View.INVISIBLE);
                    if (num_images == 9) {
                        imageMain1.setImageResource(images_objects_1[vector_images_numbers_1[turn]]);
                    } else {
                        //imageMain.setImageResource(images_objects_1[images_numbers_bonus[turn]]);
                    }
                    num_fails++;
                }
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (correctAnswer == 4) {
                    //sound_yeah.start();
                    //soundPool.play(sound_yeah, 1, 1, 0, 0, 1);
                    score++;
                    if (num_images == 9) {
                        imageMain1.setVisibility(View.VISIBLE);
                        imageMain1.setImageResource(images_numbers_1[vector_images_numbers_1[turn]]);

                        if (vector_images_numbers_1[turn] == 0)
                            soundPool.play(b_black, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 1)
                            soundPool.play(b_blue, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 2)
                            soundPool.play(b_brown, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 3)
                            soundPool.play(b_green, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 4)
                            soundPool.play(b_grey, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 5)
                            soundPool.play(b_orange, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 6)
                            soundPool.play(b_pink, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 7)
                            soundPool.play(b_purple, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 8)
                            soundPool.play(b_red, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 9)
                            soundPool.play(b_white, 1, 1, 0, 0, 1);
                        else if (vector_images_numbers_1[turn] == 10)
                            soundPool.play(b_yellow, 1, 1, 0, 0, 1);

                    } else {
                        //imageMain.setImageResource(images_numbers_1[images_numbers_bonus[turn]]);
                    }
                    img_next.setVisibility(View.VISIBLE);
                } else {
                    //sound_no.start();
                    soundPool.play(sound_no, 1, 1, 0, 0, 1);
                    imageMain1.setVisibility(View.INVISIBLE);
                    img_next.setVisibility(View.INVISIBLE);
                    if (num_images == 9) {
                        imageMain1.setImageResource(images_objects_1[vector_images_numbers_1[turn]]);
                    } else {
                        //imageMain.setImageResource(images_objects_1[images_numbers_bonus[turn]]);
                    }
                    num_fails++;
                }
            }
        });

//##################################################################################################

        button_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //initializeMobileAds();
                showBannerAd();
            }
        });

        button_interstitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //initializeMobileAds();
                initializeInterstitialAd();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showInterstitialAd();
                    }
                }, 3000);
            }
        });

        button_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //initializeMobileAds();
                //initializeRewardAd();
                showRewardAd();
            }
        });

//##################################################################################################


    }
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//##################################################################################################


    public void initializeInterstitialAd() {

        // TEST: ca-app-pub-3940256099942544/1033173712
        // TRUE: ca-app-pub-xxxxx
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                //Toast.makeText(MainActivity.this, "INTERSTITIAL LOADED", Toast.LENGTH_LONG).show();

                //button_interstitial.setVisibility(View.VISIBLE);

                check_interstitial = 1;
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

                check_interstitial = 0;


            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.

                //button_interstitial.setVisibility(View.INVISIBLE);

                music_background.pause();
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                // Load the next interstitial.
                //mInterstitialAd.loadAd(new AdRequest.Builder().build());

                startGame();
            }
        });


    }

    public void initializeRewardAd() {

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
    }

    private void loadRewardedVideoAd() {

        //TEST: ca-app-pub-3940256099942544/5224354917
        //TRUE:
        //mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
    }


    public void showBannerAd() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mAdView = findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }
        }, 3000);
    }

    public void showInterstitialAd() {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            //Toast.makeText(this, "Interstitial not loaded", Toast.LENGTH_SHORT).show();
        }
    }

    public void showRewardAd() {

        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

//##################################################################################################

    @Override
    public void onRewardedVideoAdLoaded() {

        if (reward == 0) {
            //img_gif_unlock.setVisibility(View.VISIBLE);

            //Toast.makeText(this, "REWARD VIDEO LOADED", Toast.LENGTH_SHORT).show();

            //button_reward.setVisibility(View.VISIBLE);
            //img_unlock_bonus.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRewardedVideoAdOpened() {

        //Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();

        img_gif_unlock.setVisibility(View.INVISIBLE);
        music_background.pause();
        //music_background = null;
    }

    @Override
    public void onRewardedVideoStarted() {

        //Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {

        //Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();

        if (reward == 0) {

            music_background.start();
            loadRewardedVideoAd();
        } else {

            startGame();
        }

        /*music_background = MediaPlayer.create(MainActivity.this, R.raw.music_background);
        music_background.setLooping(true);
        music_background.setVolume(0.5f, 0.5f);
        music_background.start();*/
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

        Toast.makeText(this, "CONGRATULATIONS! ALL UNLOCKED!", Toast.LENGTH_SHORT).show();

        //button_reward.setVisibility(View.INVISIBLE);

        img_gif_unlock.setVisibility(View.INVISIBLE);

        reward = 1;

        num_images = 40;

        music_background.start();

        //music_background.stop();
        //music_background = null;

        //startGame();

        //loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

        //Toast.makeText(this, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show();

        //loadRewardedVideoAd();

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

        //Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoCompleted() {

        //Toast.makeText(this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();

        //loadRewardedVideoAd();
    }

//##################################################################################################


    private void startGame() {

        //GARBAGE COLLECTOR
        //Runtime.getRuntime().gc(); //Art Android 6.0.1 Nexus 5x
        //System.gc();


        music_background.seekTo(0);
        music_background.start();

        num_games++;

        //initializeMobileAds();

        initializeInterstitialAd();

        showBannerAd();

        /*if (reward == 0) {
            initializeRewardAd();
        }*/

        turn = 0;
        correctAnswer = 0;
        score = 0;
        num_fails = 0;

        //music_background.start();

        //soundPool.play(music_background, 1, 1, 1, 0, 1);

        Collections.shuffle(Arrays.asList(vector_images_numbers_1));
        //Collections.shuffle(Arrays.asList(images_numbers_bonus));

        if (num_images == 9) {
            setImages();
        } else {
            //setImagesBonus();
        }


    }

//##################################################################################################

    private void setImages() {

        soundPool.play(sound_next, 1, 1, 0, 0, 1);

        imageMain1.setVisibility(View.INVISIBLE);

        img_next.setVisibility(View.INVISIBLE);

        Random r = new Random();

        correctAnswer = r.nextInt(4) + 1;

        int wrongAnwser1, wrongAnwser2, wrongAnwser3;

        do {
            wrongAnwser1 = r.nextInt(num_images);
        } while (wrongAnwser1 == vector_images_numbers_1[turn]);

        do {
            wrongAnwser2 = r.nextInt(num_images);
        } while (wrongAnwser2 == vector_images_numbers_1[turn] || wrongAnwser2 == wrongAnwser1);

        do {
            wrongAnwser3 = r.nextInt(num_images);
        }
        while (wrongAnwser3 == vector_images_numbers_1[turn] || wrongAnwser3 == wrongAnwser2 || wrongAnwser3 == wrongAnwser1);

        switch (correctAnswer) {

            case 1:
                image1.setImageResource(images_numbers_1[vector_images_numbers_1[turn]]);
                image2.setImageResource(images_numbers_1[wrongAnwser1]);
                image3.setImageResource(images_numbers_1[wrongAnwser2]);
                image4.setImageResource(images_numbers_1[wrongAnwser3]);
                break;

            case 2:
                image1.setImageResource(images_numbers_1[wrongAnwser1]);
                image2.setImageResource(images_numbers_1[vector_images_numbers_1[turn]]);
                image3.setImageResource(images_numbers_1[wrongAnwser2]);
                image4.setImageResource(images_numbers_1[wrongAnwser3]);
                break;

            case 3:
                image1.setImageResource(images_numbers_1[wrongAnwser1]);
                image2.setImageResource(images_numbers_1[wrongAnwser2]);
                image3.setImageResource(images_numbers_1[vector_images_numbers_1[turn]]);
                image4.setImageResource(images_numbers_1[wrongAnwser3]);
                break;

            case 4:
                image1.setImageResource(images_numbers_1[wrongAnwser1]);
                image2.setImageResource(images_numbers_1[wrongAnwser2]);
                image3.setImageResource(images_numbers_1[wrongAnwser3]);
                image4.setImageResource(images_numbers_1[vector_images_numbers_1[turn]]);
                break;
        }

        imageMain.setImageResource(images_objects_1[vector_images_numbers_1[turn]]);
    }

    /*private void setImagesBonus() {


        img_next.setVisibility(View.INVISIBLE);

        Random r = new Random();

        correctAnswer = r.nextInt(4) + 1;

        int wrongAnwser1, wrongAnwser2, wrongAnwser3;

        do {
            wrongAnwser1 = r.nextInt(num_images);
        } while (wrongAnwser1 == images_numbers_bonus[turn]);

        do {
            wrongAnwser2 = r.nextInt(num_images);
        } while (wrongAnwser2 == images_numbers_bonus[turn] || wrongAnwser2 == wrongAnwser1);

        do {
            wrongAnwser3 = r.nextInt(num_images);
        }
        while (wrongAnwser3 == images_numbers_bonus[turn] || wrongAnwser3 == wrongAnwser2 || wrongAnwser3 == wrongAnwser1);

        switch (correctAnswer) {

            case 1:
                image1.setImageResource(images_numbers_1[images_numbers_bonus[turn]]);
                image2.setImageResource(images_numbers_1[wrongAnwser1]);
                image3.setImageResource(images_numbers_1[wrongAnwser2]);
                image4.setImageResource(images_numbers_1[wrongAnwser3]);
                break;

            case 2:
                image1.setImageResource(images_numbers_1[wrongAnwser1]);
                image2.setImageResource(images_numbers_1[images_numbers_bonus[turn]]);
                image3.setImageResource(images_numbers_1[wrongAnwser2]);
                image4.setImageResource(images_numbers_1[wrongAnwser3]);
                break;

            case 3:
                image1.setImageResource(images_numbers_1[wrongAnwser1]);
                image2.setImageResource(images_numbers_1[wrongAnwser2]);
                image3.setImageResource(images_numbers_1[images_numbers_bonus[turn]]);
                image4.setImageResource(images_numbers_1[wrongAnwser3]);
                break;

            case 4:
                image1.setImageResource(images_numbers_1[wrongAnwser1]);
                image2.setImageResource(images_numbers_1[wrongAnwser2]);
                image3.setImageResource(images_numbers_1[wrongAnwser3]);
                image4.setImageResource(images_numbers_1[images_numbers_bonus[turn]]);
                break;
        }

        imageMain.setImageResource(images_objects_1[images_numbers_bonus[turn]]);
    }*/

//##################################################################################################

    private void checkEnd() {

        AlertDialog.Builder alertDialogBuider = new AlertDialog.Builder(this);
        alertDialogBuider.setCancelable(false);
        alertDialogBuider.setMessage("GAME FINISHED. You fail " + num_fails + " times in " + turn + " times.");

        alertDialogBuider.setPositiveButton(">>>>>  PLAY AGAIN  <<<<<", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //music_background.seekTo(0);
                //music_background = null;
                //soundPool.stop(music_background);

                if (check_interstitial == 1) {
                    showInterstitialAd();
                } else {
                    startGame();
                }
            }
        });

        AlertDialog showEndMessage = alertDialogBuider.create();
        showEndMessage.show();
    }

//##################################################################################################


}
