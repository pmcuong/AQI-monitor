package com.example.aqimonitor.extention

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.text.format.DateFormat
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import com.example.aqimonitor.R
import com.example.aqimonitor.model.AQIModel
import java.io.IOException
import java.util.*




fun Context.showToast(content: String) {
    Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
}

fun String.toDateShow(): String {
    return "text"
}

fun Context.getColorFromAqiIndex(aqiIndex: Float): Int {
    return when (aqiIndex) {
        in 0f..50f -> getColor(this, R.color.colorGood)
        in 51f..100f -> getColor(this, R.color.colorModerate)
        in 101f..150f -> getColor(this, R.color.colorUnhealthyForSensitiveGroup)
        in 151f..200f -> getColor(this, R.color.colorUnhealthy)
        in 201f..300f -> getColor(this, R.color.colorVeryUnhealthy)
        in 301f..500f -> getColor(this, R.color.colorHazadous)
        else -> getColor(this, R.color.colorGood)
    }
}

fun Context.getColor(resColorId: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.resources.getColor(resColorId, null)
    } else {
        this.resources.getColor(resColorId)
    }
}

fun Context.getNameOfAqiLevel(aqiIndex: Float): String {
    return when (aqiIndex) {
        in 0f..50f -> getString(R.string.str_moderate)
        in 51f..100f -> getString(R.string.str_moderate)
        in 101f..150f -> getString(R.string.str_unhealthy_for_sensitive_group)
        in 151f..200f -> getString(R.string.str_unhealthy)
        in 201f..300f -> getString(R.string.str_very_unhealthy)
        in 301f..500f -> getString(R.string.str_hazadous)
        else -> ""
    }
}

fun View.setGradientColor(endColor: Int) {
    val gradient =
        GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(endColor, Color.WHITE))
    gradient.shape = GradientDrawable.RECTANGLE
    gradient.cornerRadius = 10f
    background = gradient
}

fun View.setBorderBackground(mContext: Context, color: Int) {
    val shapeDrawable: GradientDrawable? = (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        mContext.resources.getDrawable(R.drawable.background_aqi_item, null)
    } else {
        mContext.resources.getDrawable(R.drawable.background_aqi_item)
    }) as GradientDrawable?
    shapeDrawable?.setStroke(15, color)
    background = shapeDrawable
}

fun ImageView.setFaceFromAqiIndex(aqiIndex: Float) {
    val imgResId = when (aqiIndex) {
        in 0..50 -> R.drawable.ic_good_face
        in 51..100 -> R.drawable.ic_moderate_face
        in 101..150 -> R.drawable.ic_unhealthy_sensitive_group_face
        in 151..200 -> R.drawable.ic_unhealthy_face
        in 201..300 -> R.drawable.ic_very_unhealthy_face
        in 301..500 -> R.drawable.ic_hazadous_face
        else -> R.drawable.ic_good_face
    }
    setImageResource(imgResId)
}

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun Activity.setStatusBarGradient() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val background = resources.getDrawable(R.drawable.background_toolbar, null)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(android.R.color.transparent)
        window.navigationBarColor = resources.getColor(android.R.color.transparent)
        window.setBackgroundDrawable(background)
    }
}

fun TextView.setCurrentTime() {
    text = DateFormat.format("EEEE, MMMM dd, yyyy", Date(System.currentTimeMillis()))
}

fun Location.getLatLngInString(): String {
    return String.format("%.2f", latitude) + ";" + String.format("%.2f", longitude)
}

fun AQIModel.getLatLngInString(): String {
    return String.format("%.2f", latitude) + ";" + String.format("%.2f", longitude)
}

fun Location.getFullAddressFromLatLnt(context: Context): Address? {
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        var listAddress: List<Address?>? = null
        listAddress = geocoder.getFromLocation(latitude, longitude, 1)
        if (listAddress != null && listAddress.size > 0) {
            return listAddress[0]
        } else {
            return null
        }
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}