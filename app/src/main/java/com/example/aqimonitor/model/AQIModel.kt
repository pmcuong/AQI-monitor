package com.example.aqimonitor.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.aqimonitor.utils.Constant

@Entity(tableName = Constant.AQI_DATABASE)
class AQIModel(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo var latitude: Double?,
    @ColumnInfo var longitude: Double?,
    @ColumnInfo var nameAddress: String? = null,
    @ColumnInfo var address: String? = null,
    @ColumnInfo var aqiIndex: Float? = 0f,
    @ColumnInfo var isCurrentPosition: Boolean = false,
    @ColumnInfo var isFollow: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeValue(latitude)
        parcel.writeValue(longitude)
        parcel.writeString(nameAddress)
        parcel.writeString(address)
        parcel.writeValue(aqiIndex)
        parcel.writeByte(if (isCurrentPosition) 1 else 0)
        parcel.writeByte(if (isFollow) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AQIModel> {
        override fun createFromParcel(parcel: Parcel): AQIModel {
            return AQIModel(parcel)
        }

        override fun newArray(size: Int): Array<AQIModel?> {
            return arrayOfNulls(size)
        }
    }

}

