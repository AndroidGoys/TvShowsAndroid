package good.damn.tvlist.api.interfaces.enteties.channels

import androidx.appcompat.view.ActionMode.Callback
import good.damn.tvlist.api.interfaces.enteties.shows.TvShows

interface TvChannels {
    val current: Iterable<TvShows>
    fun moveNext(callback: () -> Result<Boolean>)
}