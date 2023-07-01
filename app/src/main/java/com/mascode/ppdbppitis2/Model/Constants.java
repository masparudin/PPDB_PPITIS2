package com.mascode.ppdbppitis2.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.FirebaseDatabase;

public  final class Constants {

    public static final String UIDUser = "uidKey";
    public static final String PREFS_NAME = "Hagin_Login";//and this its name we use PREFS_NAME to hold the file name thats all
    public static final String USER_ROOT = "User";
    public static final String USER_STATE_ROOT = "state";
    public static final String LOGGED_IN = "1";
    public static final String LOGGED_OUT = "0";
    public static final String ID_ROOT = "CURRENT_ID";// its the value not the variable it self
    public static final String StarterId = "00001";// type the 5 digit that we aill start counting from them like 201512


    //open the registeratiopn activity again

 // we will create a field in firebase will called current key it will save the last key we had reached after our last count
    // and when we open the app we will get it from fire base and save it with setSavedKey method
    // and when we need to use it inside the app we will use the method

    // okay i will explain
    public static final  void setSavedKey(Context context,String id){
        //this method is called save key it will save the key in sharred pref
        // it first open shared prefs then
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
        //then put the string
        editor.putString(ID_ROOT,id); //and then when we type this line that happens sorry i meant this line
        //then add the changes to shared prefs file
        editor.apply(); // and when we click apply this happens
    }
//we will check for the id before creating the new one and if we found that it is like the one on firebase we will change it do you get it ?yeah, that's what iam talking about,
    //okay lets start doing it
        // get savedid this method
    public static final  String getSavedKey(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        //and when we type this line it searchs for the file and open it and if it didnt find the specified file
        //it creats a new file
        return sharedPreferences.getString(ID_ROOT,"111111");//and here when we try to get it it searches for it inside the file
        //and if it didnt find it it will use the default value did you get it ?yeah i get it, then the default value will be 111111 right ? yeah right if it didnt find any value inside the file and this will happen of course maybe in our first use of it
    }
        // and we will use this when we neeed to generate anew key and when we generate it we will update it in firebase
    public static final String generateNewKey(Context context){
        int mId = Integer.parseInt(Constants.getSavedKey(context));
        mId = 5+ mId;//this for if 5 students registering in the same time to protect from creating a dubplicated id do you get it ?how if there are 10 students or more than that we thought
        //thats the right question i was waiting for you to0 ask this question we will do a better testing
        return Integer.toString(mId);
    }
//now we will make a new method to save the key to firebase
    public static void updateKeyInFirebase(String key)
    {FirebaseDatabase.getInstance().getReference(ID_ROOT).setValue(key);//so now in firebase it will be saved in CURRENT_ID did you get it ?this part i dont really get what you mean,
        // how if  i wanna save a new datas without using grupid of firebase ? so can i use this method ? without using what ? wait i will show you
    }

    public static void updateKey(Context context){
        String newKey = Constants.generateNewKey(context);
        Constants.updateKeyInFirebase(newKey);
        Constants.setSavedKey(context,newKey);

    }

    /* methods here is for saving userId
        the first one is for saving uid setsavedUid and get sSavedUid for getting it and set state you already know it
        do you get it ? wait a sec.. okay i got it
     */

    public static final  void setSavedUid(Context context,String uid){
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).edit();
        editor.putString(UIDUser,uid);// okay so what is the purpose of this line of code ? saving uid okay and whats the id name inside the shared prefs ? UIDUser, am i right ? nopr wrong


        editor.apply();
    }
// should i explain shared prefs and how they work now or fix the problems first ? i already known about sharedpref, sharedpref is use for saving data on local disk on phone

    public static final  String getSavedUid(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(UIDUser,"-1");
    }



    public static final void setState(Context context,String state){
        FirebaseDatabase.getInstance().getReference().child(USER_ROOT).child(getSavedUid(context)).child(USER_STATE_ROOT).setValue(state);
    }


}
