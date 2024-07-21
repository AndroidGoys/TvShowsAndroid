package good.damn.tvlist.views.program

import android.view.View
import good.damn.tvlist.network.api.models.TVChannelRelease

interface OnClickChannelReleaseListener {

    fun onClickChannelRelease(
        view: View,
        release: TVChannelRelease?
    )

}