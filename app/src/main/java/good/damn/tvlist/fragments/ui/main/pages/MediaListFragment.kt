package good.damn.tvlist.fragments.ui.main.pages

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.size
import good.damn.tvlist.fragments.ScrollableFragment
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.utils.ViewUtils

class MediaListFragment
: ScrollableFragment() {

    override fun onCreateContentView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = ViewUtils.verticalLinear(
            context
        )
        val textView = AppCompatTextView(
            context
        )

        // Color
        textView.setTextColorId(
            R.color.accentColor
        )

        // Text size
        textView.setTextSizePx(
            measureUnit * 0.07246f
        )

        // Text
        textView.setText(
            R.string.media
        )

        // Typeface
        textView.typeface = App.font(
            R.font.open_sans_bold,
            context
        )

        layout.size(
            -1,-1
        )
        textView.boundsLinear(
            left = measureUnit * 0.03864f
        )

        (measureUnit * 0.24517f).toInt().let {
            layout.setPadding(
                0, it,
                0, it
            )
        }

        layout.addView(
            textView
        )

        return layout
    }

}