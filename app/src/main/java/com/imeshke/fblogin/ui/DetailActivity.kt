package com.imeshke.fblogin.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.imeshke.fblogin.R
import com.imeshke.fblogin.api.model.Hotel
import com.imeshke.fblogin.ui.main.MapFragment


class DetailActivity : AppCompatActivity() {
    lateinit var hotel: Hotel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val intent = getIntent()
         hotel = intent.getParcelableExtra<Hotel>("HOTEL")!!
        val detailDataFragment : DetailDataFragment = DetailDataFragment.newInstance(hotel!!)
        addFragmentToActivity(detailDataFragment)

    }

    private fun addFragmentToActivity(fragment: Fragment?){
        if (fragment == null) return
        val fm = supportFragmentManager
        val tr = fm.beginTransaction()
        tr.add(R.id.root_container, fragment)
        tr.commitAllowingStateLoss()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.getItemId() === android.R.id.home) {
            onBackPressed()
        }
        if (item.getItemId() === R.id.action_map) {
            val mapFragment = MapFragment(hotel)
            addFragmentToActivity(mapFragment)
        }

        return super.onOptionsItemSelected(item)
    }
}