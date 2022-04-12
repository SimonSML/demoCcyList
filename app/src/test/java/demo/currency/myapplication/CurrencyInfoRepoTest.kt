package demo.currency.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import demo.currency.myapplication.db.CurrencyInfoDao
import demo.currency.myapplication.model.CurrencyInfo
import demo.currency.myapplication.repositories.CurrencyInfoRepoImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class CurrencyInfoRepoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repo: CurrencyInfoRepoImpl
    private val dao: CurrencyInfoDao = mockk()
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        repo = CurrencyInfoRepoImpl(dao, testDispatcher)
        coEvery { dao.getCurrencyInfoList() } returns arrayListOf(
            CurrencyInfo("1", "Crypto.com Chain", "CRO"),
            CurrencyInfo("3", "Ethereum", "ETH"),
            CurrencyInfo("5", "Bitcoin", "BTC"),
            CurrencyInfo("2", "USDC Coin", "USDC"),
            CurrencyInfo("4", "Binance Coin", "BNB")
        )
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testGetCurrencyInfo() = testDispatcher.runBlockingTest {
        val actual = repo.getCurrencyList()
        coVerify(exactly = 1) { dao.getCurrencyInfoList() }
        assertEquals(5, actual.size)
        assertEquals("1", actual[0].id)
    }
}