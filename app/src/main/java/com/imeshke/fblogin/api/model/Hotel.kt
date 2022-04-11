package com.imeshke.fblogin.api.model

import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Hotel(): Parcelable{

    @SerializedName("id")
    var id: Long? = 0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("address")
    var address: String? = null

    @SerializedName("postcode")
    var postcode: String? = null

    @SerializedName("phoneNumber")
    var phoneNumber: String? = null

    @SerializedName("latitude")
    var latitude: String? = null

    @SerializedName("longitude")
    var longitude: String? = null

    @SerializedName("image")
    var image: Image? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        title = parcel.readString()
        description = parcel.readString()
        address = parcel.readString()
        postcode = parcel.readString()
        phoneNumber = parcel.readString()
        latitude = parcel.readString()
        longitude = parcel.readString()
        image = parcel.readParcelable(Image::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id!!)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(address)
        parcel.writeString(postcode)
        parcel.writeString(phoneNumber)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
        parcel.writeParcelable(image, flags)


    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Hotel> {
        override fun createFromParcel(parcel: Parcel): Hotel {
            return Hotel(parcel)
        }

        override fun newArray(size: Int): Array<Hotel?> {
            return arrayOfNulls(size)
        }
    }


}



