package www.bizpro.com.tw.library.utils

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import java.util.regex.Pattern

class NetworkUtils(private val application: Application) {
    private var isListening = false
    private val ipRegex =
        "([0-2][0-5][0-5]|[0-2][0-4][0-9]|[0-9][0-9]|[0-9]).([0-2][0-5][0-5]|[0-2][0-4][0-9]|[0-9][0-9]|[0-9]).([0-2][0-5][0-5]|[0-2][0-4][0-9]|[0-9][0-9]|[0-9]).([0-2][0-5][0-5]|[0-2][0-4][0-9]|[0-9][0-9]|[0-9])"
    private var mobileIP: String? = null
    private val connectivityManager: ConnectivityManager by lazy {
        application.getSystemService(ConnectivityManager::class.java)
    }
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    companion object {
        val requestWIFI: NetworkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
        val requestCELLULAR: NetworkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        val requestWIFIAndCell: NetworkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
    }


    /**
     * 註冊監聽
     */
    private fun setListener(
        request: NetworkRequest,
        networkCallback: ConnectivityManager.NetworkCallback
    ) {
        if (!isListening) {
            connectivityManager.requestNetwork(request, networkCallback)
            isListening = true
        }
    }

    /**
     * 解除註冊
     */
    fun cancelListener() {
        if (networkCallback != null && isListening) {
            connectivityManager.unregisterNetworkCallback(networkCallback!!)
            isListening = false
        }
    }

    /**
     * 網路是否連通
     */
    fun isNetworkAvailable(): Boolean {
        return connectivityManager.activeNetwork != null
    }

    private fun setMobileIP(ip: String?) {
        mobileIP = ip
    }

    /**
     * 取得裝置IP
     */
    fun getIP(): String? {
        if (isNetworkAvailable()) {
            connectivityManager.getLinkProperties(connectivityManager.activeNetwork)!!.linkAddresses.forEach {
                if (Pattern.matches(
                        ipRegex,
                        it.address.hostAddress
                    )
                ) setMobileIP(it.address.hostAddress)
            }
        }
        return mobileIP
    }
}
