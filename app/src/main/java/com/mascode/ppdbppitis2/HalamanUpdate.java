package com.mascode.ppdbppitis2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HalamanUpdate extends AppCompatActivity {
    Button updateButton, lainkaliButton;
    String statusUpdate ="";
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_halaman_update);

        updateButton = (Button)findViewById(R.id.button_update);
        lainkaliButton = (Button)findViewById(R.id.button_skip);

        if (getIntent()!= null)
            statusUpdate = getIntent().getStringExtra("StatusUpdate");
        if (!statusUpdate.isEmpty())
        {
            if(statusUpdate.equals("01")){
                //username found
                lainkaliButton.setEnabled(true);
                lainkaliButton.setText("KELUAR");
                lainkaliButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

            }else if (statusUpdate.equals("02")){
                lainkaliButton.setEnabled(true);
                lainkaliButton.setText("LAIN KALI");
                lainkaliButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent home1 = new Intent(HalamanUpdate.this, MainActivity2.class);
                        startActivity(home1);
                        finish();
                    }
                });
            }else {
                Toast.makeText(this, "ERROR 5667", Toast.LENGTH_LONG).show();
            }
        }


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayStore();

            }
        });



    }

    public void openPlayStore() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }



}
