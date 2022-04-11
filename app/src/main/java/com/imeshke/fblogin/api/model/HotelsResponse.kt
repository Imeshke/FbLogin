package com.imeshke.fblogin.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HotelsResponse{
     @SerializedName("status")
     @Expose
     val status: Int? = null

     @SerializedName("data")
     @Expose
     var data: ArrayList<Hotel>? = null

 }

