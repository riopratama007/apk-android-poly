package com.poly.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.poly.BuildConfig.BASE_URL_IMAGE
import com.poly.R
import com.poly.utils.Constant.USER_ID
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("android:loadImage")
fun ImageView.loadImage(url: Any?) {
    try {
        val options = RequestOptions()
            .placeholder(getProgressDrawable(context))
            .error(R.color.bgColorBackButton)
        Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(BASE_URL_IMAGE + url)
            .into(this)
    } catch (e: Exception) {
        e.toString()
    }
}

@BindingAdapter("android:loadCircleImage")
fun ImageView.loadCircleImage(url: Any?) {
    try {
        val options = RequestOptions()
            .placeholder(getProgressDrawable(context))
            .error(R.drawable.user)
        Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(url)
            .circleCrop()
            .into(this)
    } catch (e: Exception) {
        e.toString()
    }
}

@BindingAdapter("android:loadThumbnail")
fun ImageView.loadThumbnail(url: String?) {
    try {
        if (url != null) {
            Glide.with(this)
                .load(Uri.fromFile(File(url)))
                .into(this)
        }
    } catch (e: Exception) {
        e.toString()
    }
}

@BindingAdapter("android:checkMyItemOrNot")
fun View.checkItem(myUserId: String?) {
    try {
        if (myUserId != null) {
            val userId = getPreference(context, USER_ID)
            if (userId == myUserId)
                this.visible()
            else
                this.gone()
        }
    } catch (e: Exception) {
        e.toString()
    }
}

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10F
        centerRadius = 50F
        start()
    }
}

@BindingAdapter("android:setImageResource")
fun ImageView.setImageResource(drawable: Int) {
    try {
        this.setImageResource(drawable)
    } catch (e: Exception) {
        e.toString()
    }
}

@SuppressLint("SimpleDateFormat", "SetTextI18n")
@BindingAdapter("android:formatDate")
fun TextView.formatDate(date: String?) {
    try {
        if (date != null) {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
            val cal = Calendar.getInstance()
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            cal.time = sdf.parse(date)
            this.text = "${cal.get(Calendar.DATE)}-${cal.get(Calendar.MONTH) + 1}-${cal.get(Calendar.YEAR)}"
        }
    } catch (e: Exception) {
        e.toString()
    }
}

@BindingAdapter("android:currency")
fun TextView.currency(value: Any?) {
    try {
        if (value != null) {
            this.text = currencyIDR().format(value.toString().toDouble())
        }
    } catch (e: Exception) {
        e.toString()
    }
}

@BindingAdapter("android:checkChecked")
fun checkChecked(checkBox: CheckBox, boolean: Boolean) {
    try {
        checkBox.isChecked = boolean
    } catch (e: Exception) {
        e.toString()
    }
}