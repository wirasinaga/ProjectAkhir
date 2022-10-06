package com.example.mapsactivity.data;

public class DataSekolah {

    private String nama_sekolah;
    private String foto_sekolah;
    private String latitude;
    private String longitude;
    private String kontak_sekolah;
    private String alumni_sekolah;
    private String alamat_lengkap;
    private String id_petugas;

    public DataSekolah(String nama_sekolah, String foto_sekolah, String latitude, String longitude, String kontak_sekolah, String alumni_sekolah, String alamat_lengkap, String id_petugas) {
        this.nama_sekolah = nama_sekolah;
        this.foto_sekolah = foto_sekolah;
        this.latitude = latitude;
        this.longitude = longitude;
        this.kontak_sekolah = kontak_sekolah;
        this.alumni_sekolah = alumni_sekolah;
        this.alamat_lengkap = alamat_lengkap;
        this.id_petugas = id_petugas;
    }

    public String getNama_sekolah() {
        return nama_sekolah;
    }

    public void setNama_sekolah(String nama_sekolah) {
        this.nama_sekolah = nama_sekolah;
    }
    public String getFoto_sekolah() {
        return foto_sekolah;
    }

    public void SetFoto_sekolah(String foto_sekolah) {
        this.foto_sekolah = foto_sekolah;
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
