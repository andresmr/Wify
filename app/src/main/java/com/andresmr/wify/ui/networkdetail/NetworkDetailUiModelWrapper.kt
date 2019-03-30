package com.andresmr.wify.ui.networkdetail

import com.andresmr.wify.entity.WifiNetwork

class NetworkDetailUiModelWrapper(val network: WifiNetwork? = null,
                                  val isLoading: Boolean = false,
                                  val error: String = "") {
    companion object {
        fun isLoading() = NetworkDetailUiModelWrapper(isLoading = true)
        fun success(network: WifiNetwork) = NetworkDetailUiModelWrapper(network)
        fun error(message: String) = NetworkDetailUiModelWrapper(error = message)
    }
}