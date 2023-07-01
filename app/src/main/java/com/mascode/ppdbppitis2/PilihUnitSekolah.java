package com.mascode.ppdbppitis2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mascode.ppdbppitis2.Model.FirebaseDbHelper;

public class PilihUnitSekolah extends AppCompatActivity {


    String email;
    LinearLayout ly_smpit, ly_smait, ly_swtqis, ly_sutqis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pilih_unit_sekolah);

        Toolbar toolbar = (Toolbar)findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ly_smait = (LinearLayout)findViewById(R.id.ly_smait);
        ly_smait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDbHelper.getChildCountOfChildOf("Pendaftar", "unit", "smait", new FirebaseDbHelper.OngetChildCountListeaner() {
                    @Override
                    public void onGetCount(Long count) {
                        if (count>=150){
                            Toast.makeText(PilihUnitSekolah.this, "Maaf, kuota pendaftar unit SMAIT telah mencapai batas", Toast.LENGTH_SHORT).show();
                        }else {
                            Intent smait = new Intent(PilihUnitSekolah.this, PilihJenisKelamin.class);
                            smait.putExtra("UNIT", "smait");
                            startActivity(smait);
                        }
                    }
                });

            }
        });

        ly_smpit = (LinearLayout)findViewById(R.id.ly_smpit);
        ly_smpit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDbHelper.getChildCountOfChildOf("Pendaftar", "unit", "smpit", new FirebaseDbHelper.OngetChildCountListeaner() {
                    @Override
                    public void onGetCount(Long count) {
                        if(count>=400) {
                            Toast.makeText(PilihUnitSekolah.this, "Maaf, kuota pendaftar unit SMPIT telah mencapai batas", Toast.LENGTH_LONG).show();
                        }else{
                            Intent smpit = new Intent(PilihUnitSekolah.this, PilihJenisKelamin.class);
                            smpit.putExtra("UNIT", "smpit");
                            startActivity(smpit);
                        }
                    }
                });

            }
        });

        ly_swtqis = (LinearLayout)findViewById(R.id.ly_swtqis);
        ly_swtqis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDbHelper.getChildCountOfChildOf("Pendaftar", "unit", "swtqis", new FirebaseDbHelper.OngetChildCountListeaner() {
                    @Override
                    public void onGetCount(Long count) {
                        if (count>=100){
                            Toast.makeText(PilihUnitSekolah.this, "Maaf, kuota pendaftar unit SWTQIS telah mencapai batas", Toast.LENGTH_LONG).show();
                        }else {
                            Intent swtqis = new Intent(PilihUnitSekolah.this, PilihJenisKelamin.class);
                            swtqis.putExtra("UNIT", "swtqis");
                            startActivity(swtqis);
                        }
                    }
                });
            }
        });

        ly_sutqis = (LinearLayout)findViewById(R.id.ly_sutqis);
        ly_sutqis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDbHelper.getChildCountOfChildOf("Pendaftar", "unit", "sutqis", new FirebaseDbHelper.OngetChildCountListeaner() {
                    @Override
                    public void onGetCount(Long count) {
                        if (count>=70){
                            Toast.makeText(PilihUnitSekolah.this, "Maaf, kuota pendaftar unit SUTQIS telah mencapai batas", Toast.LENGTH_LONG).show();
                        }else {
                            Intent sutqis = new Intent(PilihUnitSekolah.this, PilihJenisKelamin.class);
                            sutqis.putExtra("UNIT", "sutqis");
                            startActivity(sutqis);
                        }
                    }
                });

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}