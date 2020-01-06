package com.example.aqimonitor.model

import android.os.Parcel
import android.os.Parcelable

class AQIModel(var latitude: Double?, var longitude: Double?,
               var nameAddress: String? = null,
               var address: String? = null,
               var aqiIndex: Float? = 0f,
               var isCurrentPosition:Boolean = false,
               var isFollow:Boolean = false) : Parcelable {
    constructor(parcel: Parcel) : this(
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

