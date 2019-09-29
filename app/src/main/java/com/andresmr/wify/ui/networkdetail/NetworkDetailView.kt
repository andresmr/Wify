package com.andresmr.wify.ui.networkdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.andresmr.wify.DependencyInjector
import com.andresmr.wify.R
import com.andresmr.wify.entity.WifiNetwork
import kotlinx.android.synthetic.main.write_tag_view.*

class NetworkDetailView : AppCompatActivity() {

    private lateinit var viewModel: NetworkDetailViewModel
    private lateinit var viewModelFactory: NetworkDetailViewModelFactory
    private lateinit var wifiNetwork: WifiNetwork

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_tag_view)
        viewModel = createViewModel()
        observeViewModel()
        val ssid = intent.getStringExtra("ssid")
        viewModel.refresh(ssid)
    }

    private fun createViewModel(): NetworkDetailViewModel {
        viewModelFactory = DependencyInjector.provideNetworkDetailViewModelFactory()
        return ViewModelProvider(ViewModelStore(), viewModelFactory).get(NetworkDetailViewModel::class.java)
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
        ssid.text = ssidInfo
    }
}