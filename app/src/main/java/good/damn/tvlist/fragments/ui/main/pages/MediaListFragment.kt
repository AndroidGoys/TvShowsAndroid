package good.damn.tvlist.fragments.ui.main.pages

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import good.damn.tvlist.fragments.StackFragment

class MediaListFragment
: StackFragment() {

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {

        val layout = LinearLayout(
            context
        )

        layout.setBackgroundColor(
            0xffffff00.toInt()
        )

        return layout
    }

}