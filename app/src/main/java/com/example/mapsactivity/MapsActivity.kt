package com.example.mapsactivity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.example.mapsactivity.controller.ServiceGenerator
import com.example.mapsactivity.controller.UserService
import com.example.mapsactivity.data.Tempat
import com.example.mapsactivity.databinding.ActivityMapsBinding
import com.example.mapsactivity.json.ResponGetAllSekolah
import com.example.mapsactivity.json.ResponseAcoSorting
import com.example.mapsactivity.others.Tools
import com.example.mapsactivity.others.Tools.decode
import com.example.mapsactivity.remote.Result
import com.example.mapsactivity.remote.entity.DirectionLegModel
import com.example.mapsactivity.remote.entity.DirectionResponseModel
import com.example.mapsactivity.remote.entity.DirectionRouteModel
import com.example.mapsactivity.viewmodel.LocationViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class MapsActivity : FragmentActivity(), OnMapReadyCallback, EasyPermissions.PermissionCallbacks,
    GoogleMap.OnMarkerClickListener {

    private var mMap: GoogleMap? = null
    private var binding: ActivityMapsBinding? = null
    var searchView: SearchView? = null
    var list = ArrayList<String>()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private var polylineFinal: Polyline? = null
    private var currentLocation: Location? = null
    private var locationCallback: LocationCallback? = null
    private var isRequestingLocationUpdates = false
    private val locationViewModel: LocationViewModel by viewModels()

    @SuppressLint("VisibleForTests")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

//untuk dapatkan lokasi dari peta
        fusedLocationProviderClient = FusedLocationProviderClient(this)
//        requestPermission()


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

//        Untuk ambil map
        mapFragment!!.getMapAsync(this)

        showAllPoints()
        search()

        binding!!.fab.setOnClickListener { showDialogPeta() }


    }


    private fun showAllPoints() {
        binding!!.fabMaps.setOnClickListener {
            Toast.makeText(this, "Loading.. show points", Toast.LENGTH_LONG).show()
            requestPermission()
        }
    }
//
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.let { map ->
            map.uiSettings.isMyLocationButtonEnabled
            map.uiSettings.isZoomControlsEnabled
            map.uiSettings.isZoomGesturesEnabled
            map.uiSettings.isMapToolbarEnabled
            map.uiSettings.setAllGesturesEnabled(true)

//untuk nampilin lokasi sekarang
            map.isMyLocationEnabled = true
//function untuk marker ketika ditekan
            mMap!!.setOnMarkerClickListener(this)
        }
    }
//untuk nyari lokasi dari peta, dengan inputan user
    private fun search() {
        binding?.svLocation?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_LONG).show()
                if (query != null || query == "") {
                    val geocoder = Geocoder(this@MapsActivity)
                    var addressList: List<Address>? = null
                    try {
                        addressList = geocoder.getFromLocationName(query, 1)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    val address = addressList?.get(0)
                    if ((addressList?.size ?: 0) > 1) {
                        Toast.makeText(applicationContext, "Is Empty", Toast.LENGTH_SHORT).show()
                    } else {
                        val latLng = LatLng(address!!.latitude, address.longitude)

                        mMap!!.addMarker(
                            MarkerOptions().position(latLng).title(query)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        )
                        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                        mMap!!.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                latLng, 17.6f
                            )
                        )
                        drawLine(latLng)
                        list.add(query)
                    }
                }
                return false
            }
//function yang handle ketika diketikkan sesuatu
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

//    mengambil data sekolah serta koordinat dari API
    private fun getAllLocation(location: LatLng) {
        val service = ServiceGenerator.createService(UserService::class.java)
//retrofit
        service.allSekolah.enqueue(object : Callback<ResponGetAllSekolah> {
            override fun onResponse(
                call: Call<ResponGetAllSekolah>,
                response: Response<ResponGetAllSekolah>
            ) {
                if (response.isSuccessful) {
                    val tempListSekolah = arrayListOf<Tempat>()
                    var nameSekolah = ""
                    response.body()?.sekolah?.forEach {
                        val tempat = Tempat(it.nama_sekolah, it.latitude, it.longitude)
                        tempListSekolah.add(tempat)
                    }

                    tempListSekolah.forEach {
                        nameSekolah = it.name
                    }
                    val nowLocation = Tempat(
                        nameSekolah,
                        location.latitude.toString(),
                        location.longitude.toString()
                    )

                    val requestBody = ResponseAcoSorting.RequestBody(
                        tempListSekolah,
                        nowLocation,
                        nowLocation
                    )

                    service.acoSort(requestBody).enqueue(object : Callback<ResponseAcoSorting> {
                        override fun onResponse(
                            call: Call<ResponseAcoSorting>,
                            response: Response<ResponseAcoSorting>
                        ) {
                            if (response.isSuccessful) {
                                response.body()?.sorted?.forEach { sekolah ->
                                    val latLng = LatLng(
                                        sekolah.lat.toDouble(),
                                        sekolah.lng.toDouble()
                                    )
                                    val marker = MarkerOptions()
                                        .position(latLng).title(sekolah.name)
                                    mMap?.addMarker(marker)
                                }
                            }
                        }

                        override fun onFailure(call: Call<ResponseAcoSorting>, t: Throwable) {}
                    })
                }
            }

            override fun onFailure(call: Call<ResponGetAllSekolah>, t: Throwable) {}
        })
    }

    private fun showDialogPeta() {
        val myDialog = Dialog(this)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.activity_rpemberitahuan)
        myDialog.window!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.transparent_black_hex_11)))
        myDialog.setCancelable(true)
        myDialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        myDialog.show()

        //onclick button lanjut
        val btn_lanjut = myDialog.findViewById<Button>(R.id.btn_lanjut)
        btn_lanjut.setOnClickListener { view: View? ->
            Toast.makeText(
                this,
                "Okeyaaa",
                Toast.LENGTH_LONG
            ).show()
        }

        //onclick button sosialisasi
        val btn_sosialisasi = myDialog.findViewById<Button>(R.id.btn_sosialisasi)
        btn_sosialisasi.setOnClickListener { view: View? ->
//            Toast.makeText(this, "hello", Toast.LENGTH_LONG).show();
            startActivity(Intent(this, TambahSosialisasiActivity::class.java))
        }
    }

    private fun showListSelected() {
        val myDialog = Dialog(this)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.activity_list_selected_location)
        myDialog.window!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.transparent_black_hex_11)))
        myDialog.setCancelable(true)
        myDialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val text = myDialog.findViewById<TextView>(R.id.textView2)
        val set: MutableSet<String> = LinkedHashSet()
        set.addAll(list)
        list.clear()
        list.addAll(set)
        for (i in list.indices) {
            text.append(list[i])
            text.append("\n")
        }
        myDialog.show()

        //onclick button lanjut
        val btn_lanjut = myDialog.findViewById<Button>(R.id.btn_lanjut)
        btn_lanjut.setOnClickListener { view: View? ->
            Toast.makeText(
                this,
                "Okey",
                Toast.LENGTH_LONG
            ).show()
        }

        //onclick button sosialisasi
        val btn_sosialisasi = myDialog.findViewById<Button>(R.id.btn_sosialisasi)
        btn_sosialisasi.setOnClickListener { view: View? ->
//            Toast.makeText(this, "hello", Toast.LENGTH_LONG).show();
            startActivity(Intent(this, TambahSosialisasiActivity::class.java))
        }
    }
//nampilin garis
    private fun drawLine(destination: LatLng) {
        val stepList: MutableList<LatLng> = ArrayList()
        val options = PolylineOptions().apply {
            width(15f)
            color(
                resources.getColor(
                    R.color.path,
                    this@MapsActivity.theme
                )
            )
            geodesic(true)
            clickable(true)
            visible(true)
        }

        //polylineFinal?.remove()

        val apiKey = "AIzaSyA1MgLuZuyqR_OGY3ob3M52N46TDBRI_9k"
//google API untuk direction
        val url = "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=" + currentLocation?.latitude + "," + currentLocation?.longitude +
                "&destination=" + destination.latitude + "," + destination.longitude +
                "&mode=driving" +
                "&key=" + apiKey

        lifecycleScope.launchWhenCreated {
            locationViewModel.getDirection(url).collect {
                when (it) {
                    is Result.Loading -> {
                        Toast.makeText(this@MapsActivity, "Laoading", Toast.LENGTH_SHORT).show()
                        //binding.loading.show()
                    }
                    is Result.Success -> {
                        Toast.makeText(this@MapsActivity, "executed", Toast.LENGTH_SHORT).show()
                        Log.d("Status", it.data.toString())
                        //binding.loading.hide()
                        val directionResponseModel: DirectionResponseModel =
                            it.data as DirectionResponseModel

                        val routeModel: DirectionRouteModel =
                            directionResponseModel.directionRouteModels!![0]

                        val legModel: DirectionLegModel = routeModel.legs?.get(0)!!

                        val pattern: List<PatternItem>

                        pattern = listOf(Dash(30f))

                        options.pattern(pattern)
                        for (stepModel in legModel.steps!!) {
                            val decodeList = decode(stepModel.polyline?.points!!)
                            for (latLng in decodeList) {
                                stepList.add(
                                    LatLng(
                                        latLng.latitude,
                                        latLng.longitude
                                    )
                                )
                            }
                        }
                        options.addAll(stepList)
                        polylineFinal = mMap?.addPolyline(options)
                    }
                    is Result.Error -> {
                        Toast.makeText(this@MapsActivity, "error", Toast.LENGTH_SHORT).show()
                        Log.d("output", it.error)
                    }
                }
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun requestPermission() {
        if (Tools.hasLocationPermission(this)) {
            val task = fusedLocationProviderClient.lastLocation

            task.addOnSuccessListener { location ->
                if (location != null) {
                    isRequestingLocationUpdates = false
                    this.currentLocation = location

                    mMap?.let { map ->
                        val latLng = LatLng(location.latitude, location.longitude)
                        val zoom = 12.5f

                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
                        getAllLocation(latLng)
                    }

                } else {
                    startLocationUpdates()
                    isRequestingLocationUpdates = true
                }
            }
            return
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept to location permissions to use this app.",
                0,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept to location permissions to use this app.",
                0,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == 0) {
            return
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            requestPermission()
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationRequest = LocationRequest.create()

        locationRequest.apply {
            priority = Priority.PRIORITY_HIGH_ACCURACY
            interval = 2000L
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                for (location in locationResult.locations) {
                    if (location != null) {
                        currentLocation?.latitude
                        currentLocation?.longitude
                    }
                }
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback as LocationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        locationCallback?.let {
            fusedLocationProviderClient.removeLocationUpdates(
                it
            )
        }
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        Toast.makeText(this@MapsActivity, "error", Toast.LENGTH_SHORT).show()
        drawLine(LatLng(p0.position.latitude, p0.position.longitude))
        return false
    }

    override fun onPause() {
        super.onPause()
        if (!isRequestingLocationUpdates) stopLocationUpdates()
    }

    override fun onResume() {
        if (!isRequestingLocationUpdates) startLocationUpdates()
        super.onResume()
    }
}