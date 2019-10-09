package com.andresmr.wify.ui.networkslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.andresmr.wify.DependencyInjector
import com.andresmr.wify.R
import com.andresmr.wify.entity.WifiNetwork
import kotlinx.android.synthetic.main.networks_list_fragment.*
import org.jetbrains.anko.support.v4.onRefresh

class NetworksListFragment : Fragment() {

    companion object {
        fun newInstance() = NetworksListFragment()
    }

    private lateinit var viewModel: NetworksListViewModel
    private lateinit var viewModelFactory: NetworksListViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.networks_list_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel = createViewModel()
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.onRefresh {
            onLoadWifiNetworks()
        }
        onLoadWifiNetworks()
    }

    private fun createViewModel(): NetworksListViewModel {
        viewModelFactory = DependencyInjector.provideNetworksListViewModelFactory()
        return ViewModelProvider(
            ViewModelStore(),
            viewModelFactory
        ).get(NetworksListViewModel::class.java)
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