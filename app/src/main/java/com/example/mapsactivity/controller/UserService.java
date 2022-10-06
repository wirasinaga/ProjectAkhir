package com.example.mapsactivity.controller;

import com.example.mapsactivity.json.ResponCreateBukti;
import com.example.mapsactivity.json.ResponCreateSekolah;
import com.example.mapsactivity.json.ResponGetAllBukti;
import com.example.mapsactivity.json.ResponGetAllSekolah;
import com.example.mapsactivity.json.ResponseAcoSorting;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {

    @GET("sekolah")
    Call<ResponGetAllSekolah> getAllSekolah();

    @GET("sekolah/{id}")
    Call<ResponGetAllSekolah> getSekolahById(@Path("id") String id);

    @POST("sekolah")
    @Multipart
    Call<ResponCreateSekolah> createSekolah(
            @Part("nama_sekolah") RequestBody nama_sekolah,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part("kontak_sekolah") RequestBody kontak_sekolah,
            @Part("alumni_sekolah") RequestBody alumni_sekolah,
            @Part("alamat_lengkap") RequestBody alamat_lengkap,
            @Part("id_petugas") RequestBody id_petugas,
            @Part MultipartBody.Part foto_sekolah
    );

    @POST("sekolah/sorting/location")
    Call<ResponseAcoSorting> acoSort(
            @Body ResponseAcoSorting.RequestBody body
    );

    @POST("sekolah/{id_sekolah}")
    @Multipart
    Call<ResponCreateSekolah> updateSekolah(
            @Path("id_sekolah") String id_sekolah,
            @Part("nama_sekolah") RequestBody nama_sekolah,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part("kontak_sekolah") RequestBody kontak_sekolah,
            @Part("alumni_sekolah") RequestBody alumni_sekolah,
            @Part("alamat_lengkap") RequestBody alamat_lengkap,
            @Part("id_petugas") RequestBody id_petugas,
            @Part MultipartBody.Part foto_sekolah
    );

    @DELETE("sekolah/{id_sekolah}")
    Call<ResponCreateSekolah> deleteSekolah(@Path("id_sekolah") String id_sekolah);


    @PUT("bukti/{id_bukti}")
    Call<ResponCreateSekolah> updateBukti(
            @Path("id_bukti") String id_bukti, @FieldMap Map<String, String> params);

    @DELETE("bukti/{id_bukti}")
    Call<ResponCreateSekolah> deleteBukti(@Path("id_bukti") String id_bukti);

    @GET("bukti")
    Call<ResponGetAllBukti> getAllBukti();

    @GET("bukti/{id}")
    Call<ResponGetAllBukti> getBuktiById(@Path("id") String id);

    @POST("bukti")
    @Multipart
    Call<ResponCreateBukti> createBukti(
            @Part("nama_petugas") RequestBody nama_petugas,
            @Part("jam_sampai") RequestBody jam_sampai,
            @Part("jam_selesai") RequestBody jam_selesai,
            @Part("nama_guru") RequestBody nama_guru,
            @Part("catatan") RequestBody catatan,
            @Part("kontak_sekolah") RequestBody kontak_sekolah,
            @Part("deskripsi_kendala") RequestBody deskripsi_kendala,
            @Part("nama_sekolah") RequestBody nama_sekolah,
            @Part MultipartBody.Part foto_bukti,
            @Part MultipartBody.Part foto_kendala,
            @Part MultipartBody.Part foto_sekolah
    );

}
