package good.damn.tvlist.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import good.damn.tvlist.views.round.RoundView

class ChannelDateView(
    context: Context
): RoundView(
    context
) {

    @ColorInt
    var textColor: Int = 0
        set(v) {
            field = v
            mPaintDate.color = v
            mPaintTime.color = v
        }

    var typeface = Typeface.DEFAULT
        set(v) {
            field = v
            mPaintTime.typeface = v
            mPaintDate.typeface = v
        }

    var textSizeTimeFactor = 0.2f
    var textSizeDateFactor = 0.1f

    var channelPreview: Bitmap? = null

    private val mPaintDate = Paint()
    private val mPaintTime = Paint()
    private val mPaintGradient = Paint()

    override fun setLayoutParams(
        params: ViewGroup.LayoutParams?
    ) {
        super.setLayoutParams(params)

        if (params == null) {
            return
        }

        val height = params.height

        mPaintGradient.shader = LinearGradient(
            0f,
            height * 0.41363f,
            0f,
            height.toFloat(),
            intArrayOf(
                0x00D9D9D9,
                0xff878787.toInt()
            ),
            floatArrayOf(
                0.0f,
                1.0f
            ),
            Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        if (channelPreview != null) {
            canvas.drawBitmap(
                channelPreview!!,
                0f,
                0f,
                mPaintDate
            )
        }

        if (mPaintGradient.shader != null) {
            canvas.drawPaint(
                mPaintGradient
            )
        }

    }

}