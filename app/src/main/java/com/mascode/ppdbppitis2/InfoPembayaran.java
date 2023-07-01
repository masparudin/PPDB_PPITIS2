package com.mascode.ppdbppitis2;

import androidx.annotation.NonNull;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoPembayaran extends AppCompatActivity {
    Button buttonChatWA;
    String email,nama,no_rek,nominal,penerima;
    TextView textViewEmail,textViewNamaPenerima,textViewNamaSantri,textViewNoRek,textViewNominal,textViewUID,textViewUnit, textBeritaTF;
    String uid,unit;
    ImageView Img_norek, Img_nominal, Img_berita_tf;
    String wa_smp, wa_sma, wa_swtqis, wa_sutqis;

    // fixed
    private ClipboardManager myClipboard;
    private ClipData myClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_info_pembayaran);

        getWaAdmin();

        Toolbar toolbar = (Toolbar)findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        textBeritaTF = (TextView)findViewById(R.id.text_berita_transfer);

        Intent intent = getIntent();
        if (intent != null) {
            unit = intent.getStringExtra("UNIT");
            uid = intent.getStringExtra("ID_SANTRI");
            nama = intent.getStringExtra("NAMA");
            email = intent.getStringExtra("EMAIL");

            textBeritaTF.setText("REG "+uid+" "+nama);
        } else {

            Toast.makeText(this, "Kesalahan menerima data", Toast.LENGTH_SHORT).show();
        }


        if (unit.contentEquals("smpit")) {
            no_rek = "1033515339";
            nominal = "349"; //350.000
            penerima = "SMPIT Imam Syafi'i";
        } else if (unit.contentEquals("smait")) {
            no_rek = "1033515398";
            nominal = "349"; //350.000
            penerima = "SMAIT Imam Syafi'i";
        } else if (unit.contentEquals("swtqis")) {
            no_rek = "1033515088";
            nominal = "349"; //350.000
            penerima = "DARUL QURRO BATU AJI";
        } else if (unit.contentEquals("sutqis")) {
            no_rek = "1033515088";
            nominal = "349"; //350.000
            penerima = "DARUL QURRO BATU AJI";
        } else {
            no_rek = "silahkan tanya no rek kepada admin";
        }

        textViewNoRek = (TextView)findViewById(R.id.text_no_rek);
        textViewNoRek.setText(no_rek);
        textViewEmail = (TextView)findViewById(R.id.text_email);
        textViewEmail.setText(email);
        textViewNamaSantri = (TextView)findViewById(R.id.text_nama);
        textViewNamaSantri.setText(nama);
        textViewUID = (TextView)findViewById(R.id.text_id);
        textViewUID.setText(uid);
        textViewUnit = (TextView)findViewById(R.id.text_unit);
        textViewUnit.setText(unit);
        textViewNamaPenerima = (TextView)findViewById(R.id.text_penerima);
        textViewNamaPenerima.setText(penerima);

        String lastThreeDigits = "";     //substring containing last 3 characters
        if (uid.length() > 3)
        { lastThreeDigits = uid.substring(uid.length() - 3);}
        else { lastThreeDigits = uid; }
        textViewNominal = (TextView)findViewById(R.id.text_nominal);
        textViewNominal.setText(nominal+lastThreeDigits);

        Button buttonChatWA = (Button)findViewById(R.id.chatwabtn);
        buttonChatWA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wa_smp == null || wa_sma == null || wa_swtqis == null || wa_sutqis == null){
                    Toast.makeText(InfoPembayaran.this, "Tunggu 5 detik, lalu coba lagi", Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Status").child("04").child("keterangan");
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String status = dataSnapshot.getValue(String.class);
                                if (status.contentEquals("Dibuka")) {
                                    // Build an AlertDialog
                                    AlertDialog.Builder builder = new AlertDialog.Builder(InfoPembayaran.this);
                                    builder.setMessage("Apakah anda sudah melakukan transfer?");
                                    builder.setPositiveButton("Sudah", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Do something when user clicked the Yes button
                                            Intent intent = new Intent(InfoPembayaran.this, KirimBuktiTf.class);
                                            intent.putExtra("Unit", unit);
                                            intent.putExtra("Id", uid);
                                            intent.putExtra("Nama", nama);
                                            intent.putExtra("Email", email);
                                            intent.putExtra("WA_SMP", wa_smp);
                                            intent.putExtra("WA_SMA", wa_sma);
                                            intent.putExtra("WA_SWTQIS", wa_swtqis);
                                            intent.putExtra("WA_SUTQIS", wa_sutqis);

                                            startActivity(intent);
                                        }
                                    });
                                    // Set the alert dialog no button click listener
                                    builder.setNegativeButton("Belum", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Do something when No button clicked
                                            dialog.dismiss();
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                } else {
                                    Toast.makeText(InfoPembayaran.this, "Maaf, Konfirmasi pembayaran sedang ditutup", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(InfoPembayaran.this, "Tidak ada value!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

        Img_norek = (ImageView)findViewById(R.id.copy_norek);
        Img_norek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                myClip = ClipData.newPlainText("text", textViewNoRek.getText().toString());
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getApplicationContext(), "Nomor rekening disalin",Toast.LENGTH_SHORT).show();
            }
        });
        Img_nominal = (ImageView)findViewById(R.id.copy_nominal);
        Img_nominal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                myClip = ClipData.newPlainText("text", textViewNominal.getText().toString());
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getApplicationContext(), "Nominal transfer disalin",Toast.LENGTH_SHORT).show();
            }
        });
        Img_berita_tf = (ImageView)findViewById(R.id.copy_berita_tf);
        Img_berita_tf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                myClip = ClipData.newPlainText("text", textBeritaTF.getText().toString());
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getApplicationContext(), "Berita transfer disalin",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getWaAdmin() {

        // GET NOMOR WA SMP
        DatabaseReference ref_smp = FirebaseDatabase.getInstance().getReference().child("Admin").child("01").child("whatsapp");
        ref_smp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    wa_smp = dataSnapshot.getValue(String.class);
                }else {
                    Toast.makeText(InfoPembayaran.this, "Tidak ada value!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        // GET NOMOR WA SMA
        DatabaseReference ref_sma = FirebaseDatabase.getInstance().getReference().child("Admin").child("02").child("whatsapp");
        ref_sma.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    wa_sma = dataSnapshot.getValue(String.class);
                }else {
                    Toast.makeText(InfoPembayaran.this, "Tidak ada value!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        // GET NOMOR WA SWTQIS
        DatabaseReference ref_sw = FirebaseDatabase.getInstance().getReference().child("Admin").child("03").child("whatsapp");
        ref_sw.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    wa_swtqis = dataSnapshot.getValue(String.class);
                }else {
                    Toast.makeText(InfoPembayaran.this, "Tidak ada value!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        // GET NOMOR WA SUTQIS
        DatabaseReference ref_su = FirebaseDatabase.getInstance().getReference().child("Admin").child("04").child("whatsapp");
        ref_su.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    wa_sutqis = dataSnapshot.getValue(String.class);
                }else {
                    Toast.makeText(InfoPembayaran.this, "Tidak ada value!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
            // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
