package com.example.mapsactivity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllSekolahModel {

    @SerializedName("id_sekolah")
    @Expose
    private int id_sekolah;

    @SerializedName("foto_sekolah")
    @Expose
    private String foto_sekolah;

    @SerializedName("nama_sekolah")
    @Expose
    private String nama_sekolah;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("kontak_sekolah")
    @Expose
    private String kontak_sekolah;

    @SerializedName("alumni_sekolah")
    @Expose
    private String alumni_sekolah;

    @SerializedName("alamat_lengkap")
    @Expose
    private String alamat_lengkap;

    @SerializedName("id_petugas")
    @Expose
    private String id_petugas;

    public int getId_sekolah() {
        return id_sekolah;
    }

    public void setId_sekolah(int id_sekolah) {
        this.id_sekolah = id_sekolah;
    }

    public String getFoto_sekolah() {
        return foto_sekolah;
    }

    public void setFoto_sekolah(String foto_sekolah) {
        this.foto_sekolah = foto_sekolah;
    }

    public String getNama_sekolah() {
        return nama_sekolah;
    }

    public void setNama_sekolah(String nama_sekolah) {
        this.nama_sekolah = nama_sekolah;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getKontak_sekolah() {
        return kontak_sekolah;
    }

    public void setKontak_sekolah(String kontak_sekolah) {
        this.kontak_sekolah = kontak_sekolah;
    }

    public String getAlumni_sekolah() {
        return alumni_sekolah;
    }

    public void setAlumni_sekolah(String alumni_sekolah) {
        this.alumni_sekolah = alumni_sekolah;
    }

    public String getAlamat_lengkap() {
        return alamat_lengkap;
    }

    public void setAlamat_lengkap(String alamat_lengkap) {
        this.alamat_lengkap = alamat_lengkap;
    }

    public String getId_petugas() {
        return id_petugas;
    }

    public void setId_petugas(String id_petugas) {
        this.id_petugas = id_petugas;
    }
}
