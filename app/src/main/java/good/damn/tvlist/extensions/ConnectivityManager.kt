package good.damn.tvlist.extensions

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

fun ConnectivityManager.isAvailable(): Boolean {

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return activeNetworkInfo?.isConnected ?: false
    }

    val nw = activeNetwork
        ?: return false

    getNetworkCapabilities(
        nw
    )?.apply {
        return hasTransport(
            NetworkCapabilities.TRANSPORT_WIFI
        ) || hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR
        ) || hasTransport(
            NetworkCapabilities.TRANSPORT_ETHERNET
        ) || hasTransport(
            NetworkCapabilities.TRANSPORT_BLUETOOTH
        )
    }
    return false
}