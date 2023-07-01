package com.mascode.ppdbppitis2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mascode.ppdbppitis2.Model.User;
import com.squareup.picasso.Picasso;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 7117;//no apapun yg diinginkan

    RelativeLayout relativeLayout;
    AnimationDrawable animationDrawable;
    LinearLayout linearLayoutSignUp;
    TextView textViewInfo;
    ImageView img_user;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference user;
    User currentUser;
    String mGroupid;

    //////////// VARIABEL DATA USER //////////
    String userUID;
    String userPhoto;
    String userName;
    String userEmail;

    String key1, image1, nama1, email1;

    boolean doItOnce = true;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );
        auth = FirebaseAuth.getInstance();


        //Firebase
        database = FirebaseDatabase.getInstance();
        user = database.getReference("User");
        mGroupid = user.push().getKey();//now go to the method again
        linearLayoutSignUp = (LinearLayout) findViewById(R.id.ly_signup_google);
        textViewInfo = (TextView)findViewById(R.id.text_info);
        checkStatus();
        linearLayoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .setLogo(R.drawable.logo_ppit)

                                .setTosAndPrivacyPolicyUrls(
                                        "https://policies.google.com/terms?hl=en-US",
                                        "https://policies.google.com/privacy?hl=en-US"
                                )
                                .setIsSmartLockEnabled(false)
                                .setTheme(R.style.MyTheme)

                                .build(),MY_REQUEST_CODE
                );
            }
        });
        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE)
        {
            //open user clas
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK)
            {
                checkAvaibilityUser();

            }else {
                getDetailUserFirebase();
                if (response == null){
                    //sign failed
                    // user pressed back button
                    //Toast.makeText(mContext, "Login gagal", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }

    private void checkStatus(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Status").child("02").child("keterangan");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String value = dataSnapshot.getValue(String.class);
                    if (value.contentEquals("Dikunci")){
                        linearLayoutSignUp.setEnabled(false);
                        textViewInfo.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Pendaftaran santri belum dimulai", Toast.LENGTH_LONG).show();
                    }else if (value.contentEquals("Under Maintenance")){
                        Toast.makeText(MainActivity.this, "System is under maintenance!", Toast.LENGTH_LONG).show();
                        linearLayoutSignUp.setEnabled(false);
                    }else {
                        textViewInfo.setVisibility(View.GONE);
                        linearLayoutSignUp.setEnabled(true);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkAvaibilityUser(){
        final FirebaseUser userr = auth.getCurrentUser();
        if (userr != null) {
            if (userr.getUid() != null) {
                String pengguna = userr.getUid();

            } else {
                showMessageRed("Gagal mengambil data provider");
            }
        }

        database = FirebaseDatabase.getInstance();
        user = database.getReference("User");
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot){
                if (dataSnapshot.hasChild(userr.getUid())){ //cek jika data user di firebase

                    FirebaseUser user = auth.getCurrentUser();
                    getDetailUserFirebase();
//                    return;

                }else{
                    //Toast.makeText(getActivity(), "User tidak terdaftar di database ", Toast.LENGTH_SHORT).show();
                    getDetailUserProvider();
                    //saveDataUser(); this was my code that im using for save user but no longer i use it, because im using the method writeNewUser();

                    if(!TextUtils.isEmpty(userName)) {
                        if(doItOnce) {
                            doItOnce = false;//now it will work
                            saveNewUser(userEmail, userPhoto, userName);
                        }// this for the test we will change it later
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getDetailUserProvider(){ // this is where i get detail of user from provider which is  google
        final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setMessage("Memuat data user...");
        mDialog.show();
        FirebaseUser user = auth.getCurrentUser();
        // Show user info
        if (user != null) {
            mDialog.dismiss();
            // go to the method again
            if (user.getEmail() != null){
                userEmail = user.getEmail();

            }else {
                userEmail = "";
            }
            if (user.getUid() != null){
                userUID = user.getUid();

            }else {
                showMessageRed("Gagal mengambil data provider");
            }
            userName = user.getDisplayName();
            if (userName != null)
            {
                showMessage("Selamat "+userName+"\n"+"telah terdaftar");
            }else if(userName == null){
                userName = "";

            }
            else{
                Toast.makeText(MainActivity.this, "Error tidak diketahui", Toast.LENGTH_SHORT).show();
            }


            if(user.getPhotoUrl() != null) {
                Uri photoUrl = user.getPhotoUrl();
                userPhoto = photoUrl.toString();

            }else{
                userPhoto = "";

            }

        }

    }

    private void getDetailUserFirebase() {
        database = FirebaseDatabase.getInstance();

        user = database.getReference("User");
        user.child("idUser").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                //atur Image

                userPhoto = currentUser.image;
                userEmail = currentUser.email;

                if (!TextUtils.isEmpty(currentUser.nama)){ //this is a wrong comparation here you are compary a string objects not the actual text you can use content equals
                    //or textutil do you get it ?how ? this is the first way , but for checking if its empty you should use text util
                    //do you get it now? yeah
                    userName = currentUser.nama;

                }else {
                    userName = "Belum diatur";

                }


                Intent masuk = new Intent(MainActivity.this, MainMenu.class);
                masuk.putExtra("UIDUser", currentUser.key);
                masuk.putExtra("FOTO", userPhoto);
                masuk.putExtra("NAMA", userName);
                masuk.putExtra("EMAIL", userEmail);
                masuk.putExtra("LOGIN_TYPE", "100");
                startActivity(masuk);
                finish();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveNewUser(String email, String image, String nama) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        // String key = mDatabase.child("User").push().getKey();
        user = database.getReference();// now the problem will be fixed
        String status_akun = "Aktif";
        String state = "0";
        User user1 = new User(email, image, userUID, nama, state, status_akun);
        Map<String, String> userValues = user1.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/User/" + userUID, userValues); //this the code
        user.updateChildren(childUpdates);
    }

    public void showMessageRed(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.toast_layout_red, (ViewGroup)findViewById(R.id.toast_root));
        ((TextView) toastLayout.findViewById(R.id.toast_text)).setText(message);
        Toast toast = new Toast(MainActivity.this);
        toast.setGravity(Gravity.CENTER,0,0  );
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.show();
    }
    public void showMessage(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.toast_layout, (ViewGroup)findViewById(R.id.toast_root));
        ((TextView) toastLayout.findViewById(R.id.toast_text)).setText(message);
        Toast toast = new Toast(MainActivity.this);
        toast.setGravity(Gravity.CENTER,0,0  );
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.show();
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