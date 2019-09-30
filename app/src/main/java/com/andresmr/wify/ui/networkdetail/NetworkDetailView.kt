package com.andresmr.wify.ui.networkdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.andresmr.wify.DependencyInjector
import com.andresmr.wify.R
import com.andresmr.wify.createApplicationScreenMessage
import com.andresmr.wify.entity.WifiNetwork
import kotlinx.android.synthetic.main.write_tag_view.*

class NetworkDetailView : Fragment() {

    companion object {
        fun newInstance() = NetworkDetailView()
    }

    private lateinit var viewModel: NetworkDetailViewModel
    private lateinit var viewModelFactory: NetworkDetailViewModelFactory
    private lateinit var wifiNetwork: WifiNetwork

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.write_tag_view, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = createViewModel()
        observeViewModel()
        val ssid = arguments?.getString("ssid")
        viewModel.refresh(ssid!!)
    }

    private fun createViewModel(): NetworkDetailViewModel {
        viewModelFactory = DependencyInjector.provideNetworkDetailViewModelFactory()
        return ViewModelProvider(
            ViewModelStore(),
            viewModelFactory
        ).get(NetworkDetailViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.getNetwork().observe(this, Observer {
            it?.let {
                if (it.isLoading) {
                    //TODO show loading
                } else {
                    it.network?.let {
                        wifiNetwork = it
                        showSSID(it.ssid)
                    }
                }
            }
        })
    }

    private fun showSSID(ssidInfo: String) {
        ssid.text = createApplicationScreenMessage()
    }
}