package com.andresmr.wify.ui.networkslist

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.andresmr.wify.R
import com.andresmr.wify.entity.WifiNetwork
import com.andresmr.wify.ui.networkdetail.NetworkDetailView
import kotlinx.android.synthetic.main.networks_list_fragment.*
import org.jetbrains.anko.support.v4.startActivity

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
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel = ViewModelProviders.of(this).get(NetworksListViewModel::class.java)
        viewModel.getWifiNetworks().observe(this, Observer<List<WifiNetwork>> {
            val adapter = NetworksListAdapter(it) {
                startActivity<NetworkDetailView>("ssid" to it.ssid)
            }
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        })
    }

}
