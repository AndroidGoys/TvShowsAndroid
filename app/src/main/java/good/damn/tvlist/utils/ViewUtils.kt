package good.damn.tvlist.utils

import android.content.Context
import android.widget.LinearLayout
import good.damn.tvlist.R
import good.damn.tvlist.extensions.setBackgroundColorId

class ViewUtils {
    companion object {
        private const val TAG = "ViewUtils"

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