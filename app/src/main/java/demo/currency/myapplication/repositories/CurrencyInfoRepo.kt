package demo.currency.myapplication.repositories

import demo.currency.myapplication.model.CurrencyInfo

interface CurrencyInfoRepo {
    suspend fun getCurrencyList() : List<CurrencyInfo>
}