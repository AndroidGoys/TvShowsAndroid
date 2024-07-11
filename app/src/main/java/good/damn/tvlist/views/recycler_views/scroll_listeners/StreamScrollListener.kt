package good.damn.tvlist.views.recycler_views.scroll_listeners

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import good.damn.tvlist.App
import good.damn.tvlist.views.recycler_views.scroll_listeners.interfaces.OnUpdateStreamDataListener

class StreamScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val mHeight: Int
) : RecyclerView.OnScrollListener() {

    companion object {
        private const val TAG = "StreamScrollListener"
    }

    var onUpdateDataStreamDataListener: OnUpdateStreamDataListener? = null

    var deltaPositionStream = 4
    var updateCount = 4

    private var mLoading = false

    private var mFirstVisibleItem = 0
    private var mVisibleItemCount = 0
    private var mTotalItemCount = 0

    private var mPreviousTotal = 0

    private var mScrolledY = 0

    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int
    ) {
        super.onScrolled(
            recyclerView,
            dx,
            dy
        )

        mScrolledY += dy

        mFirstVisibleItem = mScrolledY / mHeight
        mTotalItemCount = layoutManager.itemCount
        mVisibleItemCount = recyclerView.childCount

        if (mLoading) {
            if (mTotalItemCount > mPreviousTotal || !App.NETWORK_AVAILABLE) {
                mLoading = false
            }
            return
        }

        if ((mTotalItemCount - mFirstVisibleItem) <= deltaPositionStream) {
            mLoading = true
            mPreviousTotal = mTotalItemCount
            onUpdateDataStreamDataListener?.onUpdateDataStream(
                mTotalItemCount
            )
        }
    }

}