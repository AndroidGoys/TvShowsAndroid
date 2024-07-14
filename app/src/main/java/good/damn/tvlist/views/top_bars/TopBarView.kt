package good.damn.tvlist.views.top_bars

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.topParams
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.buttons.ButtonBack

class TopBarView(
    context: Context,
    private val mTopInset: Int = 0
): FrameLayout(
    context
) {

    companion object {
        private const val TAG = "TopBarView"
    }

    val btnBack = ButtonBack(
        context
    )

    val textViewTitle = AppCompatTextView(
        context
    )

    init {

        btnBack.strokeColor = App.color(
            R.color.bigButtonIcon
        )

        addView(btnBack)
        addView(textViewTitle)
    }

    fun layoutChild() {
        btnBack.apply {
            ViewUtils.topBarStyleBtnBack(
                this@TopBarView,
                this,
                mTopInset
            )
        }

        textViewTitle.apply {
            ViewUtils.topBarStyleTitle(
                btnBack.topParams(),
                this@TopBarView,
                this,
                mTopInset
            )
        }
    }

}

fun TopBarView.defaultTopBarStyle(
    measureUnit: Int,
    topInset: Int
) {
    setBackgroundColor(0)
    boundsFrame(
        width = measureUnit,
        height = (measureUnit * 0.19324f
            + topInset * 0.5f).toInt()
    )
    layoutChild()
}