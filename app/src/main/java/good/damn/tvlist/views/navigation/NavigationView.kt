package good.damn.tvlist.views.navigation

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import good.damn.tvlist.views.navigation.interfaces.OnItemClickNavigationListener

class NavigationView(
    context: Context
): CardView(
    context
), View.OnTouchListener {

    companion object {
        private const val TAG = "NavigationView"
    }

    var items: Array<NavigationItem>? = null

    var onItemClickListener: OnItemClickNavigationListener? = null

    @ColorInt
    var selectedItemColor: Int = 0

    @ColorInt
    var itemColor: Int = 0xffff0000.toInt()

    var currentItem = 0
        set(v) {
            field = v
            if (items == null) {
                return
            }

            for (i in items!!.indices) {
                items?.get(i)
                    ?.vector
                ?.color = if (i == currentItem)
                    selectedItemColor else itemColor
            }
            invalidate()
        }

    private var mIconBound = 0

    init {
        super.setOnTouchListener(
            this
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        var currentBound = 0

        items?.let {
            mIconBound = width / it.size
            it.forEach { item ->
                val v = item.vector ?: return@forEach
                v.x = (currentBound + (
                    mIconBound - v.width
                ) * 0.5f).toInt()
                currentBound += mIconBound
            }
        }
    }

    override fun setOnTouchListener(
        l: OnTouchListener?
    ) {
        super.setOnTouchListener(
            this
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        items?.forEach {
            it.vector?.draw(
                canvas
            )
        }
    }

    override fun setBackgroundColor(color: Int) {
        setCardBackgroundColor(
            color
        )
    }

    override fun onTouch(
        v: View?,
        event: MotionEvent?
    ): Boolean {

        if (event == null || items == null) {
            return false
        }

        if (event.action != MotionEvent.ACTION_UP) {
            return true
        }

        val x = event.x

        // It needs binary tree, but it's ok
        var currentBound = 0
        for (i in items!!.indices) {
            if (x < currentBound || x > currentBound + mIconBound) {
                // Outside
                currentBound += mIconBound
                continue
            }

            onItemClickListener?.onClickNavigationItem(
                i,
                this
            )
            currentBound += mIconBound
        }

        return true
    }

}