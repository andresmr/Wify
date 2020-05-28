package com.andresmr.wify.ui.networkslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.andresmr.wify.R
import com.andresmr.wify.di.Injector
import kotlinx.android.synthetic.main.networks_list_view.*

class NetworksListView : Fragment() {

    private val viewModel: NetworksListViewModel by viewModels {
        Injector.provideNetworksListViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.networks_list_view, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = NetworksListAdapter(context) { wifi ->
            val action =
                NetworksListViewDirections.actionNetworksListFragmentToNetworkDetailView(
                    wifi.ssid
                )
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.wifiNetworks.observe(viewLifecycleOwner, Observer { wifiList ->
            wifiList?.let {
                adapter.setWifiList(it)
            }
        })

        floating_action_button.setOnClickListener {
            val action = NetworksListViewDirections.actionNetworksListViewToAddNetworkView()
            findNavController().navigate(action)
        }
    }
}