package demo.currency.myapplication.repositories

import demo.currency.myapplication.db.CurrencyInfoDao
import demo.currency.myapplication.model.CurrencyInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CurrencyInfoRepoImpl(
    private val currencyInfoDao: CurrencyInfoDao,
    private val ioDispatcher: CoroutineDispatcher
) : CurrencyInfoRepo {

    override suspend fun getCurrencyList(): List<CurrencyInfo> {
        return withContext(ioDispatcher) {
            currencyInfoDao.getCurrencyInfoList()
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: CurrencyInfoRepoImpl? = null

        fun getInstance(
            currencyInfoDao: CurrencyInfoDao,
            ioDispatcher: CoroutineDispatcher
        ): CurrencyInfoRepoImpl {
            return INSTANCE ?: synchronized(this) {
                val instance = CurrencyInfoRepoImpl(
                    currencyInfoDao, ioDispatcher
                )
                instance.also {
                    INSTANCE = it
                }
            }
        }
    }
}