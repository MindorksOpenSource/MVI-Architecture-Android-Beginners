package com.mindorks.framework.mvi.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindorks.framework.mvi.R
import com.mindorks.framework.mvi.base.ViewModelFactory
import com.mindorks.framework.mvi.data.api.ApiHelper
import com.mindorks.framework.mvi.data.api.RetrofitBuilder
import com.mindorks.framework.mvi.ui.main.adapter.MainAdapter
import com.mindorks.framework.mvi.ui.main.dataholder.MainDataHolder
import com.mindorks.framework.mvi.ui.main.viewevent.MainEvents
import com.mindorks.framework.mvi.ui.main.viewmodel.MainViewModel
import com.mindorks.mvibeginner.util.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    val TAG = "MainActivity"
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupViewEvents()
        observeViewModel()
    }


    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupViewEvents() {
        mainViewModel.setEventValue(MainEvents.UsersLoadEvent)

    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun renderList(mainDataHolder: MainDataHolder) {
        Log.d(TAG,mainDataHolder.toString())
        mainDataHolder.users?.let { listOfUsers -> adapter.addData(listOfUsers) }
        adapter.notifyDataSetChanged()
    }

    private fun observeViewModel() {
        mainViewModel.dataOutput.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

}
