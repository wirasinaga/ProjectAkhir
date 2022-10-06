package com.example.mapsactivity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mapsactivity.EditSekolahActivity;
import com.example.mapsactivity.Koneksi.DbContract;
import com.example.mapsactivity.R;
import com.example.mapsactivity.ReadDataSekolahActivity;
import com.example.mapsactivity.model.GetAllSekolahModel;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterSekolah extends RecyclerView.Adapter<RecycleAdapterSekolah.MyViewHolder> {

    private List<GetAllSekolahModel> arrayList;
    Context context;


    public RecycleAdapterSekolah(Context context, List<GetAllSekolahModel> arrayList){
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_sekolah, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //menampilkan/parsing data sekolah


//        holder.guide_id.setText(arrayList.get(position+1).toString());
        holder.nama_sekolah.setText(arrayList.get(position).getNama_sekolah());
//         Integer.parseInt(arrayList.get(position).getGuide_id()+1);
        holder.alamat.setText(arrayList.get(position).getAlamat_lengkap());
        holder.kontak.setText(arrayList.get(position).getKontak_sekolah());

        Glide.with(context)
                .load(DbContract.IMAGES_Sekolah+arrayList.get(position).getFoto_sekolah())
                .into(holder.foto_sekolah);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ReadDataSekolahActivity.class);
            intent.putExtra("id", String.valueOf(arrayList.get(position).getId_sekolah()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nama_sekolah, alamat, kontak;
        ImageView foto_sekolah;
        CardView cv_itembuktisekolah;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            nama_sekolah = itemView.findViewById(R.id.namasekolah);
            alamat =  itemView.findViewById(R.id.alamat);
            kontak =  itemView.findViewById(R.id.nomortlpon);
            foto_sekolah =  itemView.findViewById(R.id.foto_sekolah);
            cv_itembuktisekolah =  itemView.findViewById(R.id.cv_listItemsekolah);
        }
    }
}