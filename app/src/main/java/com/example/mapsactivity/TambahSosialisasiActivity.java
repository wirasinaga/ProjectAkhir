package com.example.mapsactivity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.mapsactivity.controller.ServiceGenerator;
import com.example.mapsactivity.controller.UserService;
import com.example.mapsactivity.data.DataBukti;
import com.example.mapsactivity.json.ResponCreateBukti;

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

public class TambahSosialisasiActivity extends AppCompatActivity {

    EditText et_namaguru, et_kontaksekolah, et_kendala, et_catatan;
    Button btn_bukti;
    String DATA_JSON_STRING, data_json_string;
    Context context;
    ArrayList<DataBukti> arrayListSekolah = new ArrayList<>();
    int countData = 0;
    ImageView imagesBuktiFoto, imagesBuktiFotoSosialisasi, imagebuktifotosekolah;
    Button addFotoBukti, addBuktiFotoSosialisasi, addFotosekolahBukti;
    File fileFotoBukti, fileFotoSosialisasi, fileFotoSekolah;
    private final String boundary = "apiclient-" + System.currentTimeMillis();
    String currentDateandTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_sosialisasi);

        Date date = Calendar.getInstance().getTime();
        Locale locale = new Locale("id", "ID");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", locale);
        currentDateandTime = formatter.format(date);

        et_namaguru = (EditText) findViewById(R.id.et_namaguru);
        et_kontaksekolah = (EditText) findViewById(R.id.et_kontaksekolah);
        et_kendala = (EditText) findViewById(R.id.et_kendala);
        et_catatan = (EditText) findViewById(R.id.et_catatan);
        btn_bukti = (Button) findViewById(R.id.btn_bukti);
        imagesBuktiFoto = (ImageView) findViewById(R.id.buktifoto);
        imagesBuktiFotoSosialisasi = (ImageView) findViewById(R.id.buktifotososialisasi);
        imagebuktifotosekolah = (ImageView) findViewById(R.id.buktifotosekolah);
        addFotoBukti = findViewById(R.id.addbuktifoto);
        addBuktiFotoSosialisasi = findViewById(R.id.addbuktifotososialisasi);
        addFotosekolahBukti = findViewById(R.id.addFotosekolahBukti);
        addFotoBukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageFotoBukti();
            }
        });
        addBuktiFotoSosialisasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageFotoSosialisasi();
            }
        });
        addFotosekolahBukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageFotoBuktiSekolah();
            }
        });
        btn_bukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputdatabukti();
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        });
    }

    //kodingan tempat upload gambar
    private void selectImageFotoBukti() {
        if (check_ReadStoragepermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        }
    }

    private void selectImageFotoSosialisasi() {
        if (check_ReadStoragepermissionbukti()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        }
    }

    private void selectImageFotoBuktiSekolah() {
        if (check_ReadStoragepermissionbukti()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 3);
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

    private boolean check_ReadStoragepermissionbukti() {
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

    private boolean check_ReadStoragepermissionbuktiSekolah() {
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


    void inputdatabukti() {
        //input data bukti
        final String namaguru = et_namaguru.getText().toString();
        final String kontaksekolah = et_kontaksekolah.getText().toString();
        final String kendala = et_kendala.getText().toString();
        final String catatan = et_catatan.getText().toString();
        UserService service = ServiceGenerator.createService(UserService.class);

        RequestBody nama_petugas = RequestBody.create("ardy", MediaType.parse("text/plain"));
        RequestBody jam_sampai = RequestBody.create("", MediaType.parse("text/plain"));
        RequestBody jam_selesai = RequestBody.create("", MediaType.parse("text/plain"));
        RequestBody nama_guru = RequestBody.create(namaguru, MediaType.parse("text/plain"));
        RequestBody catatann = RequestBody.create(catatan, MediaType.parse("text/plain"));
        RequestBody kontak_sekolah = RequestBody.create(kontaksekolah, MediaType.parse("text/plain"));
        RequestBody deskripsi_kendala = RequestBody.create(kendala, MediaType.parse("text/plain"));
        RequestBody nama_sekolah = RequestBody.create("", MediaType.parse("text/plain"));

        RequestBody imageBodyBukti = RequestBody.create(fileFotoBukti, MediaType.parse("image/*"));
        String fileImageNameBukti = "_imagesBukti_" + currentDateandTime + "." + getMimeType(fileFotoBukti.getPath());
        MultipartBody.Part datafotobukti = MultipartBody.Part.createFormData("foto_bukti", fileImageNameBukti, imageBodyBukti);

        RequestBody imageBodyKendala = RequestBody.create(fileFotoSosialisasi, MediaType.parse("image/*"));
        String fileImageNameKendala = "_imagesSosialisasi_" + currentDateandTime + "." + getMimeType(fileFotoSosialisasi.getPath());
        MultipartBody.Part datafotokendala = MultipartBody.Part.createFormData("foto_kendala", fileImageNameKendala, imageBodyKendala);

        RequestBody imageBodyFotoSekolah = RequestBody.create(fileFotoSekolah, MediaType.parse("image/*"));
        String fileImageNameFotoSekolah = "_imagesSekolah_" + currentDateandTime + "." + getMimeType(fileFotoSekolah.getPath());
        MultipartBody.Part datafotosekolah = MultipartBody.Part.createFormData("foto_sekolah", fileImageNameFotoSekolah, imageBodyFotoSekolah);

        service.createBukti(
                nama_petugas,
                jam_sampai,
                jam_selesai,
                nama_guru,
                catatann,
                kontak_sekolah,
                deskripsi_kendala,
                nama_sekolah,
                datafotobukti,
                datafotokendala,
                datafotosekolah
        ).enqueue(new Callback<ResponCreateBukti>() {
            @Override
            public void onResponse(Call<ResponCreateBukti> call, Response<ResponCreateBukti> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TambahSosialisasiActivity.this, "Data Berhasil di Input", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponCreateBukti> call, Throwable t) {
                Log.e("Respon_eror", t.getMessage());
            }
        });
    }

    ;

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
                imagesBuktiFoto.setImageBitmap(rotatedBitmap);
                fileFotoBukti = new File(path);
                //imageByteArrayktp = baos.toByteArray();
                //decodedktp = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

            } else if (requestCode == 2) {
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
                imagesBuktiFotoSosialisasi.setImageBitmap(rotatedBitmap);
                fileFotoSosialisasi = new File(path);
//                fileFotoSosialisasi = new File(Objects.requireNonNull(Objects.requireNonNull(selectedImage).getPath()));
                //imageByteArrayktp = baos.toByteArray();
                //decodedktp = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

            } else if (requestCode == 3) {
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
                imagebuktifotosekolah.setImageBitmap(rotatedBitmap);
                fileFotoSekolah = new File(path);
//                fileFotoSekolah = new File(Objects.requireNonNull(Objects.requireNonNull(selectedImage).getPath()));
                //imageByteArrayktp = baos.toByteArray();
                //decodedktp = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

            }
        }

    }
}