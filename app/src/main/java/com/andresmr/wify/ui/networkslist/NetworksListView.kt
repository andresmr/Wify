package com.andresmr.wify.ui.networkslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.andresmr.wify.R
import com.andresmr.wify.databinding.NetworksListViewBinding
import com.andresmr.wify.di.Injector

class NetworksListView : Fragment() {

    private val viewModel: NetworksListViewModel by viewModels {
        Injector.provideNetworksListViewModelFactory(requireActivity())
    }

    private lateinit var binding: NetworksListViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.networks_list_view, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.storedNetworksList.apply {
            adapter = NetworksListAdapter(viewModel)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigateToDetails.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()
                ?.let { // Only proceed if the event has never been handled
                    navigateToNetworkDetail(event.peekContent())
                }
        })

        viewModel.navigateToAddNetwork.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                navigateToAddNetwork()
            }
        })
    }

    private fun navigateToAddNetwork() {
        val action = NetworksListViewDirections.actionNetworksListViewToAddNetworkView()
        findNavController().navigate(action)
    }

    private fun navigateToNetworkDetail(ssid: String) {
        val action =
            NetworksListViewDirections.actionNetworksListFragmentToNetworkDetailView(ssid)
        findNavController().navigate(action)
    }
}