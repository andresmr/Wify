package presenter

import com.andresmr.wify.domain.interactor.GetWifiNetworkListInteractor
import com.andresmr.wify.presenter.NetworksListPresenter
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test

class NetworksListPresenterTest {

    @MockK
    private lateinit var getWifiNetworkListInteractor: GetWifiNetworkListInteractor
    private lateinit var networksListPresenter: NetworksListPresenter

    @BeforeTest
    fun setUp() {
        networksListPresenter = NetworksListPresenter(getWifiNetworkListInteractor)
        return MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun shouldLoadWifiNetworkList() {

    }
}