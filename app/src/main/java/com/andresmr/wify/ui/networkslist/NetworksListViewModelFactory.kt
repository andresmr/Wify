package com.andresmr.wify.ui.networkslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andresmr.wify.domain.repository.WifiRepository

class NetworksListViewModelFactory(private val repository: WifiRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NetworksListViewModel(repository) as T
    }

}
