package com.imeshke.fblogin.utils

import android.content.Context
import android.content.Intent
import com.imeshke.fblogin.api.model.Hotel
import com.imeshke.fblogin.ui.MainActivity
import com.imeshke.fblogin.ui.main.DetailActivity


fun Context.startHomeActivity() =
    Intent(this, MainActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }



fun Context.startDetailActivity(hotel: Hotel) {
    val intent = Intent(this, DetailActivity::class.java)
    intent.putExtra("HOTEL", hotel)
    startActivity(intent)
}