package good.damn.tvlist.broadcast_receivers.network.listeners

interface NetworkListener {
    fun onNetworkConnected()
    fun onNetworkDisconnected()

}