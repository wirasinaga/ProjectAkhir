package com.example.mapsactivity;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapsactivity.Adapter.RecycleAdapterSekolah;
import com.example.mapsactivity.controller.ServiceGenerator;
import com.example.mapsactivity.controller.UserService;
import com.example.mapsactivity.json.ResponGetAllSekolah;
import com.example.mapsactivity.model.GetAllSekolahModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SekolahFragment extends Fragment {

    RecyclerView listRecycler;
    Context mContext;

    public SekolahFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_sekolah, container, false);
        mContext = requireContext();

        listRecycler = root.findViewById(R.id.list_data_sekolah);
        listRecycler.setHasFixedSize(true);
        listRecycler.setNestedScrollingEnabled(false);
        listRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    void getData() {
        UserService service = ServiceGenerator.createService(UserService.class);
        service.getAllSekolah().enqueue(new Callback<ResponGetAllSekolah>() {
            @Override
            public void onResponse(Call<ResponGetAllSekolah> call, Response<ResponGetAllSekolah> response) {
                if (response.isSuccessful()) {
                    List<GetAllSekolahModel> list = response.body().getSekolah();
                    if (list.size() > 0) {
//                        recycle view untuk menampilkan list data sekolah
                        RecycleAdapterSekolah adapter = new RecycleAdapterSekolah(mContext, list);
                        listRecycler.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponGetAllSekolah> call, Throwable t) {

            }
        });
    }
}

