package good.damn.tvlist.adapters.recycler_view

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.network.api.models.TVProgram
import good.damn.tvlist.view_holders.TVProgramViewHolder

class TVProgramsAdapter
: RecyclerView.Adapter<TVProgramViewHolder>() {

    var programs: Array<TVProgram>? = null

    var recyclerViewHeight: Int = 0
    var recyclerViewWidth: Int = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = TVProgramViewHolder.create(
        parent.context,
        recyclerViewWidth,
        recyclerViewHeight
    )

    override fun getItemCount(): Int {
        return programs?.size ?: 0
    }

    override fun onBindViewHolder(
        holder: TVProgramViewHolder,
        position: Int
    ) {
        if (programs == null) {
            return
        }
        holder.onBindViewHolder(
            programs!![position],
            if (position + 1 >= itemCount)
                null else programs!![position+1]
        )
    }
}