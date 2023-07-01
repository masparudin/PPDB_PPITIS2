package com.mascode.ppdbppitis2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class NoInternetConnection extends AppCompatActivity {
    Button btn_reload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_no_internet_connection);
        btn_reload = (Button)findViewById(R.id.button_reload);
        btn_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cek koneksi internet
                boolean connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                    Intent splashscreen = new Intent(NoInternetConnection.this,SplashScreen.class);
                    startActivity(splashscreen);
                    finish();
                }
                else {
                    connected = false;
                    Intent nic = new Intent(NoInternetConnection.this,NoInternetConnection.class);
                    startActivity(nic);
                    finish();
                }
            }
        });


    }
}
