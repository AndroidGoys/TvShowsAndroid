package good.damn.tvlist.views.canvas

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.health.connect.datatypes.HeightRecord
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import good.damn.tvlist.animators.RepeatValueAnimator

class CVDownloadAnimation(
    private val view: View
) {

    private val mPaintLoading = Paint()

    private var mDownloadGradientX = 0f

    private val mDownloadAnimator = RepeatValueAnimator()

    init {
        mPaintLoading.isDither = true

        mDownloadAnimator.apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
            repeatDelay = 1500
            addUpdateListener {
                mDownloadGradientX = it.animatedValue as? Float ?: 0f
                view.invalidate()
            }
        }
    }

    fun layout(
        widthGradient: Float,
        width: Float,
        height: Float
    ) {
        mPaintLoading.shader = LinearGradient(
            0f,
            0f,
            widthGradient,
            0f,
            intArrayOf(
                0x00ffffff,
                0xffffffff.toInt(),
                0x00ffffff
            ),
            floatArrayOf(
                0.0f,
                0.5f,
                1.0f
            ),
            Shader.TileMode.CLAMP
        )

        mDownloadAnimator.setFloatValues(
            -widthGradient, width
        )
    }

    fun isRunning() = mDownloadAnimator.isRunning

    fun draw(
        canvas: Canvas
    ) {
        canvas.translate(
            mDownloadGradientX,
            0f
        )
        canvas.drawPaint(
            mPaintLoading
        )
        canvas.translate(
            -mDownloadGradientX,
            0f
        )
    }

    fun start() {
        mDownloadAnimator.start()
    }

    fun stop() {
        mDownloadAnimator.cancel()
    }

}