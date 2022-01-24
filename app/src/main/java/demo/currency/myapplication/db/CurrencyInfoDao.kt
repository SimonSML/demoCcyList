package demo.currency.myapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import demo.currency.myapplication.model.CurrencyInfo
@Dao
interface CurrencyInfoDao {

    @Query("SELECT * from currency_table ORDER BY id ASC")
    fun getCurrencyInfoList(): List<CurrencyInfo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(curr: CurrencyInfo)
}