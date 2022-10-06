package com.example.mapsactivity.Koneksi;
//data dapat dari back end
public class DbContract {
    public static final String SERVER_HOST = "http://103.31.39.125/";
    public static final String SERVER_Sekolah = SERVER_HOST+"sekolah";
    public static final String SERVER_Petugas = SERVER_HOST+"petugas";
    public static final String SERVER_Bukti = SERVER_HOST+"bukti";

//    public static final String IMAGES = SERVER_HOST+"images/";
    public static final String IMAGES_Sekolah = SERVER_HOST+"public/images/";
    public static final String IMAGES_Bukti = SERVER_HOST+"public/images/bukti/";

    public static final String LOCAL_STORAGE = "MySharedPref";

    public static final String SERVER_HOST_DAFTAR = "103.31.39.125/";
    //public static final String SERVER_REGISTER_URL = "http://192.168.1.7/loginRegister_mysql_volley_api/createData.php";
}
