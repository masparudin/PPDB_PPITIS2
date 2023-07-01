package com.mascode.ppdbppitis2.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties

public class Admin {

    public String unit;
    public String whatsapp;

    public Admin() {
    }

    public Admin(String unit, String whatsapp) {
        this.unit = unit;
        this.whatsapp = whatsapp;
    }

    @Exclude
    public Map<String, String> toMap() {
        HashMap<String, String> result = new HashMap<>();
        result.put("unit", unit);
        result.put("whatsapp", whatsapp);

        return result;
    }


}
