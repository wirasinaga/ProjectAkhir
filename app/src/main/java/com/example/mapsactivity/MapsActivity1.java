//package com.example.mapsactivity
//
//import android.app.Dialog
//import androidx.fragment.app.FragmentActivity
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.GoogleMap
//import android.os.Bundle
//import com.google.android.gms.maps.SupportMapFragment
//import com.example.mapsactivity.R
//import android.location.Geocoder
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import com.google.android.gms.maps.CameraUpdateFactory
//import android.graphics.drawable.ColorDrawable
//import android.view.ViewGroup
//import android.widget.Toast
//import android.content.Intent
//import android.location.Address
//import android.view.View
//import android.view.Window
//import android.widget.Button
//import android.widget.SearchView
//import com.example.mapsactivity.TambahSosialisasiActivity
//import android.widget.TextView
//import com.example.mapsactivity.databinding.ActivityMapsBinding
//import java.io.IOException
//import java.util.ArrayList
//import java.util.LinkedHashSet
//
//class MapsActivity1 : FragmentActivity(), OnMapReadyCallback {
//    private var mMap: GoogleMap? = null
//    private var binding: ActivityMapsBinding? = null
//    var searchView: SearchView? = null
//    var list = ArrayList<String>()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMapsBinding.inflate(layoutInflater)
//        setContentView(binding!!.root)
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment?
//        searchView = findViewById(R.id.sv_location)
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                val location = searchView.getQuery().toString()
//                if (location != null || location == "") {
//                    val geocoder = Geocoder(this@MapsActivity1)
//                    var addressList: List<Address>? = null
//                    try {
//                        addressList = geocoder.getFromLocationName(location, 1)
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
//                    val address = addressList!![0]
//                    val latLng = LatLng(address.latitude, address.longitude)
//                    mMap!!.addMarker(MarkerOptions().position(latLng).title(location))
//                    mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//                    mMap!!.animateCamera(
//                        CameraUpdateFactory.newLatLngZoom(
//                            latLng, 13.6f
//                        )
//                    )
//                    list.add(location)
//                }
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                return false
//            }
//        })
//        mapFragment!!.getMapAsync(this)
//        binding!!.fab.setOnClickListener { view: View? -> showDialogPeta() }
//        binding!!.list.setOnClickListener { view: View? -> showListSelected() }
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        mMap!!.uiSettings.isMyLocationButtonEnabled
//        mMap!!.uiSettings.isZoomControlsEnabled
//        mMap!!.uiSettings.setAllGesturesEnabled(true)
//        mMap!!.uiSettings.isZoomGesturesEnabled
//        mMap!!.uiSettings.isMapToolbarEnabled
//
//        //menambakan marker di mapss
//
//
//        // Add a marker in Sydney and move the camera
////        LatLng sd01 = new LatLng(0.531575, 101.442467);
////
////        mMap.addMarker(new MarkerOptions().position(sd01).title("SDN 1 Pekanbaru"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(sd01));
////        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
////                sd01, 13.6f));
//
////        // Add a marker in Sydney and move the camera
////        LatLng sd189 = new LatLng(0.439823, 101.388942);
////        mMap.addMarker(new MarkerOptions().position(sd189).title("SDN 189 Pekanbaru"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(sd189));
////
////        // Add a marker in Sydney and move the camera
////        LatLng celtex = new LatLng(0.464076, 101.402707);
////        mMap.addMarker(new MarkerOptions().position(celtex).title("SDN 110 Pekanbaru"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(celtex));
////
////        // Add a marker in Sydney and move the camera
////        LatLng sd100 = new LatLng(0.527752, 101.433059);
////        mMap.addMarker(new MarkerOptions().position(sd100).title("Sekolah Dasar Negeri 100 Pekanbaru"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(sd100));
////
////        // Add a marker in Sydney and move the camera
////        LatLng sd111 = new LatLng(0.445079, 101.386312);
////        mMap.addMarker(new MarkerOptions().position(sd111).title("Sekolah Dasar Negeri 111 Pekanbaru"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(sd111));
////
////        // Add a marker in Sydney and move the camera
////        LatLng sd183 = new LatLng(0.452699, 101.376137);
////        mMap.addMarker(new MarkerOptions().position(sd183).title("Sekolah Dasar Negeri 183 Pekanbaru"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(sd183));
////
////        // Add a marker in Sydney and move the camera
////        LatLng sd15 = new LatLng(0.514779, 101.445665);
////        mMap.addMarker(new MarkerOptions().position(sd15).title("Sekolah Dasar Negeri 15 Pekanbaru"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(sd15));
////
////        // Add a marker in Sydney and move the camera
////        LatLng sd3 = new LatLng(0.536785, 101.438764);
////        mMap.addMarker(new MarkerOptions().position(sd3).title("Sekolah Dasar Negeri 3 Pekanbaru"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(sd3));
//    }
//
//    fun showDialogPeta() {
//        val myDialog = Dialog(this)
//        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        myDialog.setContentView(R.layout.activity_rpemberitahuan)
//        myDialog.window!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.transparent_black_hex_11)))
//        myDialog.setCancelable(true)
//        myDialog.window!!.setLayout(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        myDialog.show()
//
//        //onclick button lanjut
//        val btn_lanjut = myDialog.findViewById<Button>(R.id.btn_lanjut)
//        btn_lanjut.setOnClickListener { view: View? ->
//            Toast.makeText(
//                this,
//                "Okey",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//
//        //onclick button sosialisasi
//        val btn_sosialisasi = myDialog.findViewById<Button>(R.id.btn_sosialisasi)
//        btn_sosialisasi.setOnClickListener { view: View? ->
////            Toast.makeText(this, "hello", Toast.LENGTH_LONG).show();
//            startActivity(Intent(this, TambahSosialisasiActivity::class.java))
//        }
//    }
//
//    fun showListSelected() {
//        val myDialog = Dialog(this)
//        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        myDialog.setContentView(R.layout.activity_list_selected_location)
//        myDialog.window!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.transparent_black_hex_11)))
//        myDialog.setCancelable(true)
//        myDialog.window!!.setLayout(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        val text = myDialog.findViewById<TextView>(R.id.textView2)
//        val set: MutableSet<String> = LinkedHashSet()
//        set.addAll(list)
//        list.clear()
//        list.addAll(set)
//        for (i in list.indices) {
//            text.append(list[i])
//            text.append("\n")
//        }
//        myDialog.show()
//
//        //onclick button lanjut
//        val btn_lanjut = myDialog.findViewById<Button>(R.id.btn_lanjut)
//        btn_lanjut.setOnClickListener { view: View? ->
//            Toast.makeText(
//                this,
//                "Okey",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//
//        //onclick button sosialisasi
//        val btn_sosialisasi = myDialog.findViewById<Button>(R.id.btn_sosialisasi)
//        btn_sosialisasi.setOnClickListener { view: View? ->
////            Toast.makeText(this, "hello", Toast.LENGTH_LONG).show();
//            startActivity(Intent(this, TambahSosialisasiActivity::class.java))
//        }
//    }
//}