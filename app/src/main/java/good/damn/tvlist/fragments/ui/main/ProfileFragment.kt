package good.damn.tvlist.fragments.ui.main

import android.content.Context
import android.view.View
import good.damn.tvlist.fragments.CloseableFragment
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.utils.ViewUtils
import java.io.Closeable

class ProfileFragment
: CloseableFragment() {

    override fun onCreateContentView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = ViewUtils.verticalLinear(
            context
        )

        return layout
    }

}