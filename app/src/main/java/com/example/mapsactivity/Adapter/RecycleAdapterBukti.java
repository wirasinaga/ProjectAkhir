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
import com.example.mapsactivity.Koneksi.DbContract;
import com.example.mapsactivity.R;
import com.example.mapsactivity.ReadDataBuktiActivity;
import com.example.mapsactivity.ReadDataBuktiFragment;
import com.example.mapsactivity.ReadDataSekolahActivity;
import com.example.mapsactivity.model.GetAllBuktiModel;
import com.example.mapsactivity.model.GetAllSekolahModel;

import java.util.List;

public class RecycleAdapterBukti extends RecyclerView.Adapter<RecycleAdapterBukti.MyViewHolder> {

    private List<GetAllBuktiModel> arrayList;
    Context context;


    public RecycleAdapterBukti(Context context, List<GetAllBuktiModel> arrayList){
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_bukti, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //menampilkan/parsing data bukti
//        holder.guide_id.setText(arrayList.get(position+1).toString());
        holder.nama_guru.setText(arrayList.get(position).getNama_guru());
//         Integer.parseInt(arrayList.get(position).getGuide_id()+1);
        holder.kontak_sekolah.setText(arrayList.get(position).getKontak_sekolah());
        holder.kendala.setText(arrayList.get(position).getDeskripsi_kendala());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ReadDataBuktiActivity.class);
            intent.putExtra("id", String.valueOf(arrayList.get(position).getId_bukti()));
            context.startActivity(intent);
        });
        Glide.with(context)
                .load(DbContract.IMAGES_Bukti+arrayList.get(position).getFoto_bukti())
                .into(holder.bukti_foto);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nama_guru, kontak_sekolah, kendala,catatan;
        ImageView bukti_foto, bukti_sosialisasi;
        CardView cv_itembukti;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            nama_guru = itemView.findViewById(R.id.nama_guru);
            kontak_sekolah =  itemView.findViewById(R.id.kontaksekolah);
            kendala =  itemView.findViewById(R.id.kendala);
            bukti_foto =  itemView.findViewById(R.id.foto_bukti);
            cv_itembukti =  itemView.findViewById(R.id.cv_listItembukti);
        }
    }
}
