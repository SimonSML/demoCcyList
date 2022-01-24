package demo.currency.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import demo.currency.myapplication.model.CurrencyInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [CurrencyInfo::class], version = 1, exportSchema = false)
abstract class CurrencyInfoDatabase : RoomDatabase() {

    abstract fun currencyInfoDao(): CurrencyInfoDao

    companion object {
        @Volatile
        private var INSTANCE: CurrencyInfoDatabase? = null

        fun getDatabase(context: Context): CurrencyInfoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CurrencyInfoDatabase::class.java,
                    "currency_info_db"
                ).build()
                instance.also {
                    INSTANCE = it
                    it.insertDataForNewDB(context)
                }
            }
        }
    }

    private fun insertDataForNewDB(context: Context) {
        INSTANCE?.let { database ->
            GlobalScope.launch(Dispatchers.IO) {
                val json : String = context.assets.open("currencylist.json").bufferedReader().use {
                    it.readText()
                }
                val users: List<CurrencyInfo> =
                    Gson().fromJson(json, object : TypeToken<List<CurrencyInfo>>() {}.type)
                val dao = database.currencyInfoDao()
                users.forEach {
                    dao.insert(it)
                }
            }
        }
    }
}