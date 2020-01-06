package com.example.aqimonitor.view.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.aqimonitor.R
import com.example.aqimonitor.base.BaseActivity
import com.example.aqimonitor.databinding.ActivitySearchBinding
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.utils.Constant
import com.example.aqimonitor.view.adapter.MainAdapter
import com.example.aqimonitor.view.viewmodel.SearchViewModel
import com.google.android.libraries.places.widget.AutocompleteSupportFragment


const val TAG = "SearchActivity"
class SearchActivity: BaseActivity<ActivitySearchBinding, SearchViewModel>(){
    var autocompleteFragment: AutocompleteSupportFragment? = null
    var adapter: MainAdapter = MainAdapter(this)
    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun getClassViewModel(): Class<SearchViewModel> {
        return SearchViewModel::class.java
    }

    override fun initView() {
        initToolbar()
//        autocompleteFragment = binding?.searchFragment as AutocompleteSupportFragment
//        Places.initialize(this, getString(R.string.google_maps_key))
//        val placesClient = Places.createClient(this)
//       autocompleteFragment = supportFragmentManager.findFragmentById(R.id.search_fragment) as AutocompleteSupportFragment
//        autocompleteFragment?.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
//        autocompleteFragment?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
//            override fun onPlaceSelected(place: Place) {
////                Log.i(FragmentActivity.TAG, "Place: " + place.name + ", " + place.id)
//                Log.d(TAG, "Place: $place.name, $place.id");
//            }
//
//            override fun onError(p0: Status) {
//                Log.d(TAG, "error: ${p0.statusMessage}");
//            }
//        })
        binding?.tvSearch?.isEnabled = true
        /* binding?.etSearch?.addTextChangedListener(object : TextWatcher{
             override fun afterTextChanged(s: Editable?) {
                 if (s.isNullOrEmpty()) {
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
         })*/
        binding?.recyclerView?.adapter = adapter
        adapter.onFollowChange = {position, isFollowing ->
            viewModel?.setFollowItem(position, isFollowing)
        }


        viewModel?.listData?.observe(this, androidx.lifecycle.Observer { list: List<AQIModel> ->
            adapter.setListData(list)
        })
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.tv_search -> {
                Toast.makeText(this, "search", Toast.LENGTH_SHORT).show()
                search("hanoi")
            }
            R.id.iv_clear -> {
                binding?.etSearch?.setText("")
            }
            R.id.iv_back->{
                sendResult()
            }
        }
    }

    override fun onBackPressed() {
        sendResult()
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

    fun sendResult() {
        val resultIntent = Intent()
        resultIntent.putExtra(Constant.LIST_AQI_MODEL, viewModel?.getFollowedList())
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}