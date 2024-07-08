package good.damn.tvlist.utils

import android.content.Context
import android.icu.util.MeasureUnit
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.views.buttons.ButtonBack

class ViewUtils {
    companion object {
        private const val TAG = "ViewUtils"

        fun topBarStyleTitle(
            layoutTopBar: View,
            textView: TextView
        ) {
            textView.apply {
                setTextSizePx(
                    layoutTopBar.heightParams() * 0.325f
                )
                typeface = App.font(
                    R.font.open_sans_bold,
                    context
                )

                setTextColorId(
                    R.color.text
                )

                boundsFrame(
                    gravity = Gravity.CENTER_VERTICAL or(
                        Gravity.CENTER_HORIZONTAL
                    )
                )
            }
        }

        fun topBarStyleMain(
            layoutTopBar: View,
            measureUnit: Int
        ) {
            layoutTopBar.apply {
                setBackgroundColor(0)
                boundsFrame(
                    width = measureUnit,
                    height = (measureUnit * 0.19324f).toInt()
                )
            }
        }


        fun topBarStyleBtnBack(
            layoutTopBar: View,
            btnBack: ButtonBack
        ) {
            val size = (
                layoutTopBar.heightParams() * 0.45f
                ).toInt()

            btnBack.boundsFrame(
                gravity = Gravity.CENTER_VERTICAL or Gravity.START,
                left = layoutTopBar.widthParams() * 0.03864f,
                width = size,
                height = size
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