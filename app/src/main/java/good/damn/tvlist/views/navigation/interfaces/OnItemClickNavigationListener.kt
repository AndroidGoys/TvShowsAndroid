package good.damn.tvlist.views.navigation.interfaces

import good.damn.tvlist.views.navigation.NavigationView

interface OnItemClickNavigationListener {

    fun onClickNavigationItem(
        index: Int,
        navigationView: NavigationView
    )

}