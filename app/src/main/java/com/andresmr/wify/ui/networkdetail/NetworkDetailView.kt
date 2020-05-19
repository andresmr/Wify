package com.andresmr.wify.ui.networkdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.navigation.fragment.navArgs
import com.andresmr.wify.DependencyInjector
import com.andresmr.wify.R
import com.andresmr.wify.createApplicationScreenMessage
import kotlinx.android.synthetic.main.network_detail_view.*

class NetworkDetailView : Fragment() {

    private lateinit var viewModel: NetworkDetailViewModel
    private lateinit var viewModelFactory: NetworkDetailViewModelFactory
    private val safeArgs: NetworkDetailViewArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.network_detail_view, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = createViewModel()
        observeViewModel()
        val ssid = safeArgs.ssid
        viewModel.refresh(ssid)
    }

    private fun createViewModel(): NetworkDetailViewModel {
        viewModelFactory = DependencyInjector.provideNetworkDetailViewModelFactory()
        return ViewModelProvider(
            ViewModelStore(),
            viewModelFactory
        ).get(NetworkDetailViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.getNetwork().observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isLoading) {
                    //TODO show loading
                } else {
                    it.network?.let {
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