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

public class PilihJenisKelamin extends AppCompatActivity {

    int jumlah_akhwat, jumlah_ikhwan;
    String unit, init_num;
    LinearLayout linearLayoutIkhwan, linearLayoutAkhwat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pilih_jenis_kelamin);
        Toolbar toolbar = (Toolbar)findViewById(R.id.mytoolbar);

        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (getIntent() !=null){
            unit = getIntent().getStringExtra("UNIT");

        }
        else {
            Intent masuk = new Intent(PilihJenisKelamin.this, MainActivity2.class);
            Toast.makeText(this, "user tidak sedang login, silahkan login kembali", Toast.LENGTH_SHORT).show();
            startActivity(masuk);
        }
        if (unit.contentEquals("smpit")){
            jumlah_akhwat = 200;
            jumlah_ikhwan = 200;
        }else if (unit.contentEquals("smait")){
            jumlah_akhwat = 75;
            jumlah_ikhwan = 75;
        }else if (unit.contentEquals("swtqis")){
            jumlah_ikhwan = 40;
            jumlah_akhwat = 60;
        }else if (unit.contentEquals("sutqis")){
            jumlah_akhwat = 40;
            jumlah_ikhwan = 30;
        }

        linearLayoutIkhwan = (LinearLayout)findViewById(R.id.ly_ikhwan);
        linearLayoutIkhwan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unit.contentEquals("smpit")){
                    init_num = "1_Sudah Bayar";
                }else if (unit.contentEquals("smait")){
                    init_num = "3_Sudah Bayar";
                }else if (unit.contentEquals("swtqis")){
                    init_num = "5_Sudah Bayar";
                }else if (unit.contentEquals("sutqis")){
                    init_num = "7_Sudah Bayar";
                }else {
                    Toast.makeText(PilihJenisKelamin.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }

                //////
                FirebaseDbHelper.getChildCountOfChildOf("Pendaftar", "init_ujkb", init_num, new FirebaseDbHelper.OngetChildCountListeaner() {
                    @Override
                    public void onGetCount(Long count) {
                        if (count>=jumlah_ikhwan){
                            Toast.makeText(PilihJenisKelamin.this, "Maaf, kuota pendaftar ikhwan telah mencapai batas", Toast.LENGTH_LONG).show();
                        }else {
                            Intent ikhwan = new Intent(PilihJenisKelamin.this, FormDaftar.class);
                            ikhwan.putExtra("UNIT", unit);
                            ikhwan.putExtra("GENDER", "Ikhwan");
                            startActivity(ikhwan);
                        }
                    }
                });
            }
        });

        linearLayoutAkhwat = (LinearLayout)findViewById(R.id.ly_akhwat);
        linearLayoutAkhwat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (unit.contentEquals("smpit")){
                    init_num = "2_Sudah Bayar";
                }else if (unit.contentEquals("smait")){
                    init_num = "4_Sudah Bayar";
                }else if (unit.contentEquals("swtqis")){
                    init_num = "6_Sudah Bayar";
                }else if (unit.contentEquals("sutqis")){
                    init_num = "8_Sudah Bayar";
                }else {
                    Toast.makeText(PilihJenisKelamin.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }

                /////////////////
                FirebaseDbHelper.getChildCountOfChildOf("Pendaftar", "init_ujkb", init_num, new FirebaseDbHelper.OngetChildCountListeaner() {
                    @Override
                    public void onGetCount(Long count) {
                        if (count>=jumlah_akhwat){
                            Toast.makeText(PilihJenisKelamin.this, "Maaf, kuota pendaftar Akhwat telah mencapai batas", Toast.LENGTH_LONG).show();
                        }else {
                            Intent ikhwan = new Intent(PilihJenisKelamin.this, FormDaftar.class);
                            ikhwan.putExtra("UNIT", unit);
                            ikhwan.putExtra("GENDER", "Akhwat");
                            startActivity(ikhwan);
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