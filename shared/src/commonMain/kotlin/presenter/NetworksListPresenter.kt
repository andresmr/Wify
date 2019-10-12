package com.andresmr.wify.presenter

import com.andresmr.wify.domain.interactor.GetWifiNetworkListInteractor
import com.andresmr.wify.entity.WifiNetwork

class NetworksListPresenter(
    view: View,
    getWifiNetworkListInteractor: GetWifiNetworkListInteractor
) {

    init {
        view.populateList(getWifiNetworkListInteractor.execute())
    }
}

interface View {
    fun populateList(wifiNetworks: List<WifiNetwork>)
}
