package com.poly.utils

import android.content.Context
import com.poly.R
import com.poly.core.data.source.remote.response.Moment

object Datas {
    private const val URL_IMAGE_ARTICLE = "https://minio.kinikumuda.id/kitasehat-dev/article/"
    const val URL_TERM_CONDITION = "https://api.dev.kitasehat.kinikumuda.id/terms-and-conditions"
    const val URL_PRIVACY_POLICY = "https://api.dev.kitasehat.kinikumuda.id/privacy-policy"

    data class Slider(
        val title: String,
        val description: String,
        val image: Int,
    )

    fun Context.generateDataSlider(): List<Slider> {
        val sliders = ArrayList<Slider>()
        sliders.clear()
        sliders.add(
            Slider(
                getString(R.string.slider_title_one),
                getString(R.string.slider_description_one),
                R.drawable.img_slider_one
            )
        )

        sliders.add(
            Slider(
                getString(R.string.slider_title_two),
                getString(R.string.slider_description_two),
                R.drawable.img_slider_two
            )
        )

        sliders.add(
            Slider(
                getString(R.string.slider_title_three),
                getString(R.string.slider_description_three),
                R.drawable.img_slider_three
            )
        )

        return sliders
    }

    fun generateDataSliderMoment(): List<Moment.Response.Data.PostFiles> {
        val sliders = ArrayList<Moment.Response.Data.PostFiles>()
        sliders.clear()
        sliders.add(
            Moment.Response.Data.PostFiles(
                "b9b5af2b-83a2-4037-83b9-6ac3139941ab",
                "1f32ef35dddc1895818e708ad485a95d.jpeg",
                "IMAGE",
                "8035f58e-8d57-4b7b-b4cd-949191e4cdc3"
            )
        )

        sliders.add(
            Moment.Response.Data.PostFiles(
                "b9b5af2b-83a2-4037-83b9-6ac3139941ab",
                "b67384bc97b0748512dccacd3eb7b522.mp4",
                "VIDEO",
                "8035f58e-8d57-4b7b-b4cd-949191e4cdc3"
            )
        )

        sliders.add(
            Moment.Response.Data.PostFiles(
                "b9b5af2b-83a2-4037-83b9-6ac3139941ab",
                "006f0fde433211b11654ffcc63036ff7.mp4",
                "VIDEO",
                "8035f58e-8d57-4b7b-b4cd-949191e4cdc3"
            )
        )

        return sliders
    }
}