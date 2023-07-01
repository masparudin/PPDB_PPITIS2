package com.mascode.ppdbppitis2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Detail_Pendaftar extends AppCompatActivity {
    String uid, nama, telepon, unit, status_bayar, status_seleksi, akun, no_registrasi, gender, waktu, gelombang;
    TextView text_uid, text_nama, text_telepon, text_unit, text_status_bayar, text_status_seleksi, text_akun, text_no_registrasi, text_gender, textViewUnit;
    LinearLayout linearLayoutSeleksi, linearLayoutNoReg, linearLayoutSelkes;
    Button buttonBayar, buttonJoinWag1,buttonJoinWag2;
    ImageView ImageUser;
    LinearLayout  linearLayoutBG2, linearLayoutWarnBayar, linearLayoutWarnTes;


    //String input data dari intent
    String seleksi_kesehatan, asal_sekolah, ayah, ibu, alamat, kecamatan, kelurahan, prestasi, provinsi, ttl, vaksinasi;

    //Textview terima data intent
    TextView tViewTTL, tViewAsalSekolah, tViewPrestasi, tViewVaksinasi, tVSelkes, tVAyah, tVIbu, tVAlamat, tVKelurahan, tVKecamatan, tVProvinsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pendaftar);
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        linearLayoutBG2 = (LinearLayout)findViewById(R.id.ly_bg_ket_bayar);
        linearLayoutSelkes = (LinearLayout)findViewById(R.id.ly_selkes);
        linearLayoutWarnBayar = (LinearLayout)findViewById(R.id.ly_warning_bayar);
        linearLayoutWarnTes = (LinearLayout) findViewById(R.id.ly_warning_tes);

        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        Intent intent = getIntent();
        if (intent != null){
            unit = intent.getStringExtra("Unit");
            uid = intent.getStringExtra("Id");
            nama = intent.getStringExtra("Nama");
            telepon = intent.getStringExtra("Telepon");
            status_bayar = intent.getStringExtra("Status_Bayar");
            status_seleksi = intent.getStringExtra("Status_Seleksi");
            akun = intent.getStringExtra("Akun");
            no_registrasi = intent.getStringExtra("No_Registrasi");
            gender = intent.getStringExtra("Gender");

            waktu = intent.getStringExtra("Waktu");
            alamat = intent.getStringExtra("Alamat");
            asal_sekolah = intent.getStringExtra("Asal_Sekolah");
            ayah = intent.getStringExtra("Ayah");
            ibu = intent.getStringExtra("Ibu");
            kecamatan = intent.getStringExtra("Kecamatan");
            kelurahan = intent.getStringExtra("Kelurahan");
            prestasi = intent.getStringExtra("Prestasi");
            provinsi = intent.getStringExtra("Provinsi");
            seleksi_kesehatan = intent.getStringExtra("Seleksi_Kesehatan");
            if (!seleksi_kesehatan.contentEquals("null")){
                linearLayoutSelkes.setVisibility(View.VISIBLE);
            }

            ttl = intent.getStringExtra("Ttl");
            vaksinasi = intent.getStringExtra("Vaksinasi");


        }else {
            Toast.makeText(this, "Kesalahan menerima data", Toast.LENGTH_SHORT).show();
        }

        linearLayoutNoReg = (LinearLayout)findViewById(R.id.ly_no_register);
        buttonJoinWag1 = (Button)findViewById(R.id.btn_join_wag1);
        buttonJoinWag2 = (Button)findViewById(R.id.btn_join_wag2);

        linearLayoutSeleksi = (LinearLayout)findViewById(R.id.ly_status_seleksi);
        text_no_registrasi = (TextView)findViewById(R.id.text_no_registrasi);
        ImageUser = (ImageView) findViewById(R.id.img_user);
        textViewUnit = (TextView) findViewById(R.id.textUnit);

        if(status_bayar.contentEquals("Sudah Bayar")){
            buttonJoinWag1.setVisibility(View.VISIBLE);
            buttonJoinWag2.setVisibility(View.VISIBLE);
            linearLayoutWarnBayar.setVisibility(View.GONE);
            linearLayoutWarnTes.setVisibility(View.VISIBLE);


            linearLayoutBG2.setBackgroundColor(Color.rgb(131,212,147));

        }else {
            linearLayoutBG2.setBackgroundColor(Color.rgb(242,128,128));
            buttonJoinWag1.setVisibility(View.GONE);
            buttonJoinWag2.setVisibility(View.GONE);

            linearLayoutWarnBayar.setVisibility(View.VISIBLE);
            linearLayoutWarnTes.setVisibility(View.GONE);
        }



        if (unit.contentEquals("smait")) {
            ImageUser.setImageResource(R.drawable.smait);
            if (gender.contentEquals("Ikhwan")){
                textViewUnit.setText("CALON SANTRI IKHWAN SMAS IT");
            }else {
                textViewUnit.setText("CALON SANTRI AKHWAT SMAS IT");
            }
        } else if (unit.contentEquals("smpit")) {
            ImageUser.setImageResource(R.drawable.smpit);
            if (gender.contentEquals("Ikhwan")){
                textViewUnit.setText("CALON SANTRI IKHWAN SMPS IT");
            }else {
                textViewUnit.setText("CALON SANTRI AKHWAT SMPS IT");
            }
        } else if (unit.contentEquals("swtqis")) {
            ImageUser.setImageResource(R.drawable.swtqis);
            if (gender.contentEquals("Ikhwan")){
                textViewUnit.setText("CALON SANTRI IKHWAN SWTQ");
            }else {
                textViewUnit.setText("CALON SANTRI AKHWAT SWTQ");
            }
        } else if (unit.contentEquals("sutqis")) {
            ImageUser.setImageResource(R.drawable.sutqis);
            if (gender.contentEquals("Ikhwan")){
                textViewUnit.setText("CALON SANTRI IKHWAN SUTQ");
            }else {
                textViewUnit.setText("CALON SANTRI AKHWAT SUTQ");
            }
        }

        //kode di bawah perlu dipindahkan ke bawah
        tVAlamat = (TextView) findViewById(R.id.text_alamat);
        tVAlamat.setText(alamat);
        tViewAsalSekolah = (TextView) findViewById(R.id.text_asal_sekolah);
        tViewAsalSekolah.setText(asal_sekolah);
        tVAyah = (TextView) findViewById(R.id.text_ayah);
        tVAyah.setText(ayah);
        tVIbu = (TextView) findViewById(R.id.text_ibu);
        tVIbu.setText(ibu);
        tVKecamatan = (TextView) findViewById(R.id.text_kecamatan);
        tVKecamatan.setText(kecamatan);
        tVKelurahan = (TextView) findViewById(R.id.text_kelurahan);
        tVKelurahan.setText(kelurahan);
        tViewPrestasi = (TextView) findViewById(R.id.text_prestasi);
        tViewPrestasi.setText(prestasi);
        tVProvinsi = (TextView) findViewById(R.id.text_provinsi);
        tVProvinsi.setText(provinsi);
        tVSelkes = (TextView) findViewById(R.id.text_selkes);
        tVSelkes.setText(seleksi_kesehatan);
        tViewTTL = (TextView) findViewById(R.id.text_ttl);
        tViewTTL.setText(ttl);
        tViewVaksinasi = (TextView) findViewById(R.id.text_vaksinasi);
        tViewVaksinasi.setText(vaksinasi);

        text_status_seleksi = (TextView)findViewById(R.id.text_status_seleksi);
        if (!status_seleksi.contentEquals("null")){
            linearLayoutSeleksi.setVisibility(View.VISIBLE);
            text_status_seleksi.setText(status_seleksi);
        }

        text_uid = (TextView)findViewById(R.id.text_uid);
        text_uid.setText(uid);
        text_nama = (TextView)findViewById(R.id.text_nama);
        text_nama.setText(nama);
        text_telepon = (TextView)findViewById(R.id.text_telepon);
        text_telepon.setText(telepon);
        text_unit = (TextView)findViewById(R.id.text_unit);
        text_unit.setText(unit);
        text_gender = (TextView)findViewById(R.id.text_gender);
        text_gender.setText(gender);



        text_status_bayar = (TextView)findViewById(R.id.text_status_bayar);
        text_status_bayar.setText(status_bayar);
        text_akun = (TextView)findViewById(R.id.text_akun);
        text_akun.setText(akun);

        buttonBayar = (Button)findViewById(R.id.button_bayar);
        if (status_bayar.contentEquals("Sudah Bayar")){
            buttonBayar.setVisibility(View.GONE);

            if(waktu.contentEquals("19122021") || waktu.contentEquals("20122021")
                || waktu.contentEquals("21122021") || waktu.contentEquals("22122021")
                || waktu.contentEquals("23122021") || waktu.contentEquals("24122021")
                || waktu.contentEquals("25122021")){
                gelombang = "1";
            }else {
                gelombang = "2";
            }
            StringBuffer c = new StringBuffer(uid);
            text_no_registrasi.setText(gelombang+c.reverse());

        }
        if (status_bayar.contentEquals("Belum Bayar")){
            buttonBayar.setVisibility(View.VISIBLE);
            text_no_registrasi.setText("-");
        }
        buttonBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bayar = new Intent(Detail_Pendaftar.this, InfoPembayaran.class);
                bayar.putExtra("UNIT", unit);
                bayar.putExtra("ID_SANTRI", uid);
                bayar.putExtra("NAMA", nama);
                bayar.putExtra("EMAIL", akun);
                startActivity(bayar);
            }
        });

        buttonJoinWag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join = new Intent(Detail_Pendaftar.this, JoinWag1.class);
                join.putExtra("Unit", unit);
                startActivity(join);
            }
        });
        buttonJoinWag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join = new Intent(Detail_Pendaftar.this, JoinWag2.class);
                join.putExtra("Unit", unit);
                startActivity(join);
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