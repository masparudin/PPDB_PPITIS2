package com.mascode.ppdbppitis2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class WebTentangKami extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_web_tentang_kami);

            //https://sites.google.com/ppitimamsyafii.sch.id/ppdbppitimamsyafii2020/home
            Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
            setSupportActionBar(toolbar);

            // add back arrow to toolbar
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
            LinearLayout linearLayoutRating = (LinearLayout) findViewById(R.id.ly_rating_us);
            linearLayoutRating.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    openPlayStore();
                }
            });


        }

        public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
            if (paramMenuItem.getItemId() == 16908332)
                finish();
            return super.onOptionsItemSelected(paramMenuItem);
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
