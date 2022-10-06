package com.example.mapsactivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapsactivity.Adapter.RecycleAdapterBukti;
import com.example.mapsactivity.controller.ServiceGenerator;
import com.example.mapsactivity.controller.UserService;
import com.example.mapsactivity.json.ResponGetAllBukti;
import com.example.mapsactivity.model.GetAllBuktiModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BuktiFragment extends Fragment {

    RecyclerView listRecycler;
    Context mContext;

    public BuktiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_bukti, container, false);
        mContext = requireContext();

        listRecycler = root.findViewById(R.id.list_data_bukti);
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

    void getData(){

        UserService service = ServiceGenerator.createService(UserService.class);
        service.getAllBukti().enqueue(new Callback<ResponGetAllBukti>() {
            @Override
            public void onResponse(Call<ResponGetAllBukti> caller, Response<ResponGetAllBukti> response) {
                if(response.isSuccessful()){
                    List<GetAllBuktiModel> list = response.body().getBukti();
                    if(list.size() > 0 ){
                        RecycleAdapterBukti adapter = new RecycleAdapterBukti(mContext, list);
                        listRecycler.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponGetAllBukti> caller, Throwable t) {

            }
        });
    }

}
