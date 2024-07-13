package good.damn.tvlist.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.view.View
import androidx.annotation.ColorInt

class UserHeaderView(
    context: Context
): View(
    context
) {

    var textSizeFUsername = 0.2f
    var textSizeFDate = 0.1f
    var textSizeFRating = 0.1f

    var textUsername: String? = null
    var textDate: String? = null
    var textRating: String? = null

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

    var typefaceRating = Typeface.DEFAULT
        set(v) {
            field = v
            mPaintRating.typeface = v
        }

    @ColorInt
    var textColor: Int = Color.RED
        set(v) {
            field = v
            mPaintUsername.color = v
            mPaintDate.color = v
            mPaintRating.color = v
        }

    private val mPaintUsername = Paint()
    private val mPaintDate = Paint()
    private val mPaintRating = Paint()

    private var mTextUsernameX = 0f
    private var mTextUsernameY = 0f

    private var mTextDateX = 0f
    private var mTextDateY = 0f

    private var mTextRatingX = 0f
    private var mTextRatingY = 0f

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
        mPaintRating.textSize = height * textSizeFRating

        mTextUsernameX = width * 0.23404f
        mTextUsernameY = height * 0.08064f + mPaintUsername.textSize

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

    }

}