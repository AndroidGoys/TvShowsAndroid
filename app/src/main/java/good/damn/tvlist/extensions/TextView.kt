package good.damn.tvlist.extensions

import android.util.TypedValue
import android.widget.TextView

fun TextView.setTextSizePx(
    v: Float
) {
    setTextSize(
        TypedValue.COMPLEX_UNIT_PX,
        v
    )
}