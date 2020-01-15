package com.example.aqimonitor.view.activity

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.aqimonitor.R
import com.example.aqimonitor.base.BaseActivity
import com.example.aqimonitor.databinding.ActivitySearchBinding
import com.example.aqimonitor.extention.hideKeyboardFrom
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.view.adapter.MainAdapter
import com.example.aqimonitor.view.viewmodel.SearchViewModel
import com.google.android.libraries.places.widget.AutocompleteSupportFragment


const val TAG = "SearchActivity"

class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>() {
    var autocompleteFragment: AutocompleteSupportFragment? = null
    var listAqi = ArrayList<AQIModel>()
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
//        binding?.swipeRefreshLayout?.isEnabled = false
        binding?.etSearch?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                keySearch = s.toString()
                if (keySearch.isNullOrEmpty()) {
                    binding?.ivClear?.visibility = View.INVISIBLE
                } else {
                    binding?.ivClear?.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding?.etSearch?.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(keySearch)
                    binding?.root?.hideKeyboardFrom(applicationContext)
                    return true
                }
                return false
            }
        })

        binding?.recyclerView?.adapter = adapter
        adapter.onFollowChange = { position, isFollowing ->
            viewModel?.setFollowItem(position, isFollowing)
            listAqi?.let {
                if (listAqi?.size!! > position) {
                    if (isFollowing) {
                        viewModel?.addItem(listAqi!![position])
                    } else {
                        viewModel?.removeItem(listAqi!![position])
                    }
                }
            }
        }

        binding?.swipeRefreshLayout?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            if (!listAqi.isNullOrEmpty()) {
                binding?.swipeRefreshLayout?.isRefreshing = true
                viewModel?.getAqiFromList(listAqi)
            } else {
                binding?.swipeRefreshLayout?.isRefreshing = false
            }
        })

        viewModel?.listDataSearch?.observe(
            this,
            androidx.lifecycle.Observer { list: List<AQIModel> ->
                listAqi.clear()
                listAqi.addAll(list)
                adapter.setListData(listAqi)
                binding?.swipeRefreshLayout?.isRefreshing = false

            })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_clear -> {
                binding?.etSearch?.setText("")
            }
            R.id.iv_back -> {
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
        binding?.swipeRefreshLayout?.isRefreshing = true
    }
}