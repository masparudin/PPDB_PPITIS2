package com.mascode.ppdbppitis2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mascode.ppdbppitis2.Interface.ItemClickListener;
import com.mascode.ppdbppitis2.Model.FirebaseDbHelper;
import com.mascode.ppdbppitis2.Model.Pendaftar;
import com.mascode.ppdbppitis2.ViewHolder.PendaftarViewHolder;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Profileku extends AppCompatActivity {

    ImageView imageViewUser, imageViewNoData;
    TextView textViewEmail, textViewNama;

    String foto, nama, email, uid;
    //Firebase
    FirebaseDatabase database;
    DatabaseReference list_pendaftar;
    FirebaseRecyclerAdapter<Pendaftar, PendaftarViewHolder> adapter;
    RecyclerView recycler_pendaftar;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profileku);
        Toolbar toolbar = (Toolbar)findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        // Init Firebase Jenis makanan
        database = FirebaseDatabase.getInstance();
        list_pendaftar = database.getReference("Pendaftar");

        imageViewUser = (ImageView)findViewById(R.id.img_user);
        imageViewNoData = (ImageView)findViewById(R.id.img_no_data);
        textViewEmail = (TextView)findViewById(R.id.text_email);
        textViewNama = (TextView)findViewById(R.id.text_nama);

        //Mengambil data di akun google client
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null){
            foto = signInAccount.getPhotoUrl().toString();
            nama = signInAccount.getDisplayName();
            uid = signInAccount.getId();//salah uid
            email = signInAccount.getEmail();

            Picasso.with(Profileku.this).load(foto)
                    .into(imageViewUser);
            textViewEmail.setText(email);
            textViewNama.setText(nama);
        }

        // Init View
        recycler_pendaftar = (RecyclerView)findViewById(R.id.Recyclerview_pendaftar);
        recycler_pendaftar.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_pendaftar.setLayoutManager(layoutManager);

        loadPendaftar();

        FirebaseDbHelper.getChildCountOfChildOf("Pendaftar", "akun", email, new FirebaseDbHelper.OngetChildCountListeaner() {
            @Override
            public void onGetCount(Long count) {
                if (count<1){
                    imageViewNoData.setVisibility(View.VISIBLE);
                }else {
                    imageViewNoData.setVisibility(View.GONE);
                }
            }
        });

    }
    private void loadPendaftar() {
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Pendaftar>()
                        .setQuery(list_pendaftar.orderByChild("akun").equalTo(email),Pendaftar.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Pendaftar, PendaftarViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PendaftarViewHolder holder, int position, @NonNull final Pendaftar model) {
                holder.TextView_nama.setText(model.nama.toUpperCase());
                holder.TextView_unit.setText(model.unit.toUpperCase());
                holder.TextView_status.setText(model.status_bayar.toUpperCase());
                holder.TextView_id.setText("ID : "+model.id);

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // mengirim Kategori Id dan membuka activity baru
                        Intent pendaftarList = new Intent(Profileku.this, Detail_Pendaftar.class);
                        // karena CategoryId adalah key, jadi hanya perlu dapatkan key nya saja
                        pendaftarList.putExtra("Id",adapter.getRef(position).getKey());
                        pendaftarList.putExtra("Gender", model.gender);
                        pendaftarList.putExtra("Nama",model.nama);
                        pendaftarList.putExtra("Telepon",model.telepon);
                        pendaftarList.putExtra("Unit",model.unit);
                        pendaftarList.putExtra("Akun",model.akun);
                        pendaftarList.putExtra("No_Registrasi", model.nomor_registrasi);
                        pendaftarList.putExtra("Status_Bayar", model.status_bayar);
                        pendaftarList.putExtra("Status_Seleksi", model.status_seleksi);

                        pendaftarList.putExtra("Alamat", model.alamat);
                        pendaftarList.putExtra("Asal_Sekolah", model.asal_sekolah);
                        pendaftarList.putExtra("Ayah", model.ayah);
                        pendaftarList.putExtra("Ibu", model.ibu);
                        pendaftarList.putExtra("Kecamatan", model.kecamatan);
                        pendaftarList.putExtra("Kelurahan", model.kelurahan);
                        pendaftarList.putExtra("Prestasi", model.prestasi);
                        pendaftarList.putExtra("Provinsi", model.provinsi);
                        pendaftarList.putExtra("Seleksi_Kesehatan", model.seleksi_kesehatan);
                        pendaftarList.putExtra("Ttl", model.ttl);
                        pendaftarList.putExtra("Vaksinasi", model.vaksinasi);
                        pendaftarList.putExtra("Waktu", model.waktu);
                        startActivity(pendaftarList);
                    }
                });
                holder.imageView_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(Profileku.this, "Terhapus" + adapter.getRef(position), Toast.LENGTH_SHORT).show();
                        if (model.status_bayar.contentEquals("Sudah Bayar")){
                            Toast.makeText(Profileku.this, "Tidak bisa hapus, Anda bisa merubah data ketika sudah lulus", Toast.LENGTH_LONG).show();
                        }else {
                            // Build an AlertDialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(Profileku.this);
                            builder.setMessage("Hapus santri dengan ID : "+adapter.getRef(holder.getAdapterPosition()).getKey()+" ?");
                            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do something when user clicked the Yes button
                                    final ProgressDialog mDialog = new ProgressDialog(Profileku.this);
                                    mDialog.setMessage("menghapus...");
                                    mDialog.show();
                                    // proses hapus
                                    adapter.getRef(holder.getAdapterPosition()).removeValue();
                                    mDialog.dismiss();

                                }
                            });
                            // Set the alert dialog no button click listener
                            builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do something when No button clicked
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });

                // Deleting data after 2 days
                String current_time = new SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(new Date());
                String belum_bayar = "Belum Bayar";
                if(current_time.contentEquals(model.deadline)){
                    if(belum_bayar.contentEquals(model.status_bayar)){
                        adapter.getRef(holder.getAdapterPosition()).removeValue();
                    }
                }
            }
            @NonNull
            @Override
            public PendaftarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.menu_item_pendaftar,viewGroup,false);
                return new PendaftarViewHolder(view);
            }
        };
        adapter.notifyDataSetChanged();
        adapter.startListening();
        recycler_pendaftar.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent menu_utama = new Intent(Profileku.this, MainMenu.class);
        menu_utama.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        menu_utama.putExtra("tipe", "2");
        startActivity(menu_utama);
    }

}