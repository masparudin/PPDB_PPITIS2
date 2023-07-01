package com.mascode.ppdbppitis2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WebUjian extends AppCompatActivity {
    String No_reg;
    TextView textViewKey;
    String studentKey;
    String link;
    ImageView imgCopy,imgCopy2;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_web_ujian);
        imgCopy = (ImageView)findViewById(R.id.img_copy);
        studentKey = getIntent().getStringExtra("STUDENT_KEY");
        link = getIntent().getStringExtra("Link");


        textViewKey = (TextView)findViewById(R.id.text_key);
        textViewKey.setText(studentKey);

        imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String text;
                text = textViewKey.getText().toString();

                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(getApplicationContext(), "No ID tercopy", Toast.LENGTH_SHORT).show();
            }
        });


        if (link != null) {

            webView = (WebView) findViewById(R.id.webviewBerita);
            startWebView(link);

        }else {
            Toast.makeText(this, "Link Form tidak valid", Toast.LENGTH_LONG).show();
        }
    }

    private void startWebView(String url) {
        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(WebUjian.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
            }

        });

        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);

        // Other webview options

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);


        /*
         String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null);
         */

        //Load url in webview
        webView.loadUrl(url);


    }

    // Open previous opened link from history on webview when back button pressed

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        IsFinish("Penting! Pastikan anda sudah selesai menjawab semua soal yang ada, sebelum meninggalkan halaman ini");
    }

    public void IsFinish(String alertmessage) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent menu_utama = new Intent(WebUjian.this, MainMenu.class);
                        menu_utama.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        menu_utama.putExtra("tipe", "2");
                        startActivity(menu_utama);

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(alertmessage)
                .setPositiveButton("Sudah", dialogClickListener)
                .setNegativeButton("Belum", dialogClickListener).show();

    }


}