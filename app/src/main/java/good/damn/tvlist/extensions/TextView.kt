package good.damn.tvlist.extensions

import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.ColorRes
import good.damn.tvlist.App


fun TextView.setTextColorId(
    @ColorRes id: Int
) {
    setTextColor(
        App.color(
            id
        )
    )
}

fun TextView.setTextSizePx(
    v: Float
) {
    setTextSize(
        TypedValue.COMPLEX_UNIT_PX,
        v
    )
}