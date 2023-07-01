package com.mascode.ppdbppitis2.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties

public class Status {

    public String keterangan;
    public String nama;

    public Status() {
    }

    public Status(String keterangan, String nama) {
        this.keterangan = keterangan;
        this.nama = nama;
    }

    @Exclude
    public Map<String, String> toMap() {
        HashMap<String, String> result = new HashMap<>();
        result.put("keterangan", keterangan);
        result.put("nama", nama);

        return result;
    }


}
