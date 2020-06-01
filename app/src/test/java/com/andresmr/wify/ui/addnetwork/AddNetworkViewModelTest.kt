package com.andresmr.wify.ui.addnetwork

import com.andresmr.wify.domain.repository.WifiRepository
import com.andresmr.wify.entity.WifiNetwork
import com.andresmr.wify.utils.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class AddNetworkViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: AddNetworkViewModel
    private val repository: WifiRepository = mock()
    private val network: WifiNetwork = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        viewModel = AddNetworkViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    @Throws(InterruptedException::class)
    fun shouldAddNewNetwork() {
        runBlocking {
            viewModel.addNetwork(network)
            verify(repository).addNetwork(network)
        }
    }
}