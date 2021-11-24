package com.stocktracking.app

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stocktracking.app.home.HomePageRepository
import com.stocktracking.app.home.HomePageViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner


@RunWith(PowerMockRunner::class)
@PrepareForTest(
    HomePageViewModel::class
)
class HomePageViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var homePageRepository: HomePageRepository


    @Mock
    lateinit var mockApplication: Application

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        PowerMockito.whenNew(HomePageRepository::class.java).withAnyArguments()
            .thenReturn(homePageRepository)
    }


    @Test
    fun load_testFetchStockData() {
        // Test whether repository being called or not with appropriate arguments
        val homePageViewModel = HomePageViewModel(mockApplication)

        homePageViewModel.getStocksData()
        verify(homePageRepository).getStocksData(homePageViewModel.stocksLiveData)
    }

    }