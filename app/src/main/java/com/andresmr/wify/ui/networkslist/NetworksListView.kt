package com.andresmr.wify.ui.networkslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.andresmr.wify.DependencyInjector
import com.andresmr.wify.R
import com.andresmr.wify.entity.WifiNetwork
import kotlinx.android.synthetic.main.networks_list_view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.onRefresh

class NetworksListView : Fragment() {

    private lateinit var viewModel: NetworksListViewModel
    private lateinit var viewModelFactory: NetworksListViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.networks_list_view, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        floating_action_button.onClick {
            val action = NetworksListViewDirections.actionNetworksListViewToAddNetworkView()
            findNavController().navigate(action)
        }
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
        viewModel.getWifiNetworks().observe(viewLifecycleOwner, Observer<List<WifiNetwork>> {
            val adapter = NetworksListAdapter(it) { wifiNetwork ->
                val action =
                    NetworksListViewDirections.actionNetworksListFragmentToNetworkDetailView(
                        wifiNetwork.ssid
                    )
                findNavController().navigate(action)
            }
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        })
    }
}