package good.damn.tvlist.views.tab_bars

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatTextView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.normalWidth
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.utils.ViewUtils

class TabBar(
    context: Context
): ScrollView(
    context
) {

    private val mLayout = LinearLayout(
        context
    ).apply {
        orientation = LinearLayout.HORIZONTAL
    }

    init {
        addView(mLayout)
    }

    fun addTab(
        title: String
    ) {
        val textView = AppCompatTextView(
            context
        )

        textView.boundsLinear(
            height = heightParams(),
            left = widthParams() * 14.normalWidth()
        )

        textView.setTextSizePx(
            heightParams() * 0.5f
        )

        textView.text = title

        textView.typeface = App.font(
            R.font.open_sans_semi_bold,
            context
        )

        mLayout.addView(
            textView
        )
    }

}