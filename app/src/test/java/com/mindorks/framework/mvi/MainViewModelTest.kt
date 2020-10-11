package com.mindorks.framework.mvi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.mindorks.framework.mvi.data.api.ApiHelperImpl
import com.mindorks.framework.mvi.data.api.ApiService
import com.mindorks.framework.mvi.data.repository.MainRepository
import com.mindorks.framework.mvi.ui.main.viewmodel.MainViewModel
import com.mindorks.framework.mvi.ui.main.viewstate.MainState
import com.mindorks.framework.mvi.util.TestContextProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
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
    private lateinit var observer: Observer<MainState>

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        runBlockingTest {
            `when`(apiService.getUsers()).thenReturn(emptyList())
        }
        val apiHelper = ApiHelperImpl(apiService)
        val repository = MainRepository(apiHelper)
        val viewModel = MainViewModel(repository, TestContextProvider())
        viewModel.state.asLiveData().observeForever(observer)
        verify(observer).onChanged(MainState.Users(emptyList()))
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        runBlockingTest {
            `when`(apiService.getUsers()).thenThrow(RuntimeException())
        }
        val apiHelper = ApiHelperImpl(apiService)
        val repository = MainRepository(apiHelper)
        val viewModel = MainViewModel(repository, TestContextProvider())
        viewModel.state.asLiveData().observeForever(observer)
        verify(observer).onChanged(MainState.Error(null))
    }
}