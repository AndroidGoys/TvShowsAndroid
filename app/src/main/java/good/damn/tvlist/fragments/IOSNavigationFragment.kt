package good.damn.tvlist.fragments

import android.content.Context
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatTextView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.size
import good.damn.tvlist.utils.ViewUtils

abstract class IOSNavigationFragment
: StackFragment() {
    final override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = ViewUtils.verticalLinear(
            context
        )
        val scrollView = ScrollView(
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

        (measureUnit * 0.24517f).toInt().let {
            layout.setPadding(
                0, it,
                0, it
            )
        }


        layout.size(
            -1,
            -1
        )
        textView.boundsLinear(
            left = measureUnit * 0.03864f
        )

        layout.addView(
            textView
        )

        layout.addView(
            onContentView(
                context,
                measureUnit
            )
        )

        scrollView.addView(
            layout
        )

        return scrollView
    }


    abstract fun onContentView(
        context: Context,
        measureUnit: Int
    ): View
}