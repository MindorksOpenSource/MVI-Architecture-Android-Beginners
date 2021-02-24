package com.mindorks.framework.mvi.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindorks.framework.mvi.R
import com.mindorks.framework.mvi.data.model.User
import com.mindorks.framework.mvi.ui.main.adapter.MainAdapter
import com.mindorks.framework.mvi.ui.main.intent.MainIntent
import com.mindorks.framework.mvi.ui.main.viewmodel.MainViewModel
import com.mindorks.framework.mvi.ui.main.viewstate.MainState
import com.mindorks.framework.mvi.util.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var mainViewModel: MainViewModel
    private var adapter = MainAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupClicks()
        mainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        mainViewModel.state.onEach { state -> handleIntent(state) }
            .launchIn(lifecycleScope)
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        recyclerView.adapter = adapter
    }

    private fun setupClicks() {
        buttonFetchUser.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchUser)
            }
        }
    }

    private fun handleIntent(state: MainState<List<User>>) {
        when (state) {
            is MainState.Idle -> Unit
            is MainState.Loading -> {
                buttonFetchUser.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }

            is MainState.Success -> {
                progressBar.visibility = View.GONE
                buttonFetchUser.visibility = View.GONE
                renderList(state.data)
            }
            is MainState.Error -> {
                progressBar.visibility = View.GONE
                buttonFetchUser.visibility = View.VISIBLE
                Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun renderList(users: List<User>?) {
        recyclerView.visibility = View.VISIBLE
        users?.let { adapter.addData(it) }
        adapter.notifyDataSetChanged()
    }
}
