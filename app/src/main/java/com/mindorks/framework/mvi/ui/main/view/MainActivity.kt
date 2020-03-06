package com.mindorks.framework.mvi.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindorks.framework.mvi.R
import com.mindorks.framework.mvi.data.api.ApiHelper
import com.mindorks.framework.mvi.data.api.ApiServiceImpl
import com.mindorks.framework.mvi.ui.base.ViewModelFactory
import com.mindorks.framework.mvi.ui.main.adapter.MainAdapter
import com.mindorks.framework.mvi.ui.main.viewevent.MainEvent
import com.mindorks.framework.mvi.ui.main.viewmodel.MainViewModel
import com.mindorks.framework.mvi.util.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupViewEvents()
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
        mainViewModel.setEventValue(MainEvent.UsersLoadEvent)

    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }

    private fun observeViewModel() {
        mainViewModel.dataValueState.observe(this, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    mainViewModel.loadingValue(false)
                    result.data?.users.let {
                        it?.let { users -> mainViewModel.loadUser(users) }
                    }
                }
                Status.LOADING -> {
                    mainViewModel.loadingValue(true)
                    progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    mainViewModel.loadingValue(false)
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        mainViewModel.viewState.observe(this, Observer { it ->
            it.users.let { users ->
                recyclerView.visibility = View.VISIBLE
                users.let { listOfUsers -> listOfUsers?.let { adapter.addData(it) } }
                adapter.notifyDataSetChanged()
            }
        })
        mainViewModel.loadingValue.observe(this, Observer { value ->
            if (value) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.GONE
        })
    }
}
