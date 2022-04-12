package demo.currency.myapplication

import android.app.Application
import demo.currency.myapplication.di.DependencyInjection
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            // declare use Android context
            androidContext(this@MyApp)
            // declare modules
            modules(DependencyInjection.modules)
        }

    }
}