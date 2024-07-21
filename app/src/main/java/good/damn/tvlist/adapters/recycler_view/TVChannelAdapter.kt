package good.damn.tvlist.adapters.recycler_view

import android.util.Log
import android.view.ViewGroup
import androidx.annotation.WorkerThread
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.network.api.models.TVChannel
import good.damn.tvlist.network.api.models.TVChannel2
import good.damn.tvlist.network.api.services.TVChannelReleasesService
import good.damn.tvlist.view_holders.TVChannelViewHolder

class TVChannelAdapter(
    private val width: Int,
    private val height: Int,
    private val data: ArrayList<TVChannel2>,
    private val mReleaseService: TVChannelReleasesService
): RecyclerView.Adapter<TVChannelViewHolder>() {

    companion object {
        private const val TAG = "TVChannelAdapter"
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = TVChannelViewHolder.create(
        parent.context,
        width,
        height
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(
        holder: TVChannelViewHolder,
        position: Int
    ) = holder.onBindViewHolder(
        data[position],
        mReleaseService
    )

    @WorkerThread
    fun updateData(
        from: Int,
        data: ArrayList<TVChannel?>
    ) {
        /*for (i in 0..<data.size) {
            Log.d(TAG, "updateData: $from $i ${data.size} ${this.data.size}")
            this.data[from+i] = data[i]
        }*/
    }

    @WorkerThread
    fun addChannels(
        t: ArrayList<TVChannel?>
    ) {
        //data.addAll(t)
    }
}