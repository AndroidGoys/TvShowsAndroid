package good.damn.tvlist.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.view.View
import android.view.ViewGroup
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.network.api.models.TVSearchResult
import good.damn.tvlist.views.canvas.CVDownloadAnimation

class TVIconNameView(
    context: Context
): View(
    context
), View.OnClickListener {

    var onClickDataListener: ((TVSearchResult)->Unit)? = null

    var model: TVSearchResult? = null

    var bitmap: Bitmap? = null
    var text: String? = null

    private var cornerRadius = 0f

    private val mPaintText = Paint()

    private var mTextX = 0f
    private var mTextY = 0f

    private val mRectBitmap = RectF()

    private val mPath = Path()

    private val mDownloadAnimation = CVDownloadAnimation(
        this
    )

    init {
        mPaintText.typeface = App.font(
            R.font.open_sans_regular,
            context
        )

        mPaintText.color = App.color(
            R.color.text
        )

        super.setOnClickListener(
            this
        )
    }

    override fun setLayoutParams(
        params: ViewGroup.LayoutParams?
    ) {
        super.setLayoutParams(
            params
        )

        if (params == null) {
            return
        }

        val height = params.height.toFloat()
        val width = params.width.toFloat()

        mPaintText.textSize = height * 0.31034f
        mTextX = width * 0.20108f
        mTextY = (height + mPaintText.textSize) * 0.475f

        mRectBitmap.top = 0f
        mRectBitmap.left = 0f
        mRectBitmap.bottom = height
        mRectBitmap.right = height

        cornerRadius = height * 0.24137f

        mDownloadAnimation.layout(
            width * 0.3f,
            width,
            height
        )
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        if (text != null) {
            canvas.drawText(
                text!!,
                mTextX,
                mTextY,
                mPaintText
            )
        }

        canvas.drawRoundRect(
            mRectBitmap,
            cornerRadius,
            cornerRadius,
            mPaintText
        )

        if (mDownloadAnimation.isRunning()) {
            mDownloadAnimation.draw(canvas)
            return
        }

        if (bitmap == null) {
            return
        }

        clipToImageBounds(
            canvas
        )

        canvas.drawBitmap(
            bitmap!!,
            0f,
            0f,
            mPaintText
        )
    }

    override fun setOnClickListener(
        l: OnClickListener?
    ) {
        super.setOnClickListener(
            this
        )
    }

    override fun onClick(
        v: View?
    ) {
        if (model == null) {
            return
        }
        onClickDataListener?.invoke(
            model!!
        )
    }

    fun startDownloadAnimation() {
        mDownloadAnimation.start()
    }

    fun stopDownloadAnimation() {
        mDownloadAnimation.stop()
    }

    private fun clipToImageBounds(
        canvas: Canvas
    ) {
        mPath.reset()
        mPath.addRoundRect(
            mRectBitmap,
            cornerRadius,
            cornerRadius,
            Path.Direction.CW
        )
        mPath.close()

        canvas.clipPath(
            mPath
        )
    }
}