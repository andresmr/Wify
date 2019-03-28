package com.andresmr.wify.ui.networkslist

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.andresmr.wify.R
import com.andresmr.wify.entity.WifiNetwork
import kotlinx.android.synthetic.main.networks_list_fragment.*

class NetworksListFragment : Fragment() {

    companion object {
        fun newInstance() = NetworksListFragment()
    }

    private lateinit var viewModel: NetworksListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.networks_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NetworksListViewModel::class.java)
        viewModel.getWifiNetworks().observe(this, Observer<List<WifiNetwork>> {
            //TODO update UI
            message.text = it[0].ssid
        })
    }

}
