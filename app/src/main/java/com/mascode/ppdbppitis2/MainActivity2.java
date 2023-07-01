package com.mascode.ppdbppitis2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mascode.ppdbppitis2.Model.Notification;
import com.mascode.ppdbppitis2.Model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {


    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;


    FirebaseDatabase database;
    DatabaseReference user;
    DatabaseReference notification;

    LinearLayout linearLayoutSignUp;
    TextView textViewInfo, textViewPrivasiPolicy;

    boolean doItOnce = true;
    boolean doubleBackToExitPressedOnce = false;
// this activity ? yeah

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(getApplicationContext(),MainMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("tipe", "1");
            startActivity(intent);
        }


    }


    // click comand + r


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);

        linearLayoutSignUp = (LinearLayout) findViewById(R.id.ly_signup_google);
        textViewInfo = (TextView)findViewById(R.id.text_info);
        textViewPrivasiPolicy = (TextView)findViewById(R.id.text_policy);
        textViewPrivasiPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent policy = new Intent(MainActivity2.this, PrivacyPolicy.class);
                startActivity(policy);
            }
        });
        mAuth = FirebaseAuth.getInstance();


        createRequest();

        linearLayoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        database = FirebaseDatabase.getInstance();
//        user = database.getReference("User");
//        user.addListenerForSingleValueEvent(null);
//        }

    private void createRequest() {


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            String UID = user.getUid();
                            checkAvaibilityUser(UID);

                        } else {
                            Toast.makeText(MainActivity2.this, "Sorry auth failed.", Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    private void checkAvaibilityUser(String UID){
        final FirebaseUser userr = mAuth.getCurrentUser();
        if (userr != null) {
            database = FirebaseDatabase.getInstance();
            user = database.getReference("User");
            //caused by this listeaner
            // and so to fix this proplem i force the app to stop listening when you leave the activity
            user.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override //click comand + f
                public void onDataChange (@NonNull DataSnapshot dataSnapshot){
                    if (dataSnapshot.hasChild(UID)){ //cek jika data user di firebase
                        Toast.makeText(getApplicationContext(), "Selamat datang kembali "+userr.getDisplayName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainMenu.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("tipe", "1");
                        startActivity(intent);
                    }else{
                        saveNewUser(userr.getEmail(), userr.getPhotoUrl().toString(), UID, userr.getDisplayName());
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                saveNotification(UID);
                            }
                        }, 5000); // Millisecond 5000 = 5 sec
                        Intent intent = new Intent(getApplicationContext(),MainMenu.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        Toast.makeText(getApplicationContext(), "Ahlan wa sahlan "+userr.getDisplayName(), Toast.LENGTH_SHORT).show();
                        intent.putExtra("tipe", "0");
                        startActivity(intent);


                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else {
                Toast.makeText(this, "Gagal mengambil data provider", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveNewUser(String email, String image, String key, String nama) {
        // String key = mDatabase.child("User").push().getKey();
        user = database.getReference();
        String status_akun = "Aktif";
        String state = "0";
        User user1 = new User(email, image, key, nama, state, status_akun);
        Map<String, String> userValues = user1.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/User/" + key, userValues);
        user.updateChildren(childUpdates);
    }

    private void saveNotification(String key){
        notification = database.getReference();
        String judul = "Pendaftaran Akun Berhasil";
        String pesan = "Selamat datang di aplikasi pendaftaran santri baru PPDB PPITIS";
        String waktu = new SimpleDateFormat("HH:mm | dd/MM/yyyy", Locale.getDefault()).format(new Date());
        Notification notification1 = new Notification(judul, pesan, waktu);
        Map<String, String> notifValues = notification1.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/User/"+key+"/notification/1",notifValues);
        notification.updateChildren(childUpdates);

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan 2x kembali untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}