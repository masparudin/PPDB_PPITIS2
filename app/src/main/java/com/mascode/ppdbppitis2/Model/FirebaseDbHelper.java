package com.mascode.ppdbppitis2.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDbHelper {



   public interface OngetChildCountListeaner {
        void onGetCount(Long count);
    }


    public static void getChildCountOf(String root, final OngetChildCountListeaner ongetChildCountListeaner){

        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child(root);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               Long  num = dataSnapshot.getChildrenCount();
               ongetChildCountListeaner.onGetCount(num);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void getChildCountOfChildOf(String root, String obc, String eqt, final OngetChildCountListeaner ongetChildCountListeaner){
       DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child(root);
        Query query = userReference.orderByChild(obc).equalTo(eqt);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long  num = dataSnapshot.getChildrenCount();
                ongetChildCountListeaner.onGetCount(num);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
