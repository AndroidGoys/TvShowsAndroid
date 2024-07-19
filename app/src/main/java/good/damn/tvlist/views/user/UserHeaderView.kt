package good.damn.tvlist.views.user

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Typeface
import android.view.View
import androidx.annotation.ColorInt
import good.damn.tvlist.views.rate.RateView

class UserHeaderView(
    context: Context
): View(
    context
) {

    var bitmapRadius = 0f

    var textSizeFUsername = 0.2f
    var textSizeFDate = 0.1f

    var textUsername: String? = null
    var textDate: String? = null

    var grade: Byte = 1
        set(v) {
            field = v
            mRatingStars.setStarsRate(
                grade
            )
        }

    var typefaceUsername = Typeface.DEFAULT
        set(v) {
            field = v
            mPaintUsername.typeface = v
        }

    var typefaceDate = Typeface.DEFAULT
        set(v) {
            field = v
            mPaintDate  .typeface = v
        }

    var bitmap: Bitmap? = null

    @ColorInt
    var textColor: Int = Color.RED
        set(v) {
            field = v
            mPaintUsername.color = v
            mPaintDate.color = v
        }

    private val mRectBitmap = RectF()

    private val mPaintUsername = Paint()
    private val mPaintDate = Paint()

    private var mTextUsernameX = 0f
    private var mTextUsernameY = 0f

    private var mTextDateX = 0f
    private var mTextDateY = 0f

    private val mPath = Path()

    private val mRatingStars = RateView(
        context
    ).apply {
        setOnTouchListener(null)
    }

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

        mPaintUsername.textSize = height * textSizeFUsername
        mPaintDate.textSize = height * textSizeFDate

        mTextUsernameX = width * 0.23404f
        mTextUsernameY = height * 0.08064f +
            mPaintUsername.textSize

        mTextDateX = mTextUsernameX
        mTextDateY = mTextUsernameY +
            mPaintDate.textSize +
            height * 0.12f

        val widthRate = width * 0.199468f
        val heightRate = height * 0.241935f

        mRatingStars.setPosition(
            mTextUsernameX.toInt(),
            (mTextDateY + height * 0.09f).toInt()
        )
        mRatingStars.layout(
            0,
            0,
            (widthRate).toInt(),
            (heightRate).toInt()
        )

        mRectBitmap.top = 0f
        mRectBitmap.left = 0f
        mRectBitmap.bottom = height.toFloat()
        mRectBitmap.right = mRectBitmap.bottom
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        if (textUsername != null) {
            canvas.drawText(
                textUsername!!,
                mTextUsernameX,
                mTextUsernameY,
                mPaintUsername
            )
        }

        if (textDate != null) {
            canvas.drawText(
                textDate!!,
                mTextDateX,
                mTextDateY,
                mPaintDate
            )
        }

        mRatingStars.draw(
            canvas
        )

        /*if (textRating != null) {
            canvas.drawText(
                textRating!!,
                mTextRatingX,
                mTextRatingY,
                mPaintDate
            )
        }*/

        if (bitmap == null) {
            return
        }

        mPath.reset()
        mPath.addRoundRect(
            mRectBitmap,
            bitmapRadius,
            bitmapRadius,
            Path.Direction.CW
        )
        mPath.close()

        canvas.clipPath(
            mPath
        )

        canvas.drawBitmap(
            bitmap!!,
            0f,
            0f,
            mPaintUsername
        )

    }

}