package com.andresmr.wify.ui.networkslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andresmr.wify.R
import com.andresmr.wify.entity.WifiNetwork
import kotlinx.android.synthetic.main.networks_list_item.view.*

class NetworksListAdapter(private val netWorksList: List<WifiNetwork>, private val listener: (WifiNetwork) -> Unit) :
        RecyclerView.Adapter<NetworksListAdapter.WifiListsHolder>() {

    override fun getItemCount() = netWorksList.size

    override fun onBindViewHolder(holder: WifiListsHolder, position: Int) {
        holder.bind(netWorksList[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiListsHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.networks_list_item, parent, false)
        return WifiListsHolder(inflatedView)
    }

    class WifiListsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(wifiNetwork: WifiNetwork, listener: (WifiNetwork) -> Unit) = with(itemView) {
            ssid.text = wifiNetwork.ssid
            setOnClickListener { listener(wifiNetwork) }
        }
    }
}