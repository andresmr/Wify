package com.andresmr.wify.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andresmr.wify.entity.WifiAvailable
import com.andresmr.wify.entity.WifiNetwork
import com.andresmr.wify.ui.addnetwork.AvailableNetworksAdapter
import com.andresmr.wify.ui.networkslist.NetworksListAdapter

@BindingAdapter("app:available_networks")
fun setAvailableNetworks(listView: RecyclerView, items: List<WifiAvailable>?) {
    items?.let {
        (listView.adapter as AvailableNetworksAdapter).submitList(it)
    }
}

@BindingAdapter("app:stored_networks")
fun setStoredNetworks(listView: RecyclerView, items: List<WifiNetwork>?) {
    items?.let {
        (listView.adapter as NetworksListAdapter).submitList(it)
    }
}