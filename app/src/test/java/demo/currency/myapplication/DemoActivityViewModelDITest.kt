package demo.currency.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import demo.currency.myapplication.model.CurrencyInfo
import demo.currency.myapplication.repositories.CurrencyInfoRepo
import demo.currency.myapplication.repositories.CurrencyInfoRepoImpl
import demo.currency.myapplication.viewModels.DemoActivityViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get


@ExperimentalCoroutinesApi
class DemoActivityViewModelDITest : KoinTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            module {
                single<CurrencyInfoRepo> { repo }
                single { DemoActivityViewModel(get(), mainCoroutineRule.dispatcher) }
            }
        )
    }

    private lateinit var viewModel: DemoActivityViewModel
    private val repo: CurrencyInfoRepoImpl = mockk()

    @Before
    fun setup() {
        viewModel = get()
        coEvery { repo.getCurrencyList() } returns arrayListOf(
            CurrencyInfo("1", "Crypto.com Chain", "CRO"),
            CurrencyInfo("3", "Ethereum", "ETH"),
            CurrencyInfo("5", "Bitcoin", "BTC"),
            CurrencyInfo("2", "USDC Coin", "USDC"),
            CurrencyInfo("4", "Binance Coin", "BNB")
        )
    }


    @Test
    fun testGetCurrencyInfo() = mainCoroutineRule.runBlockingTest {
        viewModel.fetchCurrencyList()
        coVerify(exactly = 1) { repo.getCurrencyList() }
        val actual = viewModel.currencyInfoList.getOrAwaitValue()
        assertEquals(5, actual.size)
        assertEquals("1", actual[0].id)
    }

    @Test
    fun testGetCurrencyInfo_concurrency() = mainCoroutineRule.runBlockingTest {
        pauseDispatcher()
        viewModel.fetchCurrencyList()
        viewModel.fetchCurrencyList()
        viewModel.fetchCurrencyList()
        coVerify(exactly = 0) { repo.getCurrencyList() }
        resumeDispatcher()
        coVerify(exactly = 1) { repo.getCurrencyList() }
        val actual = viewModel.currencyInfoList.getOrAwaitValue()
        assertEquals(5, actual.size)
        assertEquals("1", actual[0].id)
    }

    @Test
    fun testDisableSorting() = mainCoroutineRule.runBlockingTest {
        pauseDispatcher()
        viewModel.fetchCurrencyList()
        assertEquals(true, viewModel.disableSorting.value)
        resumeDispatcher()
        assertEquals(false, viewModel.disableSorting.value)
    }

    @Test
    fun testListEmptyToSort() = mainCoroutineRule.runBlockingTest {
        viewModel.sortCurrentList()
        try {
            viewModel.currencyInfoList.getOrAwaitValue()
            fail("Should have thrown Timeout Exception")
        } catch (e: Exception) {
            assertEquals("LiveData value was never set.", e.message)
        }
    }

    @Test
    fun testSortingByIdDesc() = mainCoroutineRule.runBlockingTest {
        viewModel.fetchCurrencyList()
        coVerify(exactly = 1) { repo.getCurrencyList() }

        viewModel.sortCurrentList()
        val actual = viewModel.currencyInfoList.getOrAwaitValue()
        assertEquals("5", actual[0].id)
        assertEquals("4", actual[1].id)
        assertEquals("3", actual[2].id)
        assertEquals("2", actual[3].id)
        assertEquals("1", actual[4].id)
    }


    @ExperimentalCoroutinesApi
    class MainCoroutineRule(val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
        TestWatcher(),
        TestCoroutineScope by TestCoroutineScope(dispatcher) {
        override fun starting(description: Description?) {
            super.starting(description)
            Dispatchers.setMain(dispatcher)
        }

        override fun finished(description: Description?) {
            super.finished(description)
            cleanupTestCoroutines()
            Dispatchers.resetMain()
        }
    }
}