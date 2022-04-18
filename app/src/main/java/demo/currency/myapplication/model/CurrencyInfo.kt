package demo.currency.myapplication.model


import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
data class CurrencyInfo(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "symbol") val symbol: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    fun firstChar(): String {
        return name.first().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(symbol)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CurrencyInfo> {
        override fun createFromParcel(parcel: Parcel): CurrencyInfo {
            return CurrencyInfo(parcel)
        }

        override fun newArray(size: Int): Array<CurrencyInfo?> {
            return arrayOfNulls(size)
        }
    }
}