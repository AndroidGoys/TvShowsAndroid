package good.damn.tvlist.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes

class TVChannelView(
    context: Context
): View(
    context
) {

    var text = ""
    var imagePreview: Bitmap? = null
    var imageIcon: Drawable? = null

    @ColorInt
    var textColor: Int = 0
        set(v) {
            field = v
            mPaintText.color = v
        }

    var typeface = Typeface.DEFAULT
        set(v) {
            field = v
            mPaintText.typeface = v
        }

    var textSize: Float = 0f
        set(v) {
            field = v
            mPaintText.textSize = v
        }

    private val mPaintText = Paint()

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(
            changed,
            left,
            top,
            right,
            bottom
        )

    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(canvas)
        canvas.drawText(
            text,
            0f,
            height.toFloat(),
            mPaintText
        )
    }

}