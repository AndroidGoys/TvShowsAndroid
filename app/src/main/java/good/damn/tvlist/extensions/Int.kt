package good.damn.tvlist.extensions

import androidx.annotation.ColorInt

@ColorInt
fun Int.withAlpha(
    alpha: Float
): Int {
    return ((255 * alpha).toInt() and 0xff shl 24) or (
        this and 0x00ffffff
    )
}