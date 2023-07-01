package com.mascode.ppdbppitis2.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties

public class Links {

    public String link;
    public String nama;

    public Links() {
    }

    public Links(String link, String nama) {
        this.link = link;
        this.nama = nama;
    }

    @Exclude
    public Map<String, String> toMap() {
        HashMap<String, String> result = new HashMap<>();
        result.put("link", link);
        result.put("nama", nama);

        return result;
    }


}
