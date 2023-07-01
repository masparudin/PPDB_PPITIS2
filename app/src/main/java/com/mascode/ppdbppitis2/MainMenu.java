package com.mascode.ppdbppitis2;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mascode.ppdbppitis2.Model.FirebaseDbHelper;
import com.mascode.ppdbppitis2.Model.NotificationGetSet;
import com.squareup.picasso.Picasso;
import com.tomer.fadingtextview.FadingTextView;

import java.util.Calendar;


//click contrrol f when i click on
public class MainMenu extends AppCompatActivity {
    LinearLayout linearLayoutCS, linearLayoutDaftar, linearLayoutAlur, linearLayoutProfile, linearLayoutLogout, linearLayoutNotifikasi, linearLayoutAboutus, linearLayoutUjian, linearLayoutInfo, linearLayoutPenghargaan;
    ImageView imageViewUser;
    TextView textViewWelcome, textViewInfo;
    String foto, nama, uid, email, tipe;
    ProgressDialog progressDialog;
    String pengumuman_running;

    private long backPressTime;
    private Toast backToast;

    boolean doubleBackToExitPressedOnce = false;

    int id = 0;
    String judul, pesan;

    //Activity MainMenu is quite often open by itself
    // i think the problem is in MainActivity2
// the problem is not in the mainmenu activity there is another activity that opens it
    // that there is a start activity method inside firebase in other activity
    // wait .. i will explain something
    // i think the problem is in MainActivity2 . cuz there is code that retreiving data from
   // firebase , then if a condition is true then it will startactivity MainMenu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);

        FadingTextView FTV = (FadingTextView) findViewById(R.id.fadingTextView);
        //For text change every 650 milliseconds (0.65 seconds)
        FTV.setTimeout(2000, MILLISECONDS);

        if (getIntent() == null){
            Toast.makeText(MainMenu.this, "getIntent is null", Toast.LENGTH_SHORT).show();

        } else {
            tipe = getIntent().getStringExtra("tipe");

        }

        imageViewUser = (ImageView)findViewById(R.id.img_user);
        textViewWelcome = (TextView)findViewById(R.id.text_welcome);
        linearLayoutLogout = (LinearLayout)findViewById(R.id.ly_logout);
        linearLayoutInfo = (LinearLayout)findViewById(R.id.ly_info);

        textViewInfo = (TextView) this.findViewById(R.id.text_info);
        ///
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("PENGUMUMAN");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    pengumuman_running = dataSnapshot.getValue(String.class);
                    textViewInfo.setText(pengumuman_running);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        ///

        textViewInfo.setSelected(true);
        textViewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pengumuman_running.isEmpty() || pengumuman_running == null){
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showInfo(pengumuman_running);
                        }
                    }, 1500); // Millisecond 1500 = 1.5 sec
                }
                else {
                    showInfo(pengumuman_running);

                }

            }
        });

        //Mengambil data di akun google client
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null){
            foto = signInAccount.getPhotoUrl().toString();
            nama = signInAccount.getDisplayName();
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


            email = signInAccount.getEmail();

            Picasso.with(MainMenu.this).load(foto)
                    .into(imageViewUser);

            if (uid != null){

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getNotificationData(uid);
                    }
                }, 5000); // Millisecond 5000 = 5 sec
            }else {
                Toast.makeText(MainMenu.this, "UID is NULL", Toast.LENGTH_SHORT).show();
            }
        }

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            //pagi
            textViewWelcome.setText("Selamat pagi "+nama);
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            //siang
            textViewWelcome.setText("Selamat siang "+nama);
        }else if(timeOfDay >= 16 && timeOfDay < 18){
            //sore
            textViewWelcome.setText("Selamat sore "+nama);
        }else if(timeOfDay >= 18 && timeOfDay < 24){
            //malam
            textViewWelcome.setText("Selamat malam "+nama);
        }

        linearLayoutPenghargaan = (LinearLayout)findViewById(R.id.ly_penghargaan);
        linearLayoutPenghargaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent penghargaan = new Intent(MainMenu.this, Penghargaan.class);
                startActivity(penghargaan);
            }
        });

        linearLayoutCS = (LinearLayout)findViewById(R.id.ly_help);
        linearLayoutCS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cs = new Intent(MainMenu.this, CustomerService.class);
                startActivity(cs);
            }
        });

        linearLayoutUjian = (LinearLayout)findViewById(R.id.ly_ujian);
        linearLayoutUjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Status").child("01").child("keterangan");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            String value = dataSnapshot.getValue(String.class);
                            if (value.contentEquals("Belum Dimulai")){
                                Toast.makeText(MainMenu.this, "Ujian seleksi belum dimulai", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Intent ujian = new Intent(MainMenu.this, ListPendaftarUjian.class);
                                ujian.putExtra("UID", uid);
                                ujian.putExtra("FOTO", foto);
                                ujian.putExtra("NAMA", nama);
                                ujian.putExtra("EMAIL", email);
                                startActivity(ujian);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        linearLayoutAboutus = (LinearLayout)findViewById(R.id.ly_aboutus);
        linearLayoutAboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutus = new Intent(MainMenu.this, WebTentangKami.class);
                startActivity(aboutus);
            }
        });

        linearLayoutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info = new Intent(MainMenu.this, Informasi.class);
                startActivity(info);
            }
        });

        linearLayoutNotifikasi = (LinearLayout)findViewById(R.id.ly_notifikasi);
        linearLayoutNotifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notifikasi = new Intent(MainMenu.this, ListNotifikasi.class);
                startActivity(notifikasi);
            }
        });

        linearLayoutProfile = (LinearLayout)findViewById(R.id.ly_profile);
        linearLayoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(MainMenu.this, Profileku.class);
                profile.putExtra("UID", uid);
                profile.putExtra("FOTO", foto);
                profile.putExtra("NAMA", nama);
                profile.putExtra("EMAIL", email);
                startActivity(profile);



            }
        });

        linearLayoutAlur = (LinearLayout)findViewById(R.id.ly_alur_pendaftaran);
        linearLayoutAlur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alur = new Intent(MainMenu.this, AlurPendaftaran.class);
                startActivity(alur);
            }
        });

        linearLayoutDaftar = (LinearLayout)findViewById(R.id.ly_daftar_santri);
        linearLayoutDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(MainMenu.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Status").child("03").child("keterangan");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            progressDialog.dismiss();
                            String status = dataSnapshot.getValue(String.class);
                            if (status.contentEquals("Dibuka")){
                                FirebaseDbHelper.getChildCountOfChildOf("Pendaftar", "akun", email, new FirebaseDbHelper.OngetChildCountListeaner() {
                                    @Override
                                    public void onGetCount(Long count) {
                                        if (count>=4){
                                            Toast.makeText(MainMenu.this, "Anda telah mencapai jumlah maximal pendaftaran santri", Toast.LENGTH_LONG).show();
                                        }else {
                                            Intent daftar = new Intent(MainMenu.this, PilihUnitSekolah.class);
                                            startActivity(daftar);
                                        }
                                    }
                                });

                            }else{
                                Toast.makeText(MainMenu.this, "Mohon maaf, Pendaftaran telah ditutup", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(MainMenu.this, "Tidak ada value!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        linearLayoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
                builder.setMessage("Apakah anda yakin ingin logout?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when user clicked the Yes button
                        final ProgressDialog mDialog = new ProgressDialog(MainMenu.this);
                        mDialog.setMessage("Loging out...");
                        mDialog.show();
                        // proses logout
                        FirebaseAuth.getInstance().signOut();
                        Intent relogin = new Intent(MainMenu.this, MainActivity2.class);
                        startActivity(relogin);
                        finish();
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
        });

    }


    private void getNotificationData(String uid_user) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User").child(uid_user).child("notification");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    id = (int)snapshot.getChildrenCount();
                    //Toast.makeText(MainMenu.this, "get children count : "+id, Toast.LENGTH_SHORT).show();
                    ///////////////////////////////////////////
                    reference.child(String.valueOf(id)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            NotificationGetSet notification= dataSnapshot.getValue(NotificationGetSet.class);
                            judul = notification.getJudul();
                            pesan = notification.getPesan();

                            String judulNotif = "Pendaftaran Akun Berhasil";
                            if(judul != null || pesan != null){
                                if (tipe.contentEquals("0")){ //SERING ERROR
                                    notificationTriggered();
                                }else if(tipe.contentEquals("1")) { // tipe = 1
                                    if (!judul.contentEquals(judulNotif)){
                                        notificationTriggered();
                                    }
                                }else { // tipe = 2

                                }
                            } else{
                                Toast.makeText(MainMenu.this, "judul dan pesan notifikasi null", Toast.LENGTH_SHORT).show();
                            }

                            //PERHATIKAN BLOKINGAN KODE DIATAS CARI MANA YANG DIPRIORITASKAN
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
                else {
                    Toast.makeText(MainMenu.this, "snapshot is NOT exist", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void notificationTriggered(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("notification");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notification(judul, pesan);
                    }
                }, 6000); // Millisecond 4000 = 4 sec
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void notification(String judul, String pesan) {
        long[] vibratePattern = { 0, 100, 200, 300 };
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+ getApplicationContext().getPackageName() + "/" + R.raw.notif);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            channel.setSound(soundUri, audioAttributes);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                .setSmallIcon(R.drawable.logo_ppit)
                .setAutoCancel(true)
                .setContentTitle(judul)
                .setContentText(pesan)

                .setSound(alarmSound)

                .setOnlyAlertOnce(true)
                .setVibrate(vibratePattern);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setSound(soundUri);
        }



        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());

    }
    private void showInfo(String text_information){
        new AlertDialog.Builder(MainMenu.this,R.style.CustomDialogTheme)
                .setTitle("PEMBERITAHUAN")
                .setMessage(text_information)
                .setPositiveButton("TUTUP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Tekan lagi untuk keluar", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressTime = System.currentTimeMillis();

    }
}