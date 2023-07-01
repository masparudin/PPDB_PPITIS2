package com.mascode.ppdbppitis2.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties

public class Notification {

    public String judul;
    public String pesan;
    public String waktu;

    public Notification() {
    }

    public Notification(String judul, String pesan, String waktu) {
        this.judul = judul;
        this.pesan = pesan;
        this.waktu = waktu;
    }

    @Exclude
    public Map<String, String> toMap() {
        HashMap<String, String> result = new HashMap<>();
        result.put("judul", judul);
        result.put("pesan", pesan);
        result.put("waktu", waktu);

        return result;
    }


}
