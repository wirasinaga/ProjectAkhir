package com.example.mapsactivity.json;

import com.example.mapsactivity.model.GetAllSekolahModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponGetAllSekolah {

    @SerializedName("sekolah")
    @Expose
    private List<GetAllSekolahModel> sekolah = new ArrayList<>();

    public List<GetAllSekolahModel> getSekolah() {
        return sekolah;
    }

    public void setSekolah(List<GetAllSekolahModel> sekolah) {
        this.sekolah = sekolah;
    }
}
