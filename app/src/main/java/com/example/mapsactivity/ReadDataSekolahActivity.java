package com.example.mapsactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mapsactivity.Adapter.RecycleAdapterAlumni;
import com.example.mapsactivity.Koneksi.DbContract;
import com.example.mapsactivity.controller.ServiceGenerator;
import com.example.mapsactivity.controller.UserService;
import com.example.mapsactivity.data.Alumni;
import com.example.mapsactivity.json.ResponGetAllSekolah;
import com.example.mapsactivity.model.GetAllSekolahModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadDataSekolahActivity extends AppCompatActivity {

    Context mContext;
    TextView alamat, kontak;
    ImageView fotosekolah, mapppp;
    FloatingActionButton fab;
    RecyclerView rvAlumni;

    RecycleAdapterAlumni recyclerViewAdapterAlumni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data_sekolah);
        mContext = this;
        alamat = findViewById(R.id.alamat);
        kontak = findViewById(R.id.kontak);
        fotosekolah = findViewById(R.id.foto_sekolah);
        mapppp = findViewById(R.id.mapppp);
        fab = findViewById(R.id.fab);
        rvAlumni = findViewById(R.id.rv_alumni);

        recyclerViewAdapterAlumni = new RecycleAdapterAlumni();
        setupRecyclerView();

        fab.setOnClickListener(view -> {
            Intent getLastData = new Intent(getIntent());
            Intent intent = new Intent(mContext, EditSekolahActivity.class);
            intent.putExtra("id", getLastData.getStringExtra("id"));
            startActivity(intent);
        });

        mapppp.setOnClickListener(view -> {

        });

        getData();
    }

    void setupRecyclerView() {
        rvAlumni.setAdapter(recyclerViewAdapterAlumni);
        rvAlumni.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapterAlumni.setListAlumni(getAlumni());
        recyclerViewAdapterAlumni.notifyDataSetChanged();
    }

    void getData() {
        Intent intent = new Intent(getIntent());

        UserService service = ServiceGenerator.createService(UserService.class);
        service.getSekolahById(intent.getStringExtra("id")).enqueue(new Callback<ResponGetAllSekolah>() {
            @Override
            public void onResponse(Call<ResponGetAllSekolah> call, Response<ResponGetAllSekolah> response) {
                if (response.isSuccessful()) {
                    List<GetAllSekolahModel> list = response.body().getSekolah();
                    alamat.setText(list.get(0).getAlamat_lengkap());
                    kontak.setText(list.get(0).getKontak_sekolah());
                    Glide.with(mContext)
                            .load(DbContract.IMAGES_Sekolah + list.get(0).getFoto_sekolah())
                            .into(fotosekolah);
                }
            }

            @Override
            public void onFailure(Call<ResponGetAllSekolah> call, Throwable t) {
            }
        });
    }

    List<Alumni> getAlumni() {
        List<Alumni> tempList = new ArrayList<>();

        tempList.add(new Alumni("Ridho Ananda Saputra", "2019"));
        tempList.add(new Alumni("Mandang Bonardo", "2019"));

        return tempList;
    }
}