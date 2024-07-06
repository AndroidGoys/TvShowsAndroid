package good.damn.tvlist.views.buttons

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.annotation.ColorInt
import good.damn.kamchatka.views.interactions.AnimatedTouchInteraction
import good.damn.kamchatka.views.interactions.interfaces.OnActionListener
import good.damn.kamchatka.views.interactions.interfaces.OnUpdateAnimationListener
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.boundsLinear
import good.damn.tvlist.views.round.RoundView

class ButtonBack(
    context: Context
): RoundView(
    context
), OnUpdateAnimationListener, OnActionListener {

    private val mPaint = run {
        val p = Paint()
        p.color = Color.RED
        p.style = Paint.Style.STROKE
        p.strokeCap = Paint.Cap.ROUND
        p.strokeJoin = Paint.Join.ROUND
        p
    }

    @ColorInt
    var strokeColor: Int = 0
        set(v) {
            field = v
            mPaint.color = v
        }

    private var mPointStartX = 1f
    private var mPointStartY = 1f
    private var mPointCenterX = 1f
    private var mPointCenterY = 1f
    private var mPointEndY = 1f
    
    init {
        setBackgroundColor(
            Color.TRANSPARENT
        )

        mTouchInteraction.apply {
            setOnActionListener(
                this@ButtonBack
            )
            setOnUpdateAnimationListener(
                this@ButtonBack
            )
            setDuration(100)
        }
    }
    
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        mPaint.strokeWidth = width * 0.08571f

        mPointStartX = width * 0.68571f
        mPointStartY = height * 0.14285f
        mPointCenterX = width * 0.34285f
        mPointCenterY = height * 0.48571f
        mPointEndY = height * 0.828571f
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(canvas)

        canvas.drawLine(
            mPointStartX,
            mPointStartY,
            mPointCenterX,
            mPointCenterY,
            mPaint
        )

        canvas.drawLine(
            mPointCenterX,
            mPointCenterY,
            mPointStartX,
            mPointEndY,
            mPaint
        )
    }

    override fun onUpdateAnimation(
        animatedValue: Float
    ) {
        scaleX = animatedValue
        scaleY = animatedValue
    }

    override fun onDown(
        v: View,
        event: MotionEvent
    ) = Unit

}