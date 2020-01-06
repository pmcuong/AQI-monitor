package com.example.aqimonitor.view.activity

import android.Manifest
import android.content.Intent
import android.view.View
import com.example.aqimonitor.R
import com.example.aqimonitor.base.BaseActivity
import com.example.aqimonitor.databinding.ActivityPermissionBinding
import com.example.aqimonitor.view.viewmodel.DetailViewModel
import pub.devrel.easypermissions.EasyPermissions

class PermissionActivity : BaseActivity<ActivityPermissionBinding, DetailViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_permission
    }

    override fun getClassViewModel(): Class<DetailViewModel> {
        return DetailViewModel::class.java
    }

    override fun initView() {

    }

    override fun onClick(v: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResume() {
        super.onResume()
        enableMyLocation()
    }

    private fun enableMyLocation() {
        if (hasLocationPermission()) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            EasyPermissions.requestPermissions(
                this, getString(R.string.location),
                Companion.REQUEST_CODE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private fun hasLocationPermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    companion object {
        const val REQUEST_CODE_LOCATION = 2020
    }
}