package good.damn.tvlist.fragments.ui

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import good.damn.tvlist.R
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation

class TVListFragment
: StackFragment() {

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = FrameLayout(
            context
        )
        val viewPager = ViewPager2(
            context
        )

        layout.setBackgroundColorId(
            R.color.background
        )

        layout.apply {
            addView(viewPager)
        }


        return layout
    }
}