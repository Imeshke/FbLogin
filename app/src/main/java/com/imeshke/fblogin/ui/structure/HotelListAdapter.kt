package com.imeshke.fblogin.ui.main.structure

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.imeshke.fblogin.R
import com.imeshke.fblogin.api.model.Hotel
import com.imeshke.fblogin.databinding.HotelListItemBinding


internal class HotelListAdapter(private val callback: OnClickEvent) : RecyclerView.Adapter<HotelListAdapter.HotelViewHolder>() {
    private var hotels: List<Hotel>? = null
    interface OnClickEvent {
        fun onClickListner(hotel:Hotel)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): HotelViewHolder {
        val hotelListItemBinding: HotelListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.hotel_list_item, viewGroup, false
        )
        return HotelViewHolder(hotelListItemBinding)
    }

    override fun onBindViewHolder(hotelViewHolder: HotelViewHolder, i: Int) {
        val currentHotel = hotels!![i]

        hotelViewHolder.hotelListItemBinding.hotel = currentHotel
        hotelViewHolder.itemView.setOnClickListener {
            callback.onClickListner(currentHotel)
        }

    }

    override fun getItemCount(): Int {
        return if (hotels != null) {
            hotels!!.size
        } else {
            0
        }
    }

    fun setHotelList(hotelsList: List<Hotel>) {
        this.hotels = hotelsList
        notifyDataSetChanged()
    }

    internal class HotelViewHolder(@NonNull hotelListItemBinding: HotelListItemBinding) :
        RecyclerView.ViewHolder(hotelListItemBinding.root) {
        var hotelListItemBinding: HotelListItemBinding = hotelListItemBinding

    }



}



