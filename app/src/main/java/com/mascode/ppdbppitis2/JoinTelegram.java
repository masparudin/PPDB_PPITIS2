package com.mascode.ppdbppitis2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class JoinTelegram extends AppCompatActivity {

    private WebView webView = null;

    String unit, grup_invitation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_join_telegram);

        Intent intent = getIntent();
        if (intent != null){
            unit = intent.getStringExtra("Unit");
        }else {
            Toast.makeText(this, "Kesalahan menerima data", Toast.LENGTH_SHORT).show();
        }
        //pilih nomor telepon unit
        if (unit.contentEquals("smpit")){
            grup_invitation = "https://t.me/+ycW_eg2G8HwxNDBl";//SMP
        }else if (unit.contentEquals("smait")){
            grup_invitation = "https://t.me/+rstT_uesaS5lMWJl";//SMA
        }else if (unit.contentEquals("swtqis")){
            grup_invitation = "https://t.me/+LeSoDKeufkY0ZDU1";//SWTQIS
        }else {
            grup_invitation = "https://t.me/+w590CwtwDjBiNjll";//SUTQIS
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