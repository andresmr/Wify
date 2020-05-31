package com.andresmr.wify.ui.addnetwork

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andresmr.wify.domain.repository.WifiRepository

class AddNetworkViewModelFactory(private val repository: WifiRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return AddNetworkViewModel(repository) as T
    }

}
