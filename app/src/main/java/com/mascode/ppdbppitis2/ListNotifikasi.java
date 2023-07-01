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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mascode.ppdbppitis2.Interface.ItemClickListener;
import com.mascode.ppdbppitis2.Model.Notification;
import com.mascode.ppdbppitis2.Model.Pendaftar;
import com.mascode.ppdbppitis2.ViewHolder.NotificationViewHolder;
import com.mascode.ppdbppitis2.ViewHolder.PendaftarViewHolder;

public class ListNotifikasi extends AppCompatActivity {

    String uid;

    //Firebase
    FirebaseDatabase database;
    DatabaseReference list_notifikasi;
    FirebaseRecyclerAdapter<Notification, NotificationViewHolder> adapter;
    RecyclerView recyclerViewNotif;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_list_notifikasi);
        Toolbar toolbar = (Toolbar)findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Mengambil data di akun google client
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null){
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            // Init Firebase Notifikasi
            database = FirebaseDatabase.getInstance();
            list_notifikasi = database.getReference("User").child(uid).child("notification");

        }
        // Init View
        recyclerViewNotif = (RecyclerView)findViewById(R.id.rv_notification);
        recyclerViewNotif.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerViewNotif.setLayoutManager(layoutManager);

        loadNotifications();
    }

    private void loadNotifications() {
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Notification>()
                        .setQuery(list_notifikasi,Notification.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Notification, NotificationViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull NotificationViewHolder holder, int position, @NonNull final Notification model) {
                holder.TextView_judul.setText(model.judul);
                holder.TextView_pesan.setText(model.pesan);
                holder.TextView_waktu.setText(model.waktu);

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        new AlertDialog.Builder(ListNotifikasi.this,R.style.CustomDialogTheme)
                                .setTitle(model.judul)
                                .setMessage(model.pesan)
                                .setPositiveButton("TUTUP", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();

                    }
                });

            }
            @NonNull
            @Override
            public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.menu_item_notification,viewGroup,false);
                return new NotificationViewHolder(view);
            }
        };
        adapter.notifyDataSetChanged();
        adapter.startListening();
        recyclerViewNotif.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
        if (paramMenuItem.getItemId() == 16908332)
            finish();
        return super.onOptionsItemSelected(paramMenuItem);
    }

}