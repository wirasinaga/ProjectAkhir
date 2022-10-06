package com.example.mapsactivity;

import android.Manifest;
import android.app.ActionBar;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.mapsactivity.data.MenuItem;
import com.example.mapsactivity.json.ResponCreateSekolah;
import com.example.mapsactivity.controller.ServiceGenerator;
import com.example.mapsactivity.controller.UserService;
import com.example.mapsactivity.data.DataSekolah;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahSekolahActivity extends AppCompatActivity {

    EditText etnamasekolah, etalamat, etkontaksekolah,etfotosekolah,etlatitude,etlongtitude,etalumni,etidpetugas;
    Button btnlokasi,btntambah, btn_edit, btn_delete;;
    String DATA_JSON_STRING, data_json_string;
    Context context;
    ArrayList<DataSekolah> arrayListSekolah = new ArrayList<>();
    int countData = 0;
    ImageView imagesSekolah;
    Button addFotoSekolah;
    File fileFotoSekolah;
    String currentDateandTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_sekolah);

        Date date = Calendar.getInstance().getTime();
        Locale locale = new Locale("id", "ID");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", locale);
        currentDateandTime = formatter.format(date);

        etnamasekolah = (EditText) findViewById(R.id.et_namasekolah);
        etalamat = (EditText) findViewById(R.id.et_alamat);
        etkontaksekolah = (EditText) findViewById(R.id.et_kontaksekolah);
//        btnlokasi = (Button) findViewById(R.id.btn_lokasi);
        btntambah = (Button) findViewById(R.id.btn_tambah);
        imagesSekolah = findViewById(R.id.fotosekolah);
        addFotoSekolah = findViewById(R.id.addfotosekolah);
        addFotoSekolah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageSekolah();
            }
        });
        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputdata();
            }
        });

    }

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
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
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

    void inputdata(){
        //input data tambah sekolah
        final String namasekolah = etnamasekolah.getText().toString();
        final String alamat = etalamat.getText().toString();
        final String kontak = etkontaksekolah.getText().toString();
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

        service.createSekolah(
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
                    Toast.makeText(TambahSekolahActivity.this,"Data Berhasil di Input", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponCreateSekolah> call, Throwable t) {
                Log.e("Respon_eror", t.getMessage());
            }
        });
    };

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
                imagesSekolah.setImageBitmap(rotatedBitmap);
                fileFotoSekolah = new File(path);
                Log.e("pathFoto", fileFotoSekolah.getPath());
                //imageByteArrayktp = baos.toByteArray();
                //decodedktp = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

            }
        }

    }

}