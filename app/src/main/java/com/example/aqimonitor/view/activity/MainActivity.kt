package com.example.aqimonitor.view.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Location
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
    var isLoading = false

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        viewModel?.getAllData()?.observe(this, Observer { listData ->
            isLoading = false
            binding?.swipeRefreshLayout?.isRefreshing = isLoading
            listAqiModel = listData
            adapter.setListData(listData)
        })

        adapter.onItemClick = { position, content ->
            openDetaiActivity(content)
        }

        adapter.onFollowChange = { position, isFollowing ->
            viewModel?.removeItem(listAqiModel!![position])
        }

        binding?.swipeRefreshLayout?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            isLoading = true
            binding?.swipeRefreshLayout?.isRefreshing = isLoading
//            viewModel?.getAllDataAqiModel(listAqiModel!! as ArrayList<AQIModel>)
            viewModel?.updateAllData()
        })

        // check network if phone has internet connection
        // request api to update lastest aqi
        // else show list aqi from database

        getLocation()
        viewModel?.updateAllData()
    }

    private fun getLocation() {
        fusedLocationClient?.getLastLocation()?.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                currentLocation = location
                val fullAddress = currentLocation?.getFullAddressFromLatLnt(this)
                var aqiModel = AQIModel(
                    1,
                    currentLocation?.latitude!!,
                    currentLocation?.longitude!!,
                    nameAddress = fullAddress?.featureName,
                    address = fullAddress?.getAddressLine(0),
                    isCurrentPosition = true
                )
                viewModel?.addItem(aqiModel)
                viewModel?.getNearestCurrentLocation(aqiModel)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       if (requestCode == REQUEST_CODE_MAP && resultCode == Activity.RESULT_OK) {
            val listAqiModel = data?.getParcelableArrayListExtra<AQIModel>(Constant.LIST_AQI_MODEL)
            if (!listAqiModel.isNullOrEmpty()) {
                viewModel?.getNearestCurrentLocation(listAqiModel[0])
            }
        }
    }

    fun openDetaiActivity(aqiData: AQIModel) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("aqi_data", aqiData)
        startActivity(intent)
    }

    fun openMapActivity() {
        Toast.makeText(this, "Go to map", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MapActivity::class.java))
    }

    fun openSearchActivity() {
        startActivityForResult(
            Intent(this, SearchActivity::class.java), REQUEST_CODE_ADD
        )
    }
}

