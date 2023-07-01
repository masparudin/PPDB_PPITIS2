package com.mascode.ppdbppitis2.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties

public class Pengumuman {

    public String judul;
    public String pesan;
    public String status;
    public String tujuan;
    public String waktu;

    public Pengumuman() {
    }

    public Pengumuman(String judul, String pesan, String status, String tujuan, String waktu) {
        this.judul = judul;
        this.pesan = pesan;
        this.status = status;
        this.tujuan = tujuan;
        this.waktu = waktu;
    }

    @Exclude
    public Map<String, String> toMap() {
        HashMap<String, String> result = new HashMap<>();
        result.put("judul", judul);
        result.put("pesan", pesan);
        result.put("status", status);
        result.put("tujuan", tujuan);
        result.put("waktu", waktu);

        return result;
    }


}
