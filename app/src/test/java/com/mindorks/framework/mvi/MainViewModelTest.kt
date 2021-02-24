package com.mindorks.framework.mvi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.mindorks.framework.mvi.data.api.ApiHelperImpl
import com.mindorks.framework.mvi.data.api.ApiService
import com.mindorks.framework.mvi.data.model.User
import com.mindorks.framework.mvi.data.repository.MainRepository
import com.mindorks.framework.mvi.ui.main.viewmodel.MainViewModel
import com.mindorks.framework.mvi.ui.main.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @Mock
    lateinit var apiService: ApiService

    @Mock
    private lateinit var observer: Observer<MainState<List<User>>>

    @Captor
    private lateinit var captor: ArgumentCaptor<MainState<List<User>>>

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        runBlockingTest {
            `when`(apiService.getUsers()).thenReturn(emptyList())
        }
        val apiHelper = ApiHelperImpl(apiService)
        val repository = MainRepository(apiHelper).apply {
            state.asLiveData().observeForever(observer)
        }
        val viewModel = MainViewModel(repository)
        try {
            verify(observer, times(3)).onChanged(captor.capture())
            verify(observer).onChanged(MainState.Idle)
            verify(observer).onChanged(MainState.Loading)
            verify(observer).onChanged(MainState.Success(emptyList()))
        } finally {
            viewModel.state.asLiveData().removeObserver(observer)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        runBlockingTest {
            `when`(apiService.getUsers()).thenThrow(RuntimeException())
        }
        val apiHelper = ApiHelperImpl(apiService)
        val repository = MainRepository(apiHelper).apply {
            state.asLiveData().observeForever(observer)
        }
        val viewModel = MainViewModel(repository)
        try {
            verify(observer, times(3)).onChanged(captor.capture())
            verify(observer).onChanged(MainState.Idle)
            verify(observer).onChanged(MainState.Loading)
            verify(observer).onChanged(MainState.Error(null))
        } finally {
            viewModel.state.asLiveData().removeObserver(observer)
        }
    }
}