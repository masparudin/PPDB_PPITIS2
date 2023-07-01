package com.mascode.ppdbppitis2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class JoinWag1 extends AppCompatActivity {

    private WebView webView = null;

    String unit, grup_invitation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_join_wag1);

        Intent intent = getIntent();
        if (intent != null) {
            unit = intent.getStringExtra("Unit");
        } else {
            Toast.makeText(this, "Kesalahan menerima data", Toast.LENGTH_SHORT).show();
        }
        //pilih nomor telepon unit
        if (unit.contentEquals("smpit")) {
            grup_invitation = "https://chat.whatsapp.com/KkubsyxAJkm1OAxtJnwL7r";//SMP INFO PPDB
        } else if (unit.contentEquals("smait")) {
            grup_invitation = "https://chat.whatsapp.com/GeU3KXbtKcV6ClpUoTEHS3";//SMA INFO PPDB
        } else if (unit.contentEquals("swtqis")) {
            grup_invitation = "https://chat.whatsapp.com/I1aJM1y9bf3DGZ6GI51os3";//SWTQIS INFO PPDB
        } else {
            grup_invitation = "https://chat.whatsapp.com/HOQ2pFwTTMNKd73sC4KwJA";//SUTQIS INFO PPDB
        }

        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(grup_invitation);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}