package good.damn.tvlist.broadcast_receivers.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import good.damn.tvlist.broadcast_receivers.network.listeners.NetworkListener
import good.damn.tvlist.extensions.getConnectivityManager
import good.damn.tvlist.extensions.isAvailable

class NetworkReceiver: BroadcastReceiver() {

    var networkListener: NetworkListener? = null

    override fun onReceive(
        context: Context?,
        intent: Intent?
    ) {
        context?.getConnectivityManager()?.apply {
            if (isAvailable()) {
                networkListener?.onNetworkConnected()
                return
            }

            networkListener?.onNetworkDisconnected()
        }
    }
}