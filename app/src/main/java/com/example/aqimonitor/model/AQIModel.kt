package com.example.aqimonitor.model

import android.os.Parcel
import android.os.Parcelable

class AQIModel(var nameAddress: String? = null, var address: String? = null, var aqiIndex: Int? = 0) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nameAddress)
        parcel.writeString(address)
        parcel.writeValue(aqiIndex)
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

