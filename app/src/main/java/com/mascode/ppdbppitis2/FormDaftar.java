package com.mascode.ppdbppitis2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mascode.ppdbppitis2.Model.Pendaftar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FormDaftar extends AppCompatActivity {
    TextView textViewUnit, textViewTL;
    ImageView imgUnit;
    String kodeUnitJK;
    String email, gender, input_unit, studentKey;

    Button buttonSend;

    //input form register
    EditText textNama, textTempatLahir, textPrestasi, textAsalSekolah, textAyah, textIbu, textTelepon, textAlamat, textKelurahan, textKecamatan;
    TextView textViewTanggalLahir;
    private Spinner spVaksin, spProvinsi, spUnit;
    CheckBox checkBox1,checkBox2;

    // String input form register
    String inputNama, inputTempatLahir, inputTanggalLahir, inputAsalSekolah, inputPrestasi, inputAyah, inputIbu, inputTelepon, inputAlamat, inputKelurahan, inputKecamatan;
    String inputProvinsi, inputVaksin;


    FirebaseDatabase database;
    DatabaseReference pendaftar;
    DatabaseReference pendaftar1;
    boolean isSuccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_form_daftar);

        Toolbar toolbar = (Toolbar)findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Kode dibawah perlu dipindahkan ke bawah
        textNama = (EditText) findViewById(R.id.txtNama);
        textTempatLahir = (EditText) findViewById(R.id.txtTempatLahir);
        textAsalSekolah = (EditText) findViewById(R.id.txtAsalSekolah);
        textPrestasi = (EditText) findViewById(R.id.txtPrestasi);
        textAyah = (EditText) findViewById(R.id.txtAyah);
        textIbu = (EditText) findViewById(R.id.txtIbu);
        textTelepon = (EditText) findViewById(R.id.txtTelepon);
        textAlamat = (EditText) findViewById(R.id.txtAlamat);
        textKelurahan = (EditText) findViewById(R.id.txtKelurahan);
        textKecamatan = (EditText) findViewById(R.id.txtKecamatan);
        textViewTanggalLahir = (TextView) findViewById(R.id.txtInputTanggalLahir);

        //spUnit = (Spinner) findViewById(R.id.sp_unit_sekolah);
        spVaksin = (Spinner) findViewById(R.id.sp_vaksin);
        spProvinsi = (Spinner) findViewById(R.id.sp_provinsi);
        textViewTL = (TextView) findViewById(R.id.txtInputTanggalLahir);
        textViewTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TampilTanggal();
            }
        });

        checkBox1 = (CheckBox)findViewById(R.id.cbP1);
        checkBox2 = (CheckBox)findViewById(R.id.cbP2);

        //Mengambil data di akun google client
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            email = signInAccount.getEmail();
        }

        //Firebase
        database = FirebaseDatabase.getInstance();
        pendaftar = database.getReference("Pendaftar");
        imgUnit = (ImageView)findViewById(R.id.img_unit);
        textViewUnit = (TextView)findViewById(R.id.text_unit);
        buttonSend = (Button)findViewById(R.id.btnSend);

        if (getIntent() != null) {
            input_unit = getIntent().getStringExtra("UNIT");
            gender = getIntent().getStringExtra("GENDER");
        } else {
            Toast.makeText(this, "Data is null", Toast.LENGTH_SHORT).show();
        }
        if (input_unit.contentEquals("smait")) {
            imgUnit.setImageResource(R.drawable.smait);
            if (gender.contentEquals("Ikhwan")){
                textViewUnit.setText("FORMULIR SMAS IT IKHWAN");
            }else {
                textViewUnit.setText("FORMULIR SMAS IT AKHWAT");
            }
        } else if (input_unit.contentEquals("smpit")) {
            imgUnit.setImageResource(R.drawable.smpit);
            if (gender.contentEquals("Ikhwan")){
                textViewUnit.setText("FORMULIR SMPS IT IKHWAN");
            }else {
                textViewUnit.setText("FORMULIR SMPS IT AKHWAT");
            }
        } else if (input_unit.contentEquals("swtqis")) {
            imgUnit.setImageResource(R.drawable.swtqis);
            if (gender.contentEquals("Ikhwan")){
                textViewUnit.setText("FORMULIR SWTQ IKHWAN");
            }else {
                textViewUnit.setText("FORMULIR SWTQ AKHWAT");
            }
        } else if (input_unit.contentEquals("sutqis")) {
            imgUnit.setImageResource(R.drawable.sutqis);
            if (gender.contentEquals("Ikhwan")){
                textViewUnit.setText("FORMULIR SUTQ IKHWAN");
            }else {
                textViewUnit.setText("FORMULIR SUTQ AKHWAT");
            }
        }

        isSuccess = false;
        if (input_unit.contentEquals("smpit") && gender.contentEquals("Ikhwan")) {
            kodeUnitJK = "1";
        } else if (input_unit.contentEquals("smpit") && gender.contentEquals("Akhwat")) {
            kodeUnitJK = "2";
        } else if (input_unit.contentEquals("smait") && gender.contentEquals("Ikhwan")) {
            kodeUnitJK = "3";
        } else if (input_unit.contentEquals("smait") && gender.contentEquals("Akhwat")) {
            kodeUnitJK = "4";
        } else if (input_unit.contentEquals("swtqis") && gender.contentEquals("Ikhwan")) {
            kodeUnitJK = "5";
        } else if (input_unit.contentEquals("swtqis") && gender.contentEquals("Akhwat")) {
            kodeUnitJK = "6";
        } else if (input_unit.contentEquals("sutqis") && gender.contentEquals("Ikhwan")) {
            kodeUnitJK = "7";
        } else if (input_unit.contentEquals("sutqis") && gender.contentEquals("Akhwat")) {
            kodeUnitJK = "8";
        } else {
            Toast.makeText(this, "Salah kode unit jk", Toast.LENGTH_SHORT).show();
        }

        if (input_unit.contentEquals("smpit")&&gender.contentEquals("Ikhwan")){
            kodeUnitJK = "1";
        } else if (input_unit.contentEquals("smpit") && gender.contentEquals("Akhwat")) {
            kodeUnitJK = "2";
        }
        else if (input_unit.contentEquals("smait")&&gender.contentEquals("Ikhwan")) {
            kodeUnitJK = "3";
        }  else if (input_unit.contentEquals("smait") && gender.contentEquals("Akhwat")) {
            kodeUnitJK = "4";
        }
        else if (input_unit.contentEquals("swtqis")&&gender.contentEquals("Ikhwan")){
            kodeUnitJK = "5";
        } else if (input_unit.contentEquals("swtqis") && gender.contentEquals("Akhwat")) {
            kodeUnitJK = "6";
        }
        else if (input_unit.contentEquals("sutqis")&&gender.contentEquals("Ikhwan")) {
            kodeUnitJK = "7";
        }  else if (input_unit.contentEquals("sutqis") && gender.contentEquals("Akhwat")) {
            kodeUnitJK = "8";
        }else
        {
            Toast.makeText(this, "Salah kode unit jk", Toast.LENGTH_SHORT).show();
        }

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkDataEntered()){
                    return;
                }
                inputAsalSekolah = textAsalSekolah.getText().toString().trim();
                inputVaksin = spVaksin.getSelectedItem().toString();
                inputProvinsi = spProvinsi.getSelectedItem().toString();
                inputNama = textNama.getText().toString().trim();
                inputTempatLahir = textTempatLahir.getText().toString().trim();
                inputTanggalLahir = textViewTanggalLahir.getText().toString().trim();
                //inputAsalSekolah = textAsalSekolah.getText().toString().trim();
                inputPrestasi = textPrestasi.getText().toString().trim();
                inputAyah = textAyah.getText().toString().trim();
                inputIbu = textIbu.getText().toString().trim();
                inputTelepon = textTelepon.getText().toString().trim();
                inputAlamat = textAlamat.getText().toString().trim();
                inputKelurahan = textKelurahan.getText().toString().trim();
                inputKecamatan = textKecamatan.getText().toString().trim();

                String ttl = inputTempatLahir+", "+inputTanggalLahir;
                String inputNomorRegistrasi = "null";
                String inputStatusBayar = "Belum Bayar";
                String inputStatusSeleksi = "null";
                String inputSeleksiKesehatan = "null";
                String init_ujkb = kodeUnitJK+"_"+inputStatusBayar;
                String currentTime = new SimpleDateFormat("ddHHmmssSSS", Locale.getDefault()).format(new Date() );
                String waktu = new SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(new Date());

                // get date 7 days after today
                String input_deadline = getCalculatedDate("ddMMyyyy", 7);

                studentKey = kodeUnitJK+currentTime;

                saveNewPendaftar(email,inputAlamat,inputAsalSekolah,inputAyah, input_deadline,gender,inputIbu,studentKey,init_ujkb,inputKecamatan,inputKelurahan,inputNama,inputNomorRegistrasi,inputPrestasi,inputProvinsi,inputSeleksiKesehatan,inputStatusBayar,inputStatusSeleksi,inputTelepon,ttl,input_unit,inputVaksin,waktu);
                Intent home = new Intent(FormDaftar.this, Profileku.class);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);


            }
        });

    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    public boolean checkDataEntered(){
        boolean result = true;
        //todo: valid form here
        if (isEmpty(textNama)) {
            result = false;
            textNama.setError("Nama harus diisi!");
        }else{
            textNama.setError(null);
        }
        if (isEmpty(textTempatLahir)) {
            result = false;
            textTempatLahir.setError("Tempat Lahir harus diisi!");
        }else {
            textTempatLahir.setError(null);
        }

        String tanggal_lahir = textViewTanggalLahir.getText().toString();
        if (tanggal_lahir.contentEquals("Pilih Tanggal Lahir")) {
            result = false;
            textViewTanggalLahir.setError("Tanggal Lahir harus diisi!");
        }else {
            textViewTanggalLahir.setError(null);
        }
//        if (isEmpty(textAsalSekolah)) {
//            result = false;
//            textAsalSekolah.setError("Asal Sekolah harus diisi!");
//        }else {
//            textAsalSekolah.setError(null);
//        }
        if (isEmpty(textPrestasi)) {
            result = false;
            textPrestasi.setError("Prestasi isi tidak ada");
        }else {
            textPrestasi.setError(null);
        }
        if (isEmpty(textAyah)) {
            result = false;
            textAyah.setError("Nama ayah harus diisi!");
        }else {
            textAyah.setError(null);
        }
        if (isEmpty(textIbu)) {
            result = false;
            textIbu.setError("Nama ibu harus diisi!");
        }else {
            textIbu.setError(null);
        }
        if (isEmpty(textTelepon)) {
            result = false;
            textTelepon.setError("Telepon harus diisi!");
        }else {
            textTelepon.setError(null);
        }

        if (isEmpty(textAlamat)) {
            result = false;
            textAlamat.setError("Alamat harus diisi!");
        }else {
            textAlamat.setError(null);
        }

        if (isEmpty(textKelurahan)) {
            result = false;
            textKelurahan.setError("Kelurahan harus diisi!");
        }else {
            textKelurahan.setError(null);
        }
        if (isEmpty(textKecamatan)) {
            result = false;
            textKecamatan.setError("Kecamatan harus diisi!");
        }else {
            textKecamatan.setError(null);
        }

        if (!checkBox1.isChecked()){
            result = false;
            Toast.makeText(FormDaftar.this, "Anda harus mencentang pernyataan keaslian informasi pendaftar", Toast.LENGTH_SHORT).show();
        }else {
            checkBox1.setError(null);
        }

        if (!checkBox2.isChecked()){
            result = false;
            Toast.makeText(FormDaftar.this, "Anda harus mencentang pernyataan kesediaan mengikuti kegiatan parenting", Toast.LENGTH_SHORT).show();
        }else {
            checkBox2.setError(null);
        }

        return result;
    }

    private void saveNewPendaftar(String akun, String alamat, String asal_sekolah, String ayah, String deadline, String gender,String ibu, String id, String init_ujkb, String kecamatan, String kelurahan, String nama, String nomor_registrasi, String prestasi, String provinsi, String seleksi_kesehatan, String status_bayar, String status_seleksi, String telepon, String ttl, String unit, String vaksinasi, String waktu)
    {
        pendaftar1 = database.getReference();
        Pendaftar pendaftar = new Pendaftar(akun,alamat,asal_sekolah,ayah, deadline, gender,ibu,id,init_ujkb,kecamatan,kelurahan,nama,nomor_registrasi,prestasi,provinsi,seleksi_kesehatan,status_bayar,status_seleksi,telepon,ttl,unit,vaksinasi,waktu);
        Map<String, String> userValues = pendaftar.toMap();
        final Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Pendaftar/" + id, userValues);
        pendaftar1.updateChildren(childUpdates);
        Toast.makeText(getApplicationContext(), "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show();
    }
    public void TampilTanggal(){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "data");
        datePickerFragment.setOnDateClickListener(new DatePickerFragment.onDateClickListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String tahun = ""+datePicker.getYear();
                String bulan = ""+(datePicker.getMonth()+1);
                String hari = ""+datePicker.getDayOfMonth();
                String text = hari+"-"+bulan+"-"+tahun;
                textViewTL.setText(text);
            }
        });
    }

    public static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}