package com.mascode.ppdbppitis2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mascode.ppdbppitis2.Interface.ItemClickListener;
import com.mascode.ppdbppitis2.Model.Pendaftar;
import com.mascode.ppdbppitis2.ViewHolder.PendaftarUjianViewHolder;

public class ListPendaftarUjian extends AppCompatActivity {

    //Firebase
    FirebaseDatabase database;
    DatabaseReference list_pendaftar;
    FirebaseRecyclerAdapter<Pendaftar, PendaftarUjianViewHolder> adapter;
    RecyclerView recycler_pendaftar;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;
    String foto, nama, email, uid;
    String linkForm, link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pendaftar_ujian);
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (getIntent() !=null){
            foto = getIntent().getStringExtra("FOTO");
            nama = getIntent().getStringExtra("NAMA");
            uid = getIntent().getStringExtra("UID");
            email = getIntent().getStringExtra("EMAIL");
        }

        // Init Firebase Jenis makanan
        database = FirebaseDatabase.getInstance();
        list_pendaftar = database.getReference("Pendaftar");

        // Init View
        recycler_pendaftar = (RecyclerView)findViewById(R.id.Recyclerview_pendaftar);
        recycler_pendaftar.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_pendaftar.setLayoutManager(layoutManager);

        loadPendaftar();
    }
    private void loadPendaftar() {
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Pendaftar>()
                        .setQuery(list_pendaftar.orderByChild("akun").equalTo(getIntent().getStringExtra("EMAIL")),Pendaftar.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Pendaftar, PendaftarUjianViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PendaftarUjianViewHolder holder, int position, @NonNull final Pendaftar model) {
                holder.TextView_nama.setText(model.nama.toUpperCase());
                holder.TextView_unit.setText(model.unit.toUpperCase());
                holder.TextView_status.setText(model.status_bayar.toUpperCase());
                holder.TextView_id.setText("ID : "+model.id.toUpperCase());
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        if (!model.status_bayar.contentEquals("Belum Bayar")){
                            if (!model.unit.contentEquals("swtqis")){

                                if (model.unit.contentEquals("smpit")){
                                    linkForm = "04";
                                }else if (model.unit.contentEquals("smait")){
                                    linkForm = "05";
                                }else if (model.unit.contentEquals("sutqis")){
                                    linkForm = "06";
                                }
                                progressDialog = new ProgressDialog(ListPendaftarUjian.this);
                                progressDialog.setMessage("Loading...");
                                progressDialog.show();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Links").child(linkForm).child("link");
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()){
                                            progressDialog.dismiss();
                                            link = dataSnapshot.getValue(String.class);
                                            // mengirim Kategori Id dan membuka activity baru
                                            Intent pendaftarList = new Intent(ListPendaftarUjian.this, WebUjian.class);
                                            // karena CategoryId adalah key, jadi hanya perlu dapatkan key nya saja
                                            pendaftarList.putExtra("STUDENT_KEY",model.id);
                                            pendaftarList.putExtra("Link",link);
                                            startActivity(pendaftarList);
                                            finish();
                                        }else {
                                            progressDialog.dismiss();
                                            Toast.makeText(ListPendaftarUjian.this, "Tidak ada link!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }else {
                                Toast.makeText(ListPendaftarUjian.this, "Tidak ada ujian tertulis online untuk SWTQIS", Toast.LENGTH_LONG).show();
                            }

                        }
                        else {
                            Toast.makeText(ListPendaftarUjian.this, "Calon santri belum melakukan pembayaran", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
            @NonNull
            @Override
            public PendaftarUjianViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_pendaftar_ujian,viewGroup,false);
                return new PendaftarUjianViewHolder(view);
            }
        };
        adapter.notifyDataSetChanged();
        adapter.startListening();
        recycler_pendaftar.setAdapter(adapter);
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