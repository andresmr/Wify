package com.andresmr.wify.ui.networkslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andresmr.wify.R
import com.andresmr.wify.entity.Wifi
import kotlinx.android.synthetic.main.networks_list_item.view.*

class NetworksListAdapter(
    context: Context?,
    private val listener: (Wifi) -> Unit
) :
    RecyclerView.Adapter<NetworksListAdapter.WifiListsHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var wifiList = emptyList<Wifi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiListsHolder {
        val inflatedView = inflater.inflate(R.layout.networks_list_item, parent, false)
        return WifiListsHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: WifiListsHolder, position: Int) {
        holder.bind(wifiList[position], listener)
    }

    override fun getItemCount() = wifiList.size

    internal fun setWifiList(wifiList: List<Wifi>) {
        this.wifiList = wifiList
        notifyDataSetChanged()
    }

    inner class WifiListsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(wifi: Wifi, listener: (Wifi) -> Unit) = with(itemView) {
            ssid.text = wifi.ssid
            setOnClickListener { listener(wifi) }
        }
    }
}