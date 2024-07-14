package good.damn.tvlist.views.tab_bars

import android.content.Context
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatTextView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.normalWidth
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.utils.ViewUtils

class TabBar(
    context: Context
): HorizontalScrollView(
    context
) {

    var interval = 0f

    private val mLayout = LinearLayout(
        context
    )

    init {
        mLayout.orientation = LinearLayout
            .HORIZONTAL

        isHorizontalScrollBarEnabled = false
        isVerticalScrollBarEnabled = false

        clipToPadding = false

        addView(mLayout)
    }

    fun addTab(
        title: String
    ) {
        TabView(
            context
        ).apply {
            val h = this@TabBar.heightParams()
            val w = this@TabBar.widthParams()
            boundsLinear(
                height = h,
                width = (w * 63.normalWidth()).toInt(),
                left = interval
            )
            text = title
            textSizeFactor = 0.5f
            typeface = App.font(
                R.font.open_sans_semi_bold,
                context
            )
            strokeWidth = h * 0.03f
            strokeColor = App.color(
                R.color.lime
            )
            cornerRadius = h * 0.5f


            setBackgroundColor(0)

            mLayout.addView(
                this
            )
        }
    }

}