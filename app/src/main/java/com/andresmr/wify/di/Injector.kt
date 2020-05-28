package com.andresmr.wify.di

import android.content.Context
import com.andresmr.wify.data.db.WifiRoomDatabase
import com.andresmr.wify.data.repository.WifiRepositoryImpl
import com.andresmr.wify.domain.repository.WifiRepository
import com.andresmr.wify.ui.addnetwork.AddNetworkViewModel
import com.andresmr.wify.ui.addnetwork.AddNetworkViewModelFactory
import com.andresmr.wify.ui.networkdetail.NetworkDetailViewModelFactory
import com.andresmr.wify.ui.networkslist.NetworksListViewModel
import com.andresmr.wify.ui.networkslist.NetworksListViewModelFactory

object Injector {

    private fun getWifiRepository(context: Context): WifiRepository {
        return WifiRepositoryImpl.getInstance(
            WifiRoomDatabase.getInstance(context.applicationContext).wifiDao()
        )
    }

    fun provideNetworkDetailViewModelFactory(
        context: Context,
        ssid: String
    ): NetworkDetailViewModelFactory {
        return NetworkDetailViewModelFactory(getWifiRepository(context), ssid)
    }

    fun provideNetworksListViewModelFactory(context: Context): NetworksListViewModelFactory {
        return NetworksListViewModelFactory(getWifiRepository(context))
    }

    fun provideAddNetworkViewModelFactory(context: Context): AddNetworkViewModelFactory {
        return AddNetworkViewModelFactory(getWifiRepository(context))
    }
}