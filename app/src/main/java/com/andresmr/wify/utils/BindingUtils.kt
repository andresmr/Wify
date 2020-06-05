package com.andresmr.wify.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andresmr.wify.entity.WifiAvailable
import com.andresmr.wify.ui.addnetwork.AvailableNetworksAdapter

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<WifiAvailable>?) {
    items?.let {
        (listView.adapter as AvailableNetworksAdapter).submitList(it)
    }
}