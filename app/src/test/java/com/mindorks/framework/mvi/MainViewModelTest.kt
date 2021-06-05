package com.mindorks.framework.mvi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mindorks.framework.mvi.data.api.ApiHelperImpl
import com.mindorks.framework.mvi.data.api.ApiService
import com.mindorks.framework.mvi.data.model.User
import com.mindorks.framework.mvi.data.repository.MainRepositoryImpl
import com.mindorks.framework.mvi.ui.main.viewmodel.MainViewModel
import com.mindorks.framework.mvi.ui.main.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
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

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        runBlockingTest {
            Mockito.`when`(apiService.getUsers()).thenReturn(emptyList())
        }
        val apiHelper = ApiHelperImpl(apiService)
        val repository = MainRepositoryImpl(apiHelper)
        assert(repository.state.value == MainState.Idle)

        coroutineScope.pauseDispatcher()

        val viewModel = MainViewModel(repository)

        assert(viewModel.state.value == MainState.Idle)

        coroutineScope.resumeDispatcher()

        assert((viewModel.state.value == MainState.Success<List<User>>(emptyList())))
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        runBlockingTest {
            Mockito.`when`(apiService.getUsers()).thenThrow(RuntimeException())
        }
        val apiHelper = ApiHelperImpl(apiService)
        val repository = MainRepositoryImpl(apiHelper)
        assert(repository.state.value == MainState.Idle)

        coroutineScope.pauseDispatcher()

        val viewModel = MainViewModel(repository)

        assert(viewModel.state.value == MainState.Idle)

        coroutineScope.resumeDispatcher()

        assert((viewModel.state.value == MainState.Error(null)))
    }
}