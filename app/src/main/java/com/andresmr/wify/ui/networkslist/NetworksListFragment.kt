package com.andresmr.wify.ui.networkslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.andresmr.wify.R
import com.andresmr.wify.entity.WifiNetwork
import kotlinx.android.synthetic.main.networks_list_fragment.*
import org.jetbrains.anko.support.v4.onRefresh

class NetworksListFragment : Fragment() {

    companion object {
        fun newInstance() = NetworksListFragment()
    }

    private lateinit var viewModel: NetworksListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.networks_list_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel = ViewModelProviders.of(this).get(NetworksListViewModel::class.java)
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.onRefresh {
            onLoadWifiNetworks()
        }
        onLoadWifiNetworks()
    }

    private fun onLoadWifiNetworks() {
        viewModel.getWifiNetworks().observe(this, Observer<List<WifiNetwork>> {
            val adapter = NetworksListAdapter(it) { wifiNetwork ->
                val bundle = bundleOf("ssid" to wifiNetwork.ssid)
                findNavController().navigate(R.id.networkDetailView, bundle)
            }
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        })
    }
}