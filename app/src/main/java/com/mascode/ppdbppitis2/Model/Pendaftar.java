package com.mascode.ppdbppitis2.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties


public class Pendaftar {

    public String akun;
    public String alamat;
    public String asal_sekolah;
    public String ayah;
    public String deadline;
    public String gender;
    public String ibu;
    public String id;
    public String init_ujkb;
    public String kecamatan;
    public String kelurahan;
    public String nama;
    public String nomor_registrasi;
    public String prestasi;
    public String provinsi;
    public String seleksi_kesehatan;
    public String status_bayar;
    public String status_seleksi;
    public String telepon;
    public String ttl;
    public String unit;
    public String vaksinasi;
    public String waktu;


    public Pendaftar() {
    }


    public Pendaftar(String akun, String alamat, String asal_sekolah, String ayah, String deadline, String gender, String ibu, String id, String init_ujkb, String kecamatan, String kelurahan, String nama, String nomor_registrasi, String prestasi, String provinsi, String seleksi_kesehatan, String status_bayar, String status_seleksi, String telepon, String ttl, String unit, String vaksinasi, String waktu){
        this.akun = akun;
        this.alamat = alamat;
        this.asal_sekolah = asal_sekolah;
        this.ayah = ayah;
        this.deadline = deadline;
        this.gender = gender;
        this.ibu = ibu;
        this.id = id;
        this.init_ujkb = init_ujkb;
        this.kecamatan = kecamatan;
        this.kelurahan = kelurahan;
        this.nama = nama;
        this.nomor_registrasi = nomor_registrasi;
        this.prestasi = prestasi;
        this.provinsi = provinsi;
        this.seleksi_kesehatan = seleksi_kesehatan;
        this.status_bayar = status_bayar;
        this.status_seleksi = status_seleksi;
        this.telepon = telepon;
        this.ttl = ttl;
        this.unit = unit;
        this.vaksinasi = vaksinasi;
        this.waktu = waktu;
    }

    @Exclude
    public Map<String, String> toMap() {
        HashMap<String, String> result = new HashMap<>();
        result.put("akun", akun);
        result.put("alamat", alamat);
        result.put("asal_sekolah", asal_sekolah);
        result.put("ayah", ayah);
        result.put("deadline", deadline);
        result.put("gender", gender);
        result.put("ibu", ibu);
        result.put("id", id);
        result.put("init_ujkb", init_ujkb);
        result.put("kecamatan", kecamatan);
        result.put("kelurahan", kelurahan);
        result.put("nama", nama);
        result.put("nomor_registrasi", nomor_registrasi);
        result.put("prestasi", prestasi);
        result.put("provinsi", provinsi);
        result.put("seleksi_kesehatan", seleksi_kesehatan);
        result.put("status_bayar", status_bayar);
        result.put("status_seleksi", status_seleksi);
        result.put("telepon", telepon);
        result.put("ttl", ttl);
        result.put("unit", unit);
        result.put("vaksinasi", vaksinasi);
        result.put("waktu", waktu);
        return result;
    }


}
