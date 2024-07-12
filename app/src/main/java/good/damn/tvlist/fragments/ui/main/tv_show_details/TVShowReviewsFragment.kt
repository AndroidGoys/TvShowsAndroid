package good.damn.tvlist.fragments.ui.main.tv_show_details

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.R
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.fragments.StackFragment

class TVShowReviewsFragment
: StackFragment() {

    var topPadding: Int = 0

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val recyclerView = RecyclerView(
            context
        )

        recyclerView.apply {
            setBackgroundColorId(
                R.color.background
            )

            clipToPadding = false
            setPadding(
                0,
                topPadding,
                0,
                topPadding
            )
        }

        return recyclerView
    }
}