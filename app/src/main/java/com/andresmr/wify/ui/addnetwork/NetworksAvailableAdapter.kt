package com.andresmr.wify.ui.addnetwork

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andresmr.wify.R
import com.andresmr.wify.entity.WifiAvailable
import kotlinx.android.synthetic.main.networks_list_item.view.*

class NetworksAvailableAdapter(
    private val wifiAvailableList: List<WifiAvailable>,
    private val listener: (WifiAvailable) -> Unit
) : RecyclerView.Adapter<NetworksAvailableAdapter.WifiListsHolder>() {

    override fun getItemCount() = wifiAvailableList.size

    override fun onBindViewHolder(holder: WifiListsHolder, position: Int) {
        holder.bind(wifiAvailableList[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiListsHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.networks_list_item, parent, false)
        return WifiListsHolder(inflatedView)
    }

    class WifiListsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(wifiAvailable: WifiAvailable, listener: (WifiAvailable) -> Unit) = with(itemView) {
            ssid.text = wifiAvailable.ssid
            setOnClickListener { listener(wifiAvailable) }
        }
    }
}