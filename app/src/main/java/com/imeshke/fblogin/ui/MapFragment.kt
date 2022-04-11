package com.imeshke.fblogin.ui.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.imeshke.fblogin.R
import com.imeshke.fblogin.api.model.Hotel



class MapFragment(hotel: Hotel) : Fragment() {

    private var  currentHotel :Hotel = hotel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as DetailActivity?)!!.supportActionBar?.title=getString(R.string.map_header)
        setHasOptionsMenu(true);
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.frg) as SupportMapFragment?  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment!!.getMapAsync { mMap ->
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

            mMap.clear()
            val latLng  = LatLng(currentHotel.latitude!!.toDouble(), currentHotel.longitude!!.toDouble())
            val googlePlex = CameraPosition.builder()
                .target(latLng)
                .zoom(8f)
                .build()

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 4000, null)

            mMap.addMarker(
                MarkerOptions().icon(bitmapDescriptorFromVector(activity, R.drawable.pin))
                    .position(latLng)
                    .icon(bitmapDescriptorFromVector(activity, R.drawable.pin))
            )
        }

        return rootView
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        val item: MenuItem = menu.findItem(R.id.action_map)
        if (item != null) item.setVisible(false)
    }
    private fun bitmapDescriptorFromVector(c: Context?, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = c?.let { ContextCompat.getDrawable(it, vectorResId) }
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap =
            Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

}