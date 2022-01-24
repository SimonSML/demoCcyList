package demo.currency.myapplication

import android.app.Application
import demo.currency.myapplication.db.CurrencyInfoDatabase
import demo.currency.myapplication.repositories.CurrencyInfoRepoImpl
import kotlinx.coroutines.Dispatchers

class MyApp : Application() {
    private val database by lazy { CurrencyInfoDatabase.getDatabase(this) }
    val currencyInfoRepo by lazy {
        CurrencyInfoRepoImpl.getInstance(
            database.currencyInfoDao(),
            Dispatchers.IO
        )
    }
}