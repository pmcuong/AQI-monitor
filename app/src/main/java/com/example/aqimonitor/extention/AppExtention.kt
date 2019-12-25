package com.example.aqimonitor.extention

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Build
import android.view.View
import android.widget.Toast
import com.example.aqimonitor.R
import android.graphics.drawable.shapes.RoundRectShape
import android.widget.ImageView


fun showToast(context: Context, content: String) {
    Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
}

fun String.toDateShow(): String {
    return "text"
}

fun getColorFromAqiIndex(mContext: Context, aqiIndex: Int): Int {
    return when {
        aqiIndex >= 0 && aqiIndex <= 50 -> getColor(mContext, R.color.colorGood)
        aqiIndex >= 51 && aqiIndex <= 100 -> getColor(mContext, R.color.colorModerate)
        aqiIndex >= 101 && aqiIndex <= 150 -> getColor(
            mContext,
            R.color.colorUnhealthyForSensitiveGroup
        )
        aqiIndex >= 151 && aqiIndex <= 200 -> getColor(mContext, R.color.colorUnhealthy)
        aqiIndex >= 201 && aqiIndex <= 300 -> getColor(mContext, R.color.colorVeryUnhealthy)
        aqiIndex >= 301 && aqiIndex <= 500 -> getColor(mContext, R.color.colorHazadous)
        else -> getColor(mContext, R.color.colorGood)
    }
}

fun getColor(mContext: Context, resColorId: Int): Int {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        return mContext.resources.getColor(resColorId, null)
    } else {
        return mContext.resources.getColor(resColorId)
    }
}

fun getNameOfAqiLevel(mContext: Context, aqiIndex: Int): String {
    return when {
        aqiIndex >= 0 && aqiIndex <= 50 -> mContext.getString(R.string.str_moderate)
        aqiIndex >= 51 && aqiIndex <= 100 -> mContext.getString(R.string.str_moderate)
        aqiIndex >= 101 && aqiIndex <= 150 -> mContext.getString(R.string.str_unhealthy_for_sensitive_group)
        aqiIndex >= 151 && aqiIndex <= 200 -> mContext.getString(R.string.str_unhealthy)
        aqiIndex >= 201 && aqiIndex <= 300 -> mContext.getString(R.string.str_very_unhealthy)
        aqiIndex >= 301 && aqiIndex <= 500 -> mContext.getString(R.string.str_hazadous)
        else -> ""
    }
}

fun View.setGradientColor(endColor: Int) {
    val gradient =
        GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(endColor, Color.WHITE))
    gradient.shape = GradientDrawable.RECTANGLE
    gradient.cornerRadius = 10f
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        setBackground(gradient)
    } else {
        setBackgroundDrawable(gradient)
    }
}

fun View.setBorderBackground(mContext: Context, color: Int) {
    var shapeDrawable: GradientDrawable? = (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        mContext.resources.getDrawable(R.drawable.background_aqi_item, null)

    } else {
        TODO("VERSION.SDK_INT < LOLLIPOP")
        mContext.resources.getDrawable(R.drawable.background_aqi_item)
    }) as GradientDrawable?;
    shapeDrawable?.setStroke(15, color)
//
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        setBackground(shapeDrawable)
    } else {
        setBackgroundDrawable(shapeDrawable)
    }
}

fun ImageView.setFaceFromAqiIndex(aqiIndex: Int) {
    val imgResId = when {
        aqiIndex >= 0 && aqiIndex <= 50 -> R.drawable.ic_good_face
        aqiIndex >= 51 && aqiIndex <= 100 -> R.drawable.ic_moderate_face
        aqiIndex >= 101 && aqiIndex <= 150 -> R.drawable.ic_unhealthy_sensitive_group_face
        aqiIndex >= 151 && aqiIndex <= 200 -> R.drawable.ic_unhealthy_face
        aqiIndex >= 201 && aqiIndex <= 300 -> R.drawable.ic_very_unhealthy_face
        aqiIndex >= 301 && aqiIndex <= 500 -> R.drawable.ic_hazadous_face
        else -> R.drawable.ic_good_face
    }
    setImageResource(imgResId)
}