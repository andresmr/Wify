package com.andresmr.wify.ui.networkslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andresmr.wify.databinding.NetworksListItemBinding
import com.andresmr.wify.entity.WifiNetwork

class NetworksListAdapter(private val viewModel: NetworksListViewModel) :
    ListAdapter<WifiNetwork, NetworksListAdapter.ViewHolder>(ItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NetworksListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: NetworksListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: NetworksListViewModel, item: WifiNetwork) {
            binding.network = item
            binding.viewmodel = viewModel
            binding.executePendingBindings()
        }
    }
}

class ItemDiffCallback : DiffUtil.ItemCallback<WifiNetwork>() {
    override fun areItemsTheSame(oldItem: WifiNetwork, newItem: WifiNetwork): Boolean {
        return oldItem.ssid == newItem.ssid
    }

    override fun areContentsTheSame(oldItem: WifiNetwork, newItem: WifiNetwork): Boolean {
        return oldItem == newItem
    }
}