package good.damn.tvlist.fragments.ui

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import good.damn.tvlist.fragments.StackFragment

class TVDetailsFragment
: StackFragment() {

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = LinearLayout(
            context
        )

        layout.setBackgroundColor(
            0xff00ff00.toInt()
        )

        return layout
    }

}