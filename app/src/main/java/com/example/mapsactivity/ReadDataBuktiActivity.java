package com.example.mapsactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mapsactivity.Koneksi.DbContract;
import com.example.mapsactivity.controller.ServiceGenerator;
import com.example.mapsactivity.controller.UserService;
import com.example.mapsactivity.json.ResponGetAllBukti;
import com.example.mapsactivity.json.ResponGetAllSekolah;
import com.example.mapsactivity.model.GetAllBuktiModel;
import com.example.mapsactivity.model.GetAllSekolahModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadDataBuktiActivity extends AppCompatActivity {

    Context mContext;
    TextView jamsampai,jamselesai,tv_namaguru,tv_kendala,tv_catatan;
    ImageView foto_sekolahbukti,foto_kendala,foto_sosialisasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data_bukti);
        mContext = this;
        jamsampai = findViewById(R.id.jamsampai);
        jamselesai = findViewById(R.id.jamselesai);
        tv_namaguru = findViewById(R.id.tv_namaguru);
        tv_kendala = findViewById(R.id.tv_kendala);
        tv_catatan = findViewById(R.id.tv_catatan);
        foto_sekolahbukti = findViewById(R.id.foto_sekolahbukti);
        foto_kendala = findViewById(R.id.foto_kendala);
        foto_sosialisasi = findViewById(R.id.foto_sosialisasi);

        getDataBukti();
    }
    void getDataBukti(){
        Intent intent = new Intent(getIntent());

        UserService service = ServiceGenerator.createService(UserService.class);
        service.getBuktiById(intent.getStringExtra("id")).enqueue(new Callback<ResponGetAllBukti>() {
            @Override
            public void onResponse(Call<ResponGetAllBukti> call, Response<ResponGetAllBukti> response) {
                if(response.isSuccessful()){
                    List<GetAllBuktiModel> list = response.body().getBukti();
                    jamsampai.setText(list.get(0).getJam_sampai());
                    jamselesai.setText(list.get(0).getJam_selesai());
                    tv_namaguru.setText(list.get(0).getNama_guru());
                    tv_kendala.setText(list.get(0).getDeskripsi_kendala());
                    tv_catatan.setText(list.get(0).getCatatan());
                    Glide.with(mContext)
                            .load(DbContract.IMAGES_Bukti+list.get(0).getFoto_sekolah())
                            .into(foto_sekolahbukti);
                    Glide.with(mContext)
                            .load(DbContract.IMAGES_Bukti+list.get(0).getFoto_sekolah())
                            .into(foto_kendala);
                    Glide.with(mContext)
                            .load(DbContract.IMAGES_Bukti+list.get(0).getFoto_sekolah())
                            .into(foto_sosialisasi);
                }
            }

            @Override
            public void onFailure(Call<ResponGetAllBukti> call, Throwable t) {

            }
        });
    }}
