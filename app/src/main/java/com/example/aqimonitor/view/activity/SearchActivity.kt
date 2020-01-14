package com.example.aqimonitor.view.activity

import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.aqimonitor.R
import com.example.aqimonitor.base.BaseActivity
import com.example.aqimonitor.databinding.ActivitySearchBinding
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.view.adapter.MainAdapter
import com.example.aqimonitor.view.viewmodel.SearchViewModel
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.*


const val TAG = "SearchActivity"
class SearchActivity: BaseActivity<ActivitySearchBinding, SearchViewModel>(){
    var autocompleteFragment: AutocompleteSupportFragment? = null
    var listAqi: List<AQIModel>? = null
    var adapter: MainAdapter = MainAdapter(this)
    var keySearch: String = ""
    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun getClassViewModel(): Class<SearchViewModel> {
        return SearchViewModel::class.java
    }

    override fun initView() {
        initToolbar()
         binding?.etSearch?.addTextChangedListener(object : TextWatcher {
             override fun afterTextChanged(s: Editable?) {
                 keySearch = s.toString()
                 if (keySearch.isNullOrEmpty()) {
                     binding?.tvSearch?.isEnabled = false
                     binding?.ivClear?.visibility = View.INVISIBLE
                 } else {
                     binding?.tvSearch?.isEnabled = true
                     binding?.ivClear?.visibility = View.VISIBLE
                 }
             }

             override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
             }

             override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
             }
         })
        binding?.recyclerView?.adapter = adapter
        adapter.onFollowChange = {position, isFollowing ->
            viewModel?.setFollowItem(position, isFollowing)
            listAqi?.let {
                if (listAqi?.size!! > position) {
                    if (isFollowing) {
                        viewModel?.addItem(listAqi!![position])
                    }else {
                        viewModel?.removeItem(listAqi!![position])
                    }
                }
            }
        }

        viewModel?.listData?.observe(this, Observer { listData ->
            for (item in listData) {
                Log.d(TAG, "getAllData-id: ${item.id}, ${item.isFollow}, " );
            }
        })

        viewModel?.listDataSearch?.observe(this, androidx.lifecycle.Observer { list: List<AQIModel> ->
            listAqi = list
            for (item in list) {
                Log.d(TAG, "getAllData-id: ${item.id}, ${item.isFollow}, " );
            }
            adapter.setListData(list)
        })
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.tv_search -> {
                keySearch = keySearch.replace(" ", "")
                Log.d(TAG, ": " + keySearch);
                search(keySearch)
            }
            R.id.iv_clear -> {
                binding?.etSearch?.setText("")
            }
            R.id.iv_back->{
                finish()
            }
        }
    }

    private fun initToolbar() {
        binding?.toolbar?.ivBack?.visibility = View.VISIBLE
        binding?.toolbar?.tvTitle?.visibility = View.VISIBLE
        binding?.toolbar?.tvTitle?.text = "Search address"
        binding?.toolbar?.baseActivity = this
    }
    fun search(address: String) {
        viewModel?.searchCity(address)
    }
}