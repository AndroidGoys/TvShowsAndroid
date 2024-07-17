package good.damn.tvlist.utils

import android.content.Context
import android.icu.util.MeasureUnit
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.views.buttons.ButtonBack

class ViewUtils {
    companion object {
        private const val TAG = "ViewUtils"

        fun chapterTextView(
            context: Context,
            measureUnit: Int,
            @StringRes textId: Int
        ) = TextView(
            context
        ).apply {
            setText(
                textId
            )

            typeface = App.font(
                R.font.open_sans_bold,
                context
            )

            setTextSizePx(
                measureUnit * 0.05314f
            )

            setTextColorId(
                R.color.text
            )

            boundsLinear(
                left = measureUnit * 0.07004f,
                top = measureUnit * 0.08816f
            )
        }

        fun topBarStyleTitle(
            y: Int,
            layoutTopBar: View,
            textView: TextView,
            topInset: Int
        ) {
            textView.apply {
                val h = layoutTopBar.heightParams() - topInset
                setTextSizePx(
                    h * 0.325f
                )
                typeface = App.font(
                    R.font.open_sans_bold,
                    context
                )

                setTextColorId(
                    R.color.text
                )

                boundsFrame(
                    gravity = Gravity.CENTER_HORIZONTAL,
                    top = y.toFloat()
                )
            }
        }

        fun topBarStyleBtnBack(
            layoutTopBar: View,
            btnBack: ButtonBack,
            topInset: Int
        ) {
            val h = layoutTopBar.heightParams() - topInset
            val size = (
                h * 0.45f
            ).toInt()

            btnBack.boundsFrame(
                left = layoutTopBar.widthParams() * 0.03864f,
                width = size,
                height = size,
                top = topInset + (h - size) * 0.45f
            )
        }

        fun verticalLinear(
            context: Context
        ): LinearLayout {
            val layout = LinearLayout(
                context
            )

            layout.orientation = LinearLayout
                .VERTICAL

            layout.setBackgroundColorId(
                R.color.background
            )

            return layout
        }

    }
}