package com.example.mapsactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mapsactivity.Adapter.RecycleAdapterSekolah;
import com.example.mapsactivity.Koneksi.DbContract;
import com.example.mapsactivity.controller.ServiceGenerator;
import com.example.mapsactivity.controller.UserService;
import com.example.mapsactivity.json.ResponCreateSekolah;
import com.example.mapsactivity.json.ResponGetAllSekolah;
import com.example.mapsactivity.model.GetAllSekolahModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSekolahActivity extends AppCompatActivity {
    RecyclerView listRecycler;
    Context mContext;

    TextView edNama, edAlamat,edKontak;
    ImageView editfotosekolah;
    Button btn_edittambah,addEditFotoSekolah,btn_delete;
    File fileFotoSekolah;
    String currentDateandTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sekolah);
        mContext = this;

        Date date = Calendar.getInstance().getTime();
        Locale locale = new Locale("id", "ID");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", locale);
        currentDateandTime = formatter.format(date);

        edNama = findViewById(R.id.et_editnamasekolah);
        edAlamat = findViewById(R.id.et_editalamat);
        edKontak = findViewById(R.id.et_editkontaksekolah);
        editfotosekolah = findViewById(R.id.editfotosekolah);
        addEditFotoSekolah = findViewById(R.id.addeditfotosekolah);
        btn_edittambah = findViewById(R.id.btn_edittambah);
        btn_delete = findViewById(R.id.btn_delete);

        getData();

        addEditFotoSekolah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageSekolah();
            }
        });

        btn_edittambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editdata();
            }
        });

        btn_delete.setOnClickListener(view -> {
            deleteData();
        });

    }

    void getData(){
        Intent intent = new Intent(getIntent());

        UserService service = ServiceGenerator.createService(UserService.class);
        service.getSekolahById(intent.getStringExtra("id")).enqueue(new Callback<ResponGetAllSekolah>() {
            @Override
            public void onResponse(Call<ResponGetAllSekolah> call, Response<ResponGetAllSekolah> response) {
                if(response.isSuccessful()){
                    List<GetAllSekolahModel> list = response.body().getSekolah();
                    edNama.setText(list.get(0).getNama_sekolah());
                    edAlamat.setText(list.get(0).getAlamat_lengkap());
                    edKontak.setText(list.get(0).getKontak_sekolah());
                    Glide.with(mContext)
                            .load(DbContract.IMAGES_Sekolah+list.get(0).getFoto_sekolah())
                            .into(editfotosekolah);
                }
            }

            @Override
            public void onFailure(Call<ResponGetAllSekolah> call, Throwable t) {

            }
        });
    }

    void editdata(){
        Intent intent = new Intent(getIntent());
        //input data tambah sekolah
        final String namasekolah = edNama.getText().toString();
        final String alamat = edAlamat.getText().toString();
        final String kontak = edKontak.getText().toString();
        UserService service = ServiceGenerator.createService(UserService.class);

        RequestBody nama_sekolah = RequestBody.create(namasekolah, MediaType.parse("text/plain"));
        RequestBody latitude = RequestBody.create("123", MediaType.parse("text/plain"));
        RequestBody longitude = RequestBody.create("123", MediaType.parse("text/plain"));
        RequestBody kontak_sekolah = RequestBody.create(kontak, MediaType.parse("text/plain"));
        RequestBody alumni_sekolah = RequestBody.create("asdf", MediaType.parse("text/plain"));
        RequestBody alamat_lengkap = RequestBody.create(alamat, MediaType.parse("text/plain"));
        RequestBody id_petugas = RequestBody.create("1", MediaType.parse("text/plain"));

        RequestBody imageBody = RequestBody.create(fileFotoSekolah, MediaType.parse("image/*"));
        String fileImageName = "_imagesSekolah_" + currentDateandTime + "." + getMimeType(fileFotoSekolah.getPath());
        MultipartBody.Part dataFotoSekolah = MultipartBody.Part.createFormData("foto_sekolah", fileImageName, imageBody);

        service.updateSekolah(
                intent.getStringExtra("id"),
                nama_sekolah,
                latitude,
                longitude,
                kontak_sekolah,
                alumni_sekolah,
                alamat_lengkap,
                id_petugas,
                dataFotoSekolah
        ).enqueue(new Callback<ResponCreateSekolah>() {
            @Override
            public void onResponse(Call<ResponCreateSekolah> call, Response<ResponCreateSekolah> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext,"Data Berhasil di Input", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponCreateSekolah> call, Throwable t) {
                Log.e("Respon_eror", t.getMessage());
            }
        });
    };

    private void selectImageSekolah() {
        if (check_ReadStoragepermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        }
    }

    public String getPath(Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    private boolean check_ReadStoragepermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            789);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return false;
    }

    public static String getMimeType(String fileName) {
        String encoded;
        try {
            encoded = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            encoded = fileName;
        }
        return MimeTypeMap.getFileExtensionFromUrl(encoded).toLowerCase();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(Objects.requireNonNull(selectedImage));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);

                String path = getPath(selectedImage);
                Matrix matrix = new Matrix();
                ExifInterface exif;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        exif = new ExifInterface(path);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                editfotosekolah.setImageBitmap(rotatedBitmap);
                fileFotoSekolah = new File(path);
                Log.e("pathFoto", fileFotoSekolah.getPath());
                //imageByteArrayktp = baos.toByteArray();
                //decodedktp = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

            }
        }

    }

    void deleteData(){
        Intent intent = new Intent(getIntent());

        UserService service = ServiceGenerator.createService(UserService.class);
        service.deleteSekolah(intent.getStringExtra("id")).enqueue(new Callback<ResponCreateSekolah>() {
            @Override
            public void onResponse(Call<ResponCreateSekolah> call, Response<ResponCreateSekolah> response) {
                if(response.isSuccessful()){
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponCreateSekolah> call, Throwable t) {

            }
        });
    }

}