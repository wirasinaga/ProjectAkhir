package com.example.mapsactivity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllBuktiModel {

    @SerializedName("id_bukti")
    @Expose
    private int id_bukti;

    @SerializedName("nama_petugas")
    @Expose
    private String nama_petugas;

    @SerializedName("foto_sekolah")
    @Expose
    private String foto_sekolah;

    @SerializedName("jam_sampai")
    @Expose
    private String jam_sampai;

    @SerializedName("jam_selesai")
    @Expose
    private String jam_selesai;

    @SerializedName("foto_bukti")
    @Expose
    private String foto_bukti;

    @SerializedName("nama_guru")
    @Expose
    private String nama_guru;

    @SerializedName("catatan")
    @Expose
    private String catatan;

    @SerializedName("kontak_sekolah")
    @Expose
    private String kontak_sekolah;

    @SerializedName("deskripsi_kendala")
    @Expose
    private String deskripsi_kendala;

    @SerializedName("foto_kendala")
    @Expose
    private String foto_kendala;

    @SerializedName("nama_sekolah")
    @Expose
    private String nama_sekolah;

    public int getId_bukti() {
        return id_bukti;
    }

    public void setId_bukti(int id_bukti) {
        this.id_bukti = id_bukti;
    }

    public String getNama_petugas() {
        return nama_petugas;
    }

    public void setNama_petugas(String nama_petugas) {
        this.nama_petugas = nama_petugas;
    }

    public String getFoto_sekolah() {
        return foto_sekolah;
    }

    public void setFoto_sekolah(String foto_sekolah) {
        this.foto_sekolah = foto_sekolah;
    }

    public String getJam_sampai() {
        return jam_sampai;
    }

    public void setJam_sampai(String jam_sampai) {
        this.jam_sampai = jam_sampai;
    }

    public String getJam_selesai() {
        return jam_selesai;
    }

    public void setJam_selesai(String jam_selesai) {
        this.jam_selesai = jam_selesai;
    }

    public String getFoto_bukti() {
        return foto_bukti;
    }

    public void setFoto_bukti(String foto_bukti) {
        this.foto_bukti = foto_bukti;
    }

    public String getNama_guru() {
        return nama_guru;
    }

    public void setNama_guru(String nama_guru) {
        this.nama_guru = nama_guru;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getKontak_sekolah() {
        return kontak_sekolah;
    }

    public void setKontak_sekolah(String kontak_sekolah) {
        this.kontak_sekolah = kontak_sekolah;
    }

    public String getDeskripsi_kendala() {
        return deskripsi_kendala;
    }

    public void setDeskripsi_kendala(String deskripsi_kendala) {
        this.deskripsi_kendala = deskripsi_kendala;
    }

    public String getFoto_kendala() {
        return foto_kendala;
    }

    public void setFoto_kendala(String foto_kendala) {
        this.foto_kendala = foto_kendala;
    }

    public String getNama_sekolah() {
        return nama_sekolah;
    }

    public void setNama_sekolah(String nama_sekolah) {
        this.nama_sekolah = nama_sekolah;
    }
}
