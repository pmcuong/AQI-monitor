package com.example.aqimonitor.view.activity

import android.app.Activity
import android.content.Intent
import android.location.Location
import android.util.Log
import android.view.View
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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapActivity : BaseActivity<ActivityMapBinding, MapViewModel>(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    var mLocationPermissionGranted = false
    var currentLocation: Location? = null
    var newLocation: Location? = null
    val TAG = "MapActivity"
    var mapFragment: SupportMapFragment? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_map
    }

    override fun getClassViewModel(): Class<MapViewModel> {
        return MapViewModel::class.java
    }

    override fun initView() {
        getExtra(intent)
        mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment?.getMapAsync(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_map -> {

            }
            R.id.tv_add -> {
                addNewObserveLocation(newLocation)
            }
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        val marker = p0?.addMarker(
            MarkerOptions()
                .position(LatLng(currentLocation?.latitude!!, currentLocation?.longitude!!))
                .draggable(true)
        )
        p0?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    currentLocation?.latitude!!,
                    currentLocation?.longitude!!
                ), 18f
            )
        )
        p0?.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(arg0: Marker) {
                // TODO Auto-generated method stub
                Log.d(
                    "System out",
                    "onMarkerDragStart..." + arg0.position.latitude + "..." + arg0.position.longitude
                )
            }

            override fun onMarkerDragEnd(arg0: Marker) {
                // TODO Auto-generated method stub
                newLocation = Location("")
                newLocation?.latitude = arg0.position.latitude
                newLocation?.longitude = arg0.position.longitude
                Log.d(
                    "System out",
                    "onMarkerDragEnd..." + arg0.position.latitude + "..." + arg0.position.longitude
                )

            }

            override fun onMarkerDrag(arg0: Marker) {
                // TODO Auto-generated method stub
                Log.i("System out", "onMarkerDrag...")
            }
        })

//        p0?.setOnMarkerClickListener(this)
    }

    fun getExtra(intent: Intent?) {
        currentLocation = intent?.getParcelableExtra(Constant.LOCATION)
    }

    fun addNewObserveLocation(location: Location?) {
        Log.d(TAG, ": " + location + "\n" + newLocation);
        val fullAddress = newLocation?.getFullAddressFromLatLnt(this)
        var aqiModel = AQIModel(
            newLocation?.latitude!!,
            newLocation?.longitude!!,
            nameAddress = fullAddress?.featureName,
            address = fullAddress?.getAddressLine(0),
            isCurrentPosition = true
        )
        val resultIntent = Intent()
        val data : ArrayList<AQIModel> = ArrayList()
        data.add(aqiModel)
        resultIntent.putExtra(Constant.LIST_AQI_MODEL, data)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}