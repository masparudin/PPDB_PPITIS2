package com.mascode.ppdbppitis2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mascode.ppdbppitis2.Model.FirebaseDbHelper;

public class KirimBuktiTf extends AppCompatActivity {
    private WebView webView = null;

    String operator;
    String unit, uid, nama, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirim_bukti_tf);


        Intent intent = getIntent();
        if (intent != null){
            unit = intent.getStringExtra("Unit");
            uid = intent.getStringExtra("Id");
            nama = intent.getStringExtra("Nama");
            email = intent.getStringExtra("Email");
            //pilih nomor telepon unit
            if (unit.contentEquals("smpit")){
                operator = intent.getStringExtra("WA_SMP");// ust agus, ust farid, ust sabran, ust rio
            }else if (unit.contentEquals("smait")){
                operator = intent.getStringExtra("WA_SMA"); // ust saiful
            }else if (unit.contentEquals("swtqis")){
                operator = intent.getStringExtra("WA_SWTQIS"); // ust rian firmandes
            }else {
                operator = intent.getStringExtra("WA_SUTQIS"); // ust amiruddin (su)
            }

        }else {
            Toast.makeText(this, "Kesalahan menerima data", Toast.LENGTH_SHORT).show();
        }


        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(webViewClient);
        String link = "https://api.whatsapp.com/send?phone="+operator+"&text=Bismillah%0AAssalamualaikum,%20ustadz.%0ASaya%20ingin%20mengirimkan%20bukti%20transfer,%20Berikut%20adalah%20datanya:%0A*ID%20:%20"+uid+"*%0ANama%20Pendaftar%20:%20"+nama+"%0AUnit%20:%20"+unit+"%0A%0a%0aDari%20:%20"+email;
        webView.loadUrl(link);
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