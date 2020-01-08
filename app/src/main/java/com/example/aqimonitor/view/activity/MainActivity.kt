package com.example.aqimonitor.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.example.aqimonitor.R
import com.example.aqimonitor.base.BaseActivity
import com.example.aqimonitor.databinding.ActivityMainBinding
import com.example.aqimonitor.extention.getFullAddressFromLatLnt
import com.example.aqimonitor.extention.setStatusBarGradient
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.utils.Constant
import com.example.aqimonitor.view.adapter.MainAdapter
import com.example.aqimonitor.view.viewmodel.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


const val REQUEST_CODE_ADD = 1030
const val REQUEST_CODE_MAP = 1031


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    val TAG = "MainActivity"
    var currentLocation: Location? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    var listAqiModel: List<AQIModel>? = null
    override fun onClick(v: View) {
        when (v?.id) {
            R.id.tv_add -> openSearchActivity()
            R.id.tv_map -> openMapActivity()
        }
    }

    var adapter: MainAdapter = MainAdapter(this)

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getClassViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    @SuppressLint("MissingPermission")
    override fun initView() {
        binding?.main = this
        setStatusBarGradient()
        binding?.recyclerView?.adapter = adapter


        viewModel?.listData?.observe(this, Observer { listData ->
            for (item in listData) {
                Log.d(TAG, "getAllData-id: ${item.id}, ${item.isFollow}, " );
            }
            listAqiModel = listData
            adapter.setListData(listData)
        })
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

//        adapter.onItemClick = object : OnItemClickedListener {
//            override fun onLongClick(position: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onClick(position: Int) {
//                Toast.makeText(applicationContext, adapter.data[position].content, Toast.LENGTH_SHORT).show()
////                viewModel?.setListData()
//            }
//        }

        adapter.onItemClick = { position, content ->
            //            Log.d("MainActivity", ": $content");
            openDetaiActivity(content)
//            viewModel?.searchCity("halong")
        }

        adapter.onFollowChange = {position, isFollowing ->
            viewModel?.removeItem(listAqiModel!![position])
        }
    }



    fun openDetaiActivity(aqiData: AQIModel) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("aqi_data", aqiData)
        startActivity(intent)
    }

    fun openMapActivity() {
        Toast.makeText(this, "Go to map", Toast.LENGTH_SHORT).show()
        startActivityForResult(
            Intent(this, MapActivity::class.java).putExtra(
                Constant.LOCATION,
                currentLocation
            ), REQUEST_CODE_MAP
        )
    }

    fun openSearchActivity() {
        startActivityForResult(
            Intent(this, SearchActivity::class.java), REQUEST_CODE_ADD
        )
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            return
        } else {
            Log.d(TAG, "getLocation: ${isGPSEnable()}, ${isNetworkEnable()}");
            if (!isGPSEnable()) {
                Toast.makeText(this, "Please turn on GPS", Toast.LENGTH_SHORT).show()
            } else if (!isNetworkEnable()) {
                Toast.makeText(
                    this,
                    "Please turn on mobile data or connect to wifi",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                fusedLocationClient?.getLastLocation()?.addOnSuccessListener(this) { location ->
                    // Got last known location. In some rare situations this can be null.
                    Log.d(TAG, "getLocation: $location");
                    if (location != null) {
                        currentLocation = location
                        if (isNetworkEnable()) {
                            val fullAddress = currentLocation?.getFullAddressFromLatLnt(this)
                            var aqiModel = AQIModel(1,
                                currentLocation?.latitude!!,
                                currentLocation?.longitude!!,
                                nameAddress = fullAddress?.featureName,
                                address = fullAddress?.getAddressLine(0),
                                isCurrentPosition = true
                            )
                            viewModel?.getNearestCurrentLocation(aqiModel)
                        } else {
                            Toast.makeText(
                                this,
                                "Please turn on mobile data or connect to wifi",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        // Logic to handle location object
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getLocation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: $requestCode, $resultCode")
        if (requestCode == REQUEST_CODE_ADD && resultCode == Activity.RESULT_OK) {
            val listAqiModel = data?.getParcelableArrayListExtra<AQIModel>(Constant.LIST_AQI_MODEL)
            Log.d(TAG, "onActivityResult: $listAqiModel");
            viewModel?.updateList(listAqiModel!!)
//            val newLocation = data?.getParcelableExtra<Location>(Constant.LOCATION)
//            newLocation?.let {
//                val fullAddress =
//                    getFullAddressFromLatLnt(newLocation?.latitude!!, newLocation?.longitude!!)
//                var aqiModel = AQIModel(
//                    newLocation?.latitude!!,
//                    newLocation?.longitude!!,
//                    nameAddress = fullAddress?.featureName,
//                    address = fullAddress?.getAddressLine(0),
//                    isCurrentPosition = false
//                )
//                viewModel?.getNearestCurrentLocation(aqiModel)
//            }

        } else if(requestCode == REQUEST_CODE_MAP && resultCode == Activity.RESULT_OK) {
            val listAqiModel = data?.getParcelableArrayListExtra<AQIModel>(Constant.LIST_AQI_MODEL)
            Log.d(TAG, "onActivityResult: $listAqiModel[0]");
            if (!listAqiModel.isNullOrEmpty()) {
                viewModel?.getNearestCurrentLocation(listAqiModel[0])
            }
        }
    }

    fun isNetworkEnable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val networkCapabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork);
                if (networkCapabilities != null) {
                    return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                }
            }
        }
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun isGPSEnable(): Boolean {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

}

