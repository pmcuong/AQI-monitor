package com.example.aqimonitor.extention

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import com.example.aqimonitor.R


fun Context.showToast(content: String) {
    Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
}

fun String.toDateShow(): String {
    return "text"
}

fun Context.getColorFromAqiIndex(aqiIndex: Int): Int {
    return when (aqiIndex) {
        in 0..50 -> getColor(this, R.color.colorGood)
        in 51..100 -> getColor(this, R.color.colorModerate)
        in 101..150 -> getColor(this, R.color.colorUnhealthyForSensitiveGroup)
        in 151..200 -> getColor(this, R.color.colorUnhealthy)
        in 201..300 -> getColor(this, R.color.colorVeryUnhealthy)
        in 301..500 -> getColor(this, R.color.colorHazadous)
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

fun Context.getNameOfAqiLevel(aqiIndex: Int): String {
    return when (aqiIndex) {
        in 0..50 -> getString(R.string.str_moderate)
        in 51..100 -> getString(R.string.str_moderate)
        in 101..150 -> getString(R.string.str_unhealthy_for_sensitive_group)
        in 151..200 -> getString(R.string.str_unhealthy)
        in 201..300 -> getString(R.string.str_very_unhealthy)
        in 301..500 -> getString(R.string.str_hazadous)
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

fun ImageView.setFaceFromAqiIndex(aqiIndex: Int) {
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