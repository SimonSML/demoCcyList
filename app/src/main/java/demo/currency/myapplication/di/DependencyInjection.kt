package demo.currency.myapplication.di

import demo.currency.myapplication.db.CurrencyInfoDatabase
import demo.currency.myapplication.repositories.CurrencyInfoRepo
import demo.currency.myapplication.repositories.CurrencyInfoRepoImpl
import demo.currency.myapplication.viewModels.DemoActivityViewModel
import demo.currency.myapplication.viewModels.QuoteViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DependencyInjection {
    val modules = listOf(
        dispatchers,
        currencyModule
    )
}

val dispatchers = module {
    single(named("ioDispatcher")) { Dispatchers.IO }
    single(named("mainDispatcher")) { Dispatchers.Main }
    single(named("defaultDispatcher")) { Dispatchers.Default }
}

val currencyModule = module {
    single<CurrencyInfoDatabase> { CurrencyInfoDatabase.getDatabase(androidContext()) }
    single<CurrencyInfoRepo> {
        CurrencyInfoRepoImpl.getInstance(
            get<CurrencyInfoDatabase>().currencyInfoDao(),
            get(named("ioDispatcher"))
        )
    }
    viewModel { DemoActivityViewModel(get(), get(named("defaultDispatcher"))) }
    viewModel { QuoteViewModel() }
}