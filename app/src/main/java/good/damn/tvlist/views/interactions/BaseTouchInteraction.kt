package good.damn.kamchatka.views.interactions

import android.util.Log
import android.view.MotionEvent
import android.view.View
import good.damn.kamchatka.views.interactions.interfaces.OnActionListener

open class BaseTouchInteraction
: View.OnTouchListener {

    companion object {
        private const val TAG = "BaseTouchInteraction"
    }

    private var mOnActionListener: OnActionListener? = null

    override fun onTouch(
        v: View?,
        event: MotionEvent?
    ): Boolean {
        Log.d(TAG, "onTouch: $v $event $mOnActionListener")
        if (event == null || v == null || mOnActionListener == null) {
            return false
        }

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                mOnActionListener!!.onDown(v,event)
            }

            MotionEvent.ACTION_UP -> {
                mOnActionListener!!.onUp(v,event)
            }
        }

        return true
    }

    open fun setOnActionListener(
        l: OnActionListener?
    ) {
        mOnActionListener = l
    }


}