package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.view.View
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.utils.ViewUtils

class FavouritesFragment
: StackFragment() {

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = ViewUtils.verticalLinear(
            context
        )

        return layout
    }

}