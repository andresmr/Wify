package com.andresmr.wify.ui.addnetwork

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andresmr.wify.databinding.AvailableNetworksListItemBinding
import com.andresmr.wify.entity.WifiAvailable

class AvailableNetworksAdapter(private val viewModel: AddNetworkViewModel) :
    ListAdapter<WifiAvailable, AvailableNetworksAdapter.ViewHolder>(ItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            AvailableNetworksListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: AvailableNetworksListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: AddNetworkViewModel, item: WifiAvailable) {
            binding.wifiavailable = item
            binding.viewmodel = viewModel
            binding.executePendingBindings()
        }
    }
}

class ItemDiffCallback : DiffUtil.ItemCallback<WifiAvailable>() {
    override fun areItemsTheSame(oldItem: WifiAvailable, newItem: WifiAvailable): Boolean {
        return oldItem.ssid == newItem.ssid
    }

    override fun areContentsTheSame(oldItem: WifiAvailable, newItem: WifiAvailable): Boolean {
        return oldItem == newItem
    }
}