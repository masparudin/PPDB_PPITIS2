package com.mascode.ppdbppitis2;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tomer.fadingtextview.FadingTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SplashScreen extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference pembaruan;
    String statusUpdate="";
    CountDownTimer countdowntimer;
    ConstraintLayout bg;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        FadingTextView FTV = (FadingTextView) findViewById(R.id.fadingTextView);

        //For text change every 650 milliseconds (0.65 seconds)
        FTV.setTimeout(650, MILLISECONDS);

        //animation background
        bg = (ConstraintLayout) findViewById(R.id.linear_layout_bg_login);
        AnimationDrawable animationDrawable = (AnimationDrawable) bg.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        //cek  internet connection
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;


            //cek koneksi firebase server
            DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
            connectedRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean connected = snapshot.getValue(Boolean.class);
                    if (connected) {

                        checkVersionThenDo();

                    } else {
                        Log.d("Listener Status", "is was cancelled");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(SplashScreen.this, "Error 6676", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            connected = false;
            Intent nic = new Intent(SplashScreen.this, NoInternetConnection.class);
            startActivity(nic);
            finish();
        }

    }

    private void checkVersionThenDo()
    {
        /**
         * checks for the version then  pop up the update activity if we need an update
         */
        database = FirebaseDatabase.getInstance();
        pembaruan = database.getReference("update_version");

        pembaruan.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String version = dataSnapshot.getValue(String.class);
                String currentVersion = getString(R.string.current_version);
                if(currentVersion.contentEquals(version))
                {
                    Intent halamanUtama = new Intent(SplashScreen.this, MainActivity2.class);
                    startActivity(halamanUtama);
                    finish();
                }else{
                    checkForUpdatePriority();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void checkForUpdatePriority() {
        /**
         * checks for the version prioraty then  pop up the update activity if we need an update
         */
        database = FirebaseDatabase.getInstance();
        pembaruan = database.getReference("update_priority");

        pembaruan.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String versionPrioraty = dataSnapshot.getValue(String.class);

                if(versionPrioraty.contentEquals("1"))
                {
                    //here it means the update is a must
                    statusUpdate = "01";
                    Intent halamanUpdate = new Intent(SplashScreen.this, HalamanUpdate.class);//HalamanUpdate.class
                    halamanUpdate.putExtra("StatusUpdate", statusUpdate);
                    startActivity(halamanUpdate);
                    finish();
                }else if(versionPrioraty.contentEquals("2"))
                {
                    //here it means the update is optional
                    statusUpdate = "02";
                    Intent halamanUpdate = new Intent(SplashScreen.this, HalamanUpdate.class);//HalamanUpdate.class
                    halamanUpdate.putExtra("StatusUpdate", statusUpdate);
                    startActivity(halamanUpdate);
                    finish();
                }else{
                    //here is means the prioraty is three
                    Intent halamanLogin = new Intent(SplashScreen.this, MainActivity2.class);
                    startActivity(halamanLogin);
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
