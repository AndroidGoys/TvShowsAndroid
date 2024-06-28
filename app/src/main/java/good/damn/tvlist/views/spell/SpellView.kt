package good.damn.tvlist.views.spell

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.ColorInt
import java.util.LinkedList

class SpellView(
    context: Context
): View(
    context
), ValueAnimator.AnimatorUpdateListener {

    var text = ""
        set(v) {
            field = v
            text.toCharArray().let { chars ->
                mSpellRenders = Array(chars.size) {
                    SpellRender(
                        chars[it].toString(),
                        0f,
                        0f
                    )
                }
            }
        }

    var textSize = 15f
        set(v) {
            field = v
            mPaint.textSize = v
        }

    var typeface = Typeface.DEFAULT
        set(v) {
            field = v
            mPaint.typeface = v
        }

    @ColorInt
    var textColor = 0xffff0000.toInt()
        set(v) {
            field = v
            mPaint.color = textColor
        }

    private var mSpellRenders: Array<SpellRender>? = null

    private val mAnimator = ValueAnimator.ofFloat(
        0.0f, 1.0f
    )
    private val mPaint = Paint()

    init {
        mAnimator.duration = 1350
        mAnimator.interpolator = AccelerateDecelerateInterpolator()

        mAnimator.addUpdateListener(
            this
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        var x = (width - mPaint.measureText(
            text
        )) * 0.5f

        mSpellRenders?.forEach {
            val spellWidth = mPaint.measureText(
                it.spell
            )
            it.startX = x
            x += spellWidth
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mSpellRenders?.forEach {
            canvas.drawText(
                it.spell,
                it.currentX,
                height.toFloat(),
                mPaint
            )
        }

    }

    override fun onAnimationUpdate(
        animation: ValueAnimator
    ) {
        (animation.animatedValue as Float).let { factor ->
            mSpellRenders?.forEach {
                it.currentX = it.startX + (width + it.startX * 5.0f) * (1.0f - factor)
            }
        }
        invalidate()
    }

    fun startAnimation() {
        mAnimator.start()
    }

}