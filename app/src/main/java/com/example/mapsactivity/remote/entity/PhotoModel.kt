package com.example.mapsactivity.remote.entity

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.squareup.moshi.Json

data class PhotoModel(
    @field:Json(name = "height")

    val height: Int?,

    @field:Json(name = "html_attributions")

    val htmlAttributions: List<String>?,

    @field:Json(name = "photo_reference")

    val photoReference: String?,

    @field:Json(name = "width")

    val width: Int?
) {
    companion object {

        fun loadImage(view: ImageView, image: String?) {
            Glide.with(view.context).load(image).into(view)
        }
    }
}