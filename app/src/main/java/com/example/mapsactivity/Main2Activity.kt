package com.example.mapsactivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mapsactivity.data.MenuItem
import com.example.mapsactivity.databinding.ActivityMain2Binding
import com.example.mapsactivity.widget.SNavigationDrawer

//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v7.app.AppCompatActivity;
//import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
//import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;
class Main2Activity : AppCompatActivity() {
    private var binding: ActivityMain2Binding? = null
    private var sNavigationDrawer: SNavigationDrawer? = null
    var color1 = 0
    var fragmentClass: Class<*>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding!!.root)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        sNavigationDrawer = findViewById(R.id.navigationDrawer)
        val menuItems: MutableList<MenuItem> = ArrayList()
        // gambar menu
        menuItems.add(MenuItem("Beranda", R.drawable.news_bg))
        menuItems.add(MenuItem("Sekolah", R.drawable.sekolah))
        menuItems.add(MenuItem("Peta", R.drawable.peta))
        menuItems.add(MenuItem("Bukti", R.drawable.bukti))
        binding!!.navigationDrawer.menuItemList = menuItems
        tambahButton = binding!!.navigationDrawer.findViewById(R.id.menuTambah)
        fragmentClass = BerandaFragment::class.java
        try {
            fragment = (fragmentClass as Class<BerandaFragment>).newInstance() as Fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (fragment != null) {
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.frameLayout, fragment!!).commit()
        }
        binding!!.navigationDrawer.onMenuItemClickListener =
            SNavigationDrawer.OnMenuItemClickListener { position -> //klik slide slide menu
                println("Position $position")
                when (position) {
                    0 -> {
                        color1 = R.color.white
                        fragmentClass = BerandaFragment::class.java
                        tambahButton!!.visibility =
                            View.GONE
                    }
                    1 -> {
                        color1 = R.color.white
                        fragmentClass = SekolahFragment::class.java
                        tambahButton?.setVisibility(View.VISIBLE)
                        //fungsi klick menu add di page sekolah
                        tambahButton?.setOnClickListener(View.OnClickListener { //Open activity
                            //                                startActivity(new Intent(Main2Activity.this, TambahSekolahActivity.class));
                            startActivity(
                                Intent(
                                    this@Main2Activity,
                                    TambahSekolahActivity::class.java
                                )
                            )
                        })
                    }
                    2 -> {
                        color1 = R.color.white
                        fragmentClass = MessagesFragment::class.java
                        tambahButton?.visibility = View.GONE
                        //fungsi klick menu add di page peta
                        tambahButton?.setOnClickListener(View.OnClickListener {
                            //Open activity
                        })
                    }
                    3 -> {
                        color1 = R.color.white
                        fragmentClass = BuktiFragment::class.java
                        tambahButton?.visibility = View.GONE
                        //fungsi click menu add di page bukti
                        tambahButton?.setOnClickListener(View.OnClickListener {
                            //Open Activity
                            //                                startActivity(new Intent(Main2Activity.this, TambahSosialisasiActivity.class));
                        })
                    }
                }
                binding!!.navigationDrawer.drawerListener = object : SNavigationDrawer.DrawerListener {
                    override fun onDrawerOpened() {}
                    override fun onDrawerOpening() {}
                    override fun onDrawerClosing() {
                        println("Drawer closed")
                        try {
                            fragment = fragmentClass!!.newInstance() as Fragment
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        if (fragment != null) {
                            val fragmentManager = supportFragmentManager
                            fragmentManager.beginTransaction().setCustomAnimations(
                                android.R.animator.fade_in,
                                android.R.animator.fade_out
                            ).replace(R.id.frameLayout, fragment!!).commit()
                        }
                    }

                    override fun onDrawerClosed() {}
                    override fun onDrawerStateChanged(newState: Int) {
                        println("State $newState")
                    }
                }
            }
    }

    companion object {
        var fragment: Fragment? = null

        @SuppressLint("StaticFieldLeak")
        var tambahButton: ImageView? = null
    }
}