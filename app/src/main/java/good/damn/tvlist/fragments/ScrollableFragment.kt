package good.damn.tvlist.fragments

import android.content.Context
import android.view.View
import android.widget.ScrollView
import good.damn.tvlist.R
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.size

abstract class ScrollableFragment
: StackFragment() {

    final override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val scrollView = ScrollView(
            context
        )

        scrollView.setBackgroundColorId(
            R.color.background
        )


        scrollView.size(
            -1,
            -1
        )

        scrollView.addView(
            onCreateContentView(
                context,
                measureUnit
            )
        )

        return scrollView
    }

    abstract fun onCreateContentView(
        context: Context,
        measureUnit: Int
    ): View

}