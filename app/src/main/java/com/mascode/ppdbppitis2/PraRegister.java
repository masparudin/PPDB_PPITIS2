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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mascode.ppdbppitis2.Model.Pendaftar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PraRegister extends AppCompatActivity {

    Button buttonNext;
    FirebaseDatabase database;
    EditText editTextName,editTextTelepon;
    String gender;
    ImageView imgUnit;
    String email, input_nama, input_telepon, input_unit, studentKey;
    boolean isSuccess;
    String kodeUnitJK;
    DatabaseReference pendaftar;
    DatabaseReference pendaftar1;
    TextView textViewUnit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pra_register);
        Toolbar toolbar = (Toolbar)findViewById(R.id.mytoolbar);

        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Mengambil data di akun google client
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            email = signInAccount.getEmail();
        }

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        database = firebaseDatabase;
        pendaftar = firebaseDatabase.getReference("Pendaftar");
        editTextName = (EditText)findViewById(R.id.input_name);
        editTextTelepon = (EditText)findViewById(R.id.input_nomor);
        imgUnit = (ImageView)findViewById(R.id.img_unit);
        textViewUnit = (TextView)findViewById(R.id.text_unit);
        buttonNext = (Button)findViewById(R.id.button_next);
        if (getIntent() != null) {
            input_unit = getIntent().getStringExtra("UNIT");
            gender = getIntent().getStringExtra("GENDER");
        } else {
            Toast.makeText(this, "Data is null", Toast.LENGTH_SHORT).show();
        }
        if (input_unit.contentEquals("smait")) {
            imgUnit.setImageResource(R.drawable.smait);
            textViewUnit.setText("SMA IT");
        } else if (input_unit.contentEquals("smpit")) {
            imgUnit.setImageResource(R.drawable.smpit);
            textViewUnit.setText("SMP IT");
        } else if (input_unit.contentEquals("swtqis")) {
            imgUnit.setImageResource(R.drawable.swtqis);
            textViewUnit.setText("SWTQIS");
        } else if (input_unit.contentEquals("sutqis")) {
            imgUnit.setImageResource(R.drawable.sutqis);
            textViewUnit.setText("SUTQIS");
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

        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!checkDataEntered())
                    return;
                input_nama = editTextName.getText().toString().trim();
                input_telepon = editTextTelepon.getText().toString().trim();
                String input_nomor_registrasi = "null";
                String input_status_bayar = "Belum Bayar";
                String input_status_seleksi = "null";
                String input_seleksi_kesehatan = "null";
                String init_ujkb = kodeUnitJK+"_"+input_status_bayar;
                String currentTime = new SimpleDateFormat("ddHHmmssSSS", Locale.getDefault()).format(new Date());
                String waktu = new SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(new Date());
                studentKey = kodeUnitJK+currentTime;

//                saveNewPendaftar(email, gender, studentKey, init_ujkb, input_nama, input_nomor_registrasi, input_seleksi_kesehatan, input_status_bayar, input_status_seleksi, input_telepon, input_unit, waktu);
//                isSuccess = true;
                Intent intent = new Intent(PraRegister.this, InfoPembayaran.class);
                intent.putExtra("ID_SANTRI", studentKey);
                intent.putExtra("NAMA", input_nama);
                intent.putExtra("UNIT", input_unit);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
            }
        });
    }



//    private void saveNewPendaftar(String akun, String gender, String id, String init_ujkb, String nama, String no_registrasi, String seleksi_kesehatan, String status_bayar, String status_seleksi, String telepon, String unit, String waktu) {
//
//        pendaftar1 = database.getReference();
//        Pendaftar pendaftar = new Pendaftar(akun, gender, id, init_ujkb, nama, no_registrasi, seleksi_kesehatan, status_bayar, status_seleksi, telepon, unit, waktu);
//        Map<String, String> userValues = pendaftar.toMap();
//
//        final Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/Pendaftar/" + id, userValues);
//        pendaftar1.updateChildren(childUpdates);
//        isSuccess = true;
//    }


    public boolean checkDataEntered() {
        boolean bool = true;
        if (isEmpty(editTextName)) {
            editTextName.setError("Nama harus diisi!");
            bool = false;
        } else {
            editTextName.setError(null);
        }
        if (isEmpty(editTextTelepon)) {
            editTextTelepon.setError("Telepon harus diisi!");
            return false;
        }
        editTextTelepon.setError(null);
        return bool;
    }

    boolean isEmpty(EditText paramEditText) {
        return TextUtils.isEmpty(paramEditText.getText().toString());
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