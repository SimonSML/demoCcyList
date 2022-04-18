package demo.currency.myapplication.utils

import android.content.Context
import com.google.gson.Gson
import java.lang.reflect.Type

fun Context.readJsonStringFromAssetFile(filename: String): String {
    return this.assets.open(filename).bufferedReader().use {
        it.readText()
    }
}

fun <T> Context.readJsonObjectFromAssetFile(filename: String, type: Type): T {
    val json = this.assets.open(filename).bufferedReader().use {
        it.readText()
    }
    return Gson().fromJson(json, type)
}

fun <T> Context.readJsonObjectListFromAssetFile(filename: String, type: Type): List<T> {
    val json = this.readJsonStringFromAssetFile(filename)
    return Gson().fromJson(json, type)
}