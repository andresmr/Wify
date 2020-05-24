package com.andresmr.wify.ui.networkdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.andresmr.wify.R
import kotlinx.android.synthetic.main.network_detail_view.*

class NetworkDetailView : Fragment() {

    private lateinit var viewModel: NetworkDetailViewModel
    private val safeArgs: NetworkDetailViewArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.network_detail_view, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NetworkDetailViewModel::class.java)
        viewModel.getBySsid(safeArgs.ssid).observe(viewLifecycleOwner, Observer { wifi ->
            showSSID(wifi.ssid)
        })
    }

    private fun showSSID(ssidInfo: String) {
        ssid.text = ssidInfo
    }
}