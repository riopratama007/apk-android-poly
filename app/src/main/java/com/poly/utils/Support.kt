package com.poly.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.poly.BuildConfig
import com.poly.BuildConfig.BASE_URL_IMAGE
import com.poly.R
import com.poly.ui.fragment.auth.ProgressDialogFragment
import java.io.ByteArrayOutputStream
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private lateinit var mOptionDialogFragment: ProgressDialogFragment
private lateinit var preferenceManager: PreferenceManager

fun Activity.fullscreen() {
    this.window.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
}

fun Activity.notFullscreen() {
    this.window.clearFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
    )
}

fun Activity.setWindowColor(color: Int) {
    this.window.statusBarColor = ContextCompat.getColor(applicationContext, color)
}

fun Fragment.showLoading() {
    mOptionDialogFragment = ProgressDialogFragment()
    val mFragmentManager = this.childFragmentManager
    mOptionDialogFragment.show(
        mFragmentManager,
        ProgressDialogFragment::class.java.simpleName
    )
}

fun hideLoading() {
    mOptionDialogFragment.dismiss()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun navigateUp(view: View?) {
    val controller = view?.let { Navigation.findNavController(view) }
    controller?.navigateUp()
}

fun EditText?.setValuePrice() {
    this?.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            this@setValuePrice.removeTextChangedListener(this)

            try {
                var originalString = s.toString()
                if (originalString.contains(",")) {
                    originalString = originalString.replace(",".toRegex(), "")
                }
                val longVal = originalString.toLong()
                val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
                formatter.applyPattern("#,###,###,###")
                val formattedString: String = formatter.format(longVal)
                this@setValuePrice.setText(formattedString)
                this@setValuePrice.text?.length?.let {
                    this@setValuePrice.setSelection(it)
                }
            } catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
            }
            this@setValuePrice.addTextChangedListener(this)
        }
    })
}

fun EditText?.getValuePrice(): String {
    return this?.text?.toString()?.replace(",", "").toString()
}

fun addCountryCodeInPhoneNumber(phoneNumber: String): String {
    var output = ""
    output = when {
        phoneNumber.startsWith("62") -> {
            phoneNumber.replaceFirst("62", "+62")
        }
        phoneNumber.startsWith("0") -> {
            phoneNumber.replaceFirst("0", "+62")
        }
        else -> {
            phoneNumber
        }
    }
    Log.d("PhoneNumber", "onViewCreated: $output")

    return output
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun putPreference(context: Context, key: String, value: String) {
    preferenceManager = PreferenceManager(context)
    preferenceManager.putString(key, value)
}

fun getPreference(context: Context, key: String): String {
    preferenceManager = PreferenceManager(context)
    return preferenceManager.getString(key)
}

fun clearPreference(context: Context) {
    preferenceManager = PreferenceManager(context)
    return preferenceManager.clearPreferences()
}

fun clearSinglePreference(context: Context, key: String) {
    preferenceManager = PreferenceManager(context)
    return preferenceManager.removeSinglePreferences(key)
}

fun encodeImage(bm: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val b = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun ImageView.loadImageFromUri(uri: Uri?) {
    Glide.with(context)
        .load(uri)
        .into(this)
}

fun ImageView.loadCircleImageFromUrl(url: Uri?) {
    Glide.with(context)
        .load(url)
        .circleCrop()
        .into(this)
}

@SuppressLint("SimpleDateFormat")
fun getLocalDateTime(): String {
    val date = Calendar.getInstance().time
    val formatDate = SimpleDateFormat("dd-MM-yyyy")
    return formatDate.format(date)
}

fun currencyIDR(): NumberFormat {
    val locale = Locale("in", "ID")
    return NumberFormat.getCurrencyInstance(locale)
}

fun ImageView.loadImagePost(path: String, url: String) {
    try {
        val options = RequestOptions()
            .placeholder(getProgressDrawable(context))
            .error(R.color.bgColorBackButton)
        Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(BASE_URL_IMAGE + path + url)
            .into(this)
    } catch (e: Exception) {
        e.toString()
    }
}