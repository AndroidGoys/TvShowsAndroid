package good.damn.tvlist.views

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorInt
import good.damn.tvlist.views.round.RoundView
import kotlin.math.roundToInt

class SearchView(
    context: Context
): RoundView(
    context
), ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

    var textExample = ""
    var textWords = arrayOf("")

    var textSizeExample = 0.1f
    var textSizeWord = 0.1f
    var iconSize = 0.1f
    var iconPaddingRight = 0.0f

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

    var animationInterpolator: Interpolator = LinearInterpolator()
        set(v) {
            field = v
            mAnimator.interpolator = v
        }

    var animationInterval: Long = 0
        set(v) {
            field = v
            mAnimator.startDelay = v
        }

    var animationDuration: Long = 0
        set(v) {
            field = v
            mAnimator.duration = v
        }

    private val mPaintExample = Paint()
    private val mPaintWord = Paint()

    private var mExampleX = 0f
    private var mExampleY = 0f

    private var mWordX = 2f
    private var mWordY = 2f

    private var mStartAnimSpell: Int = 0
    private var mEndAnimSpell: Int = 0

    private var mCurrentAnimWord: Int = 0

    private var mNeedReverse = false

    private val mAnimator = ValueAnimator.ofFloat(
        0.0f, 1.0f
    )

    init {
        mAnimator.addUpdateListener(
            this
        )
        mAnimator.addListener(
            this
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        mPaintExample.textSize = height * textSizeExample
        mPaintWord.textSize = height * textSizeWord

        val iconSizei = (iconSize * height).toInt()
        val textBeginX = paddingLeft + iconSizei

        mExampleX = textBeginX + iconPaddingRight * width
        mWordX = mExampleX + mPaintExample.measureText(
            textExample
        ) + width * 0.018f // margin


        mPaintExample.textSize.let {
            mExampleY = it*0.85f + (
                height - it
            ) * 0.5f
        }

        mPaintWord.textSize.let {
            mWordY = it*0.85f + (
                height - it
            ) * 0.5f
        }

        ((height - iconSizei) * 0.5f).toInt().let {
            drawable?.setBounds(
                paddingLeft,
                it,
                paddingLeft + iconSizei,
                it + iconSizei
            )
        }

    }

    override fun onAnimationUpdate(
        animation: ValueAnimator
    ) {
        val v = animation.animatedValue as Float
        mEndAnimSpell = (v * textWords[mCurrentAnimWord].length).roundToInt()
        invalidate()
    }

    override fun onAnimationStart(
        animation: Animator
    ) {
        if (mNeedReverse) {
            return
        }
        mCurrentAnimWord++
        if (mCurrentAnimWord >= textWords.size) {
            mCurrentAnimWord = 0
        }
    }

    override fun onAnimationEnd(
        animation: Animator
    ) {
        mNeedReverse = !mNeedReverse
        if (mNeedReverse) {
            mAnimator.setFloatValues(
                1.0f,0.0f
            )
            startAnimation()
            return
        }
        mAnimator.setFloatValues(
            0.0f, 1.0f
        )
        startAnimation()
    }

    override fun onAnimationCancel(
        animation: Animator
    ) = Unit

    override fun onAnimationRepeat(
        animation: Animator
    ) = Unit

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawText(
            textExample,
            mExampleX,
            mExampleY,
            mPaintExample
        )

        canvas.drawText(
            textWords[mCurrentAnimWord],
            mStartAnimSpell,
            mEndAnimSpell,
            mWordX,
            mWordY,
            mPaintWord
        )

        drawable?.draw(
            canvas
        )
    }

    fun startAnimation() {
        mAnimator.start()
    }
}