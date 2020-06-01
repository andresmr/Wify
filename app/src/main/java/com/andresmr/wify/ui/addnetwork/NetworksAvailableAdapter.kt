package com.andresmr.wify.ui.addnetwork

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andresmr.wify.R
import com.andresmr.wify.entity.WifiAuthType
import com.andresmr.wify.entity.WifiAvailable
import com.andresmr.wify.entity.WifiNetwork
import kotlinx.android.synthetic.main.networks_list_item.view.*

class NetworksAvailableAdapter(
    context: Context?,
    private val listener: (WifiNetwork) -> Unit
) : RecyclerView.Adapter<NetworksAvailableAdapter.WifiListsHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var wifiAvailableList = emptyList<WifiAvailable>()

    override fun getItemCount() = wifiAvailableList.size

    override fun onBindViewHolder(holder: WifiListsHolder, position: Int) {
        holder.bind(wifiAvailableList[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiListsHolder {
        val inflatedView = inflater.inflate(R.layout.networks_list_item, parent, false)
        return WifiListsHolder(inflatedView)
    }

    class WifiListsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(wifiAvailable: WifiAvailable, listener: (WifiNetwork) -> Unit) = with(itemView) {
            ssid.text = wifiAvailable.ssid
            setOnClickListener {
                listener(
                    WifiNetwork(
                        wifiAvailable.ssid,
                        "1234",
                        WifiAuthType.WPA2_PSK
                    )
                )
            }
        }
    }

    fun setWifiAvailableList(wifiAvailableList: List<WifiAvailable>) {
        this.wifiAvailableList = wifiAvailableList
        notifyDataSetChanged()
    }
}