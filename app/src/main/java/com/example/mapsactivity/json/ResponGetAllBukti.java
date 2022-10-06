package com.example.mapsactivity.json;

import com.example.mapsactivity.model.GetAllBuktiModel;
import com.example.mapsactivity.model.GetAllSekolahModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponGetAllBukti {

    @SerializedName("bukti")
    @Expose
    private List<GetAllBuktiModel> bukti = new ArrayList<>();

    public List<GetAllBuktiModel> getBukti() {
        return bukti;
    }

    public void setBukti(List<GetAllBuktiModel> bukti) {
        this.bukti = bukti;
    }
}
