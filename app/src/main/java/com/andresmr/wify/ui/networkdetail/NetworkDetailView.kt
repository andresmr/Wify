package com.andresmr.wify.ui.networkdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.andresmr.wify.R
import com.andresmr.wify.di.Injector
import kotlinx.android.synthetic.main.network_detail_view.*

class NetworkDetailView : Fragment() {

    private val safeArgs: NetworkDetailViewArgs by navArgs()

    private val viewModel: NetworkDetailViewModel by viewModels {
        Injector.provideNetworkDetailViewModelFactory(requireActivity(), safeArgs.ssid)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.network_detail_view, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.network.observe(viewLifecycleOwner, Observer { wifi ->
            showSSID(wifi.ssid)
        })
    }

    private fun showSSID(ssidInfo: String) {
        ssid.text = ssidInfo
    }
}