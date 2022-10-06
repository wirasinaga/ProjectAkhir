package com.example.mapsactivity.data;

public class DataBukti {

    private String nama_petugas;
    private String foto_sekolah;
    private String nama_guru;
    private String catatan;
    private String kontak_sekolah;
    private String deskripsi_kendala;
    private String nama_sekolah;


    public DataBukti(String nama_petugas, String foto_sekolah, String nama_guru, String catatan, String kontak_sekolah, String deskripsi_kendala, String nama_sekolah) {
        this.nama_petugas = nama_petugas;
        this.foto_sekolah = foto_sekolah;
        this.nama_guru = nama_guru;
        this.catatan = catatan;
        this.kontak_sekolah = kontak_sekolah;
        this.deskripsi_kendala = deskripsi_kendala;
        this.nama_sekolah = nama_sekolah;
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

    public void SetFoto_sekolah(String foto_sekolah) {
        this.foto_sekolah = foto_sekolah;
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

    public String getNama_sekolah() {
        return nama_sekolah;
    }

    public void setNama_sekolah(String nama_sekolah) {
        this.nama_sekolah = nama_sekolah;
    }
}
