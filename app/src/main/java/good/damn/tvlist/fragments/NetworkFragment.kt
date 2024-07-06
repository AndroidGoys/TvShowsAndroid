package good.damn.tvlist.fragments

import androidx.fragment.app.Fragment

open class NetworkFragment
: Fragment() {

    open fun onNetworkConnected() {}

    open fun onNetworkDisconnected() {}

}