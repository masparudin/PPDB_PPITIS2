package com.mascode.ppdbppitis2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Testing extends AppCompatActivity {

    String wa_smp, wa_sma, wa_swtqis, wa_sutqis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        getKontakAdmin();

        Toast.makeText(Testing.this, "nomor admin : "+wa_smp+"\n"+wa_sma+"\n"+wa_swtqis+"\n"+wa_sutqis, Toast.LENGTH_SHORT).show();


    }
    private void getKontakAdmin() {

        // GET NOMOR WA SMP
        DatabaseReference ref_smp = FirebaseDatabase.getInstance().getReference().child("Admin").child("01").child("whatsapp");
        ref_smp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    wa_smp = dataSnapshot.getValue(String.class);
                }else {
                    Toast.makeText(Testing.this, "Tidak ada value!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Testing.this, "Tidak ada value!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Testing.this, "Tidak ada value!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Testing.this, "Tidak ada value!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }
}