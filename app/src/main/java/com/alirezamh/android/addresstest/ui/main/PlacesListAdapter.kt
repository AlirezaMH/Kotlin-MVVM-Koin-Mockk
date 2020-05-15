package com.alirezamh.android.addresstest.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alirezamh.android.addresstest.R
import com.alirezamh.android.addresstest.data.model.PlaceModel
import kotlinx.android.synthetic.main.main_fragment_bottom_sheet_item.view.*

/**
 * Author:      Alireza Mahmoodi
 * Created:     5/14/2020
 * Email:       mahmoodi.dev@gmail.com
 * Website:     alirezamh.com
 */
class PlacesListAdapter: RecyclerView.Adapter<PlacesListAdapter.ViewHolder>() {
    private val items = mutableListOf<PlaceModel>()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            itemView.title.text = items[position].title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_fragment_bottom_sheet_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setItems(items: List<PlaceModel>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}