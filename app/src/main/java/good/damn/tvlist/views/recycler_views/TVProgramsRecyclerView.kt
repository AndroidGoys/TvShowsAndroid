package good.damn.tvlist.views.recycler_views

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.adapters.recycler_view.TVProgramsAdapter
import good.damn.tvlist.network.api.models.TVProgram

class TVProgramsRecyclerView(
    context: Context
): RecyclerView(
    context
) {

    val adapterPrograms = TVProgramsAdapter()

    var heightHolder: Int = 1
        set(v) {
            field = v
            adapterPrograms.recyclerViewHeight = v
        }
    var programs: Array<TVProgram>? = null
        set(v) {
            field = v
            adapterPrograms.programs = v
            adapterPrograms.notifyDataSetChanged()
        }

    override fun setLayoutParams(
        params: ViewGroup.LayoutParams?
    ) {
        if (params == null) {
            return
        }
        adapterPrograms.recyclerViewWidth = params.width
        super.setLayoutParams(params)
    }

}