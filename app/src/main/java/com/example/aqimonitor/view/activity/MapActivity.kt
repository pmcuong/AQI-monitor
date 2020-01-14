package com.example.aqimonitor.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.aqimonitor.R
import com.example.aqimonitor.base.BaseActivity
import com.example.aqimonitor.databinding.ActivityMapBinding
import com.example.aqimonitor.extention.getFullAddressFromLatLnt
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.utils.Constant
import com.example.aqimonitor.view.viewmodel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import android.graphics.drawable.Drawable
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.graphics.Color
import android.widget.TextView
import com.example.aqimonitor.extention.getColorFromAqiIndex
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MapActivity : BaseActivity<ActivityMapBinding, MapViewModel>(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    var currentLocation: Location? = null
    var newLocation: Location? = null
    val TAG = "MapActivity"
    var mapFragment: SupportMapFragment? = null
    var listAqiModel: ArrayList<AQIModel> = ArrayList()
    var isGoogleMapReady: Boolean = false
    var marker:Marker? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_map
    }

    override fun getClassViewModel(): Class<MapViewModel> {
        return MapViewModel::class.java
    }

    override fun initView() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment?.getMapAsync(this)
        viewModel?.getAllData()?.observe(this, Observer {
            listAqiModel.clear()
            listAqiModel.addAll(it)
            if (isGoogleMapReady) {
                for (item in it) {
                    Log.d(TAG, ": ${item.address}, ${item.aqiIndex}");
                    createMarker(item)
                }
            }
        })
        viewModel?.getAllFromDB()
        getLocation()
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_add -> {
                onAddClick()
            }
            R.id.tv_ok -> {
                hideConfirmLayout()
                addNewObserveLocation(newLocation)
            }
            R.id.tv_cancel -> {
                hideConfirmLayout()
            }
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        map = p0!!
        isGoogleMapReady = true

        for (item in listAqiModel) {
            Log.d(TAG, ": ${item.address}, ${item.aqiIndex}");
            createMarker(item)
        }

        focusCurrentPosition()

        p0?.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(arg0: Marker) {
                // TODO Auto-generated method stub
                Log.d(
                    TAG,
                    "onMarkerDragStart..." + arg0.position.latitude + "..." + arg0.position.longitude
                )
            }

            override fun onMarkerDragEnd(arg0: Marker) {
                // TODO Auto-generated method stub
                newLocation = Location("")
                newLocation?.latitude = arg0.position.latitude
                newLocation?.longitude = arg0.position.longitude
                Log.d(
                    TAG,
                    "onMarkerDragEnd..." + arg0.position.latitude + "..." + arg0.position.longitude
                )

            }

            override fun onMarkerDrag(arg0: Marker) {
                // TODO Auto-generated method stub
                Log.i(TAG, "onMarkerDrag...")
            }
        })
    }

    fun addNewObserveLocation(location: Location?) {
        Log.d(TAG, ": $currentLocation, $location, " + (currentLocation === location));
        if (currentLocation != location) {
            val fullAddress = location?.getFullAddressFromLatLnt(this)
            var aqiModel = AQIModel(
                0,
                location?.latitude!!,
                location?.longitude!!,
                nameAddress = fullAddress?.featureName,
                address = fullAddress?.getAddressLine(0),
                isCurrentPosition = false,
                isFollow = true
            )
            viewModel?.addItem(aqiModel)
        }
    }

    protected fun createMarker(aqiModel: AQIModel): Marker {

        return map.addMarker(
            MarkerOptions()
                .position(LatLng(aqiModel.latitude!!, aqiModel.longitude!!))
                .anchor(0.5f, 0.5f)
                .title(aqiModel.address!!)
                .snippet(aqiModel.address!!)
                .icon(getMarkerBitmapFromView(aqiModel))
        )
    }

    private fun bitmapDescriptorFromVector(context: Context, @DrawableRes vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun onAddClick() {
        binding?.tvAdd?.visibility = View.GONE
        binding?.layoutConfirm?.visibility = View.VISIBLE
        marker = map?.addMarker(
            MarkerOptions()
                .position(LatLng(map.cameraPosition.target.latitude, map.cameraPosition.target.longitude))
                .draggable(true)
        )
        newLocation = Location("")
        newLocation?.latitude = map.cameraPosition.target.latitude
        newLocation?.longitude = map.cameraPosition.target.longitude
    }

    fun hideConfirmLayout() {
        binding?.layoutConfirm?.visibility = View.GONE
        binding?.tvAdd?.visibility = View.VISIBLE
        marker?.remove()
    }

    private fun getMarkerBitmapFromView(aqiModel: AQIModel): BitmapDescriptor {

        val customMarkerView =
            (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.layout_custom_marker,
                null
            )
        var tvAqi = customMarkerView.findViewById<TextView>(R.id.tv_aqi)
        tvAqi.setText(aqiModel.aqiIndex.toString())
        tvAqi.setBackgroundColor(getColorFromAqiIndex(aqiModel.aqiIndex!!))
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        customMarkerView.layout(
            0,
            0,
            customMarkerView.getMeasuredWidth(),
            customMarkerView.getMeasuredHeight()
        )
        customMarkerView.buildLayer()
        val returnedBitmap = Bitmap.createBitmap(
            customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(returnedBitmap)
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN)
        val drawable = customMarkerView.getBackground()
        if (drawable != null)
            drawable!!.draw(canvas)
        customMarkerView.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(returnedBitmap)
    }

    private fun getLocation() {
        fusedLocationClient?.getLastLocation()?.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                currentLocation = location
                if (isGoogleMapReady) {
                    focusCurrentPosition()
                }
            }

        }
    }

    fun focusCurrentPosition() {
        if (currentLocation != null && map != null) {
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        currentLocation?.latitude!!,
                        currentLocation?.longitude!!
                    ), 15f
                )
            )
        }
    }
}