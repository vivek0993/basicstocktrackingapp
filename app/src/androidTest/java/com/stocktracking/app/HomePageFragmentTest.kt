package com.stocktracking.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4
import com.stocktracking.app.home.HomePageFragment
import com.stocktracking.app.home.HomePageViewModel
import com.stocktracking.app.home.data.StockData
import com.stocktracking.app.home.data.StocksListDataClass
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@RunWith(AndroidJUnit4::class)
class HomePageFragmentTest {

    @Mock
    lateinit var mockViewModelProvider: ViewModelProvider

    @Mock
    lateinit var mockHomePageViewModel: HomePageViewModel

    @Mock
    lateinit var mockNavController: NavController

    private lateinit var fragmentScenario: FragmentScenario<TestHomePageFragment>

    private val homePageFragment = TestHomePageFragment()

    val stocksLiveData = MutableLiveData<List<StockData>>()

    private val homePageSuccessResponse = StocksObjectMapper.getObjectFromJson(
        STOCK_DATA_SUCCESS_RESPONSE, StocksListDataClass::class.java
    ) as StocksListDataClass?

    private val homePageFailureResponse = StocksObjectMapper.getObjectFromJson(
        STOCK_DATA_FAILURE_RESPONSE, StocksListDataClass::class.java
    ) as StocksListDataClass?


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(mockViewModelProvider.get(HomePageViewModel::class.java))
            .thenReturn(mockHomePageViewModel)
        homePageFragment.mockViewModelProvider = mockViewModelProvider
        homePageFragment.mockNavigationController = mockNavController

        Mockito.`when`(mockHomePageViewModel.stocksLiveData)
            .thenReturn(stocksLiveData)


    }

    fun launchFragment() {
        fragmentScenario =
            launchFragmentInContainer<TestHomePageFragment>(null, 0) {
                homePageFragment.also {
                    it.viewLifecycleOwnerLiveData.observeForever { viewLifeCycleOwner ->
                        if (viewLifeCycleOwner != null) {
                            // The fragment’s view has just been created

                            Navigation.setViewNavController(it.requireView(), mockNavController)
                        }
                    }
                }
            }

        fragmentScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
    }
    @Test
    fun whenSuccessApiResponse_verifyUI() {
        launchFragment()
        Mockito.verify(mockHomePageViewModel).getStocksData()
        stocksLiveData.postValue(homePageSuccessResponse?.data)

        onData(anything()).inAdapterView(withId(R.id.lv_stocks_list))
            .atPosition(0)
            .onChildView(withId(R.id.tv_sid))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onData(anything()).inAdapterView(withId(R.id.lv_stocks_list))
            .atPosition(0)
            .onChildView(withId(R.id.tv_sid))
            .check(ViewAssertions.matches(ViewMatchers.withText("RELI")))

    }

    @Test
    fun whenFailureApiResponse_verifyUI() {
        launchFragment()
        Mockito.verify(mockHomePageViewModel).getStocksData()
        stocksLiveData.postValue(homePageFailureResponse?.data)

        Espresso.onView(withId(R.id.lv_stocks_list))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))

    }

}


class TestHomePageFragment() :
    HomePageFragment() {
    lateinit var mockViewModelProvider: ViewModelProvider
    lateinit var mockNavigationController:NavController
    override fun getViewModelProvider(fragment: Fragment, factory:ViewModelProvider.Factory?) : ViewModelProvider {
        return mockViewModelProvider

    }
}