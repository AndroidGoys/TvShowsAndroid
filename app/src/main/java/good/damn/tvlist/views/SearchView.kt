package good.damn.tvlist.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import good.damn.tvlist.views.round.RoundView

class SearchView(
    context: Context
): RoundView(
    context
) {

    var textExample = ""

    var textSizeExample = 0.1f
    var textSizeWord = 0.1f
    var iconSize = 0.1f

    var drawable: Drawable? = null

    @ColorInt
    var textColorExample = 0
        set(v) {
            field = v
            mPaintExample.color = v
        }

    @ColorInt
    var textColorWord = 0
        set(v) {
            field = v
            mPaintWord.color = v
        }

    var typefaceExample = Typeface.DEFAULT
        set(v) {
            field = v
            mPaintExample.typeface = v
        }

    var typefaceWord = Typeface.DEFAULT
        set(v) {
            field = v
            mPaintWord.typeface = v
        }

    private val mPaintExample = Paint()
    private val mPaintWord = Paint()

    private var mExampleX = 0f
    private var mExampleY = 0f

    private var mWordX = 2f
    private var mWordY = 2f

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        mPaintExample.textSize = height * textSizeExample
        mPaintWord.textSize = height * textSizeWord

        val iconSizei = (iconSize * height).toInt()
        val textBeginX = paddingLeft + iconSizei

        mExampleX = textBeginX.toFloat()
        mWordX = mExampleX + mPaintExample.measureText(
            textExample
        )

        mExampleY = (height - mPaintExample.textSize) * 0.5f
        mWordY = (height - mPaintWord.textSize) * 0.5f

        ((height - iconSizei) * 0.5f).toInt().let {
            drawable?.setBounds(
                paddingLeft,
                it,
                paddingLeft + iconSizei,
                it + iconSizei
            )
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawText(
            textExample,
            mExampleX,
            mExampleY,
            mPaintExample
        )

        canvas.drawText(
            "стс",
            mWordX,
            mWordY,
            mPaintWord
        )

        drawable?.draw(
            canvas
        )

    }
}