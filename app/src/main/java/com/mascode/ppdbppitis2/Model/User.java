package com.mascode.ppdbppitis2.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties

public class User {

    public String email;
    public String image;
    public String key;
    public String nama;
    public String state;
    public String status_akun;

    public User() {
    }

    public User(String email, String image, String key, String nama, String state, String status_akun) {
        this.email = email;
        this.image = image;
        this.key = key;
        this.nama = nama;
        this.state = state;
        this.status_akun = status_akun;
    }

    @Exclude
    public Map<String, String> toMap() {
        HashMap<String, String> result = new HashMap<>();
        result.put("email", email);
        result.put("image", image);
        result.put("key", key);
        result.put("nama", nama);
        result.put("state", state);
        result.put("status_akun", status_akun);

        return result;
    }


}
