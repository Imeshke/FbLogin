package com.imeshke.fblogin.api.model
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.imeshke.fblogin.R


class Image() : Parcelable {
     @SerializedName("small")
     @Expose
     var small: String? = null

     @SerializedName("medium")
     @Expose
     var medium: String? = null

     @SerializedName("large")
     @Expose
     var large: String? = null

     constructor(parcel: Parcel) : this() {
          small = parcel.readString()
          medium = parcel.readString()
          large = parcel.readString()
     }



     override fun writeToParcel(parcel: Parcel, flags: Int) {
          parcel.writeString(small)
          parcel.writeString(medium)
          parcel.writeString(large)
     }

     override fun describeContents(): Int {
          return 0
     }

     companion object CREATOR : Parcelable.Creator<Image> {
          override fun createFromParcel(parcel: Parcel): Image {
               return Image(parcel)
          }

          override fun newArray(size: Int): Array<Image?> {
               return arrayOfNulls(size)
          }
          @BindingAdapter("bind:src")
          @JvmStatic
          fun loadAvatar(view: ImageView, imageUrl: String) {

               Glide.with(view.context).setDefaultRequestOptions(RequestOptions().circleCrop())
                    .load(imageUrl).placeholder(R.drawable.loading).into(view)
          }
     }


}


