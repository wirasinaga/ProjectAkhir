package com.example.mapsactivity.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mapsactivity.remote.repository.GoogleMapsRepository

class LocationViewModel : ViewModel() {
    private val repo = GoogleMapsRepository()
    fun getDirection(url: String) = repo.getDirection(url)
}