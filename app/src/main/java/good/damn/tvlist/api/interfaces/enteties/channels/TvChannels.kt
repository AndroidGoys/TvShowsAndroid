package good.damn.tvlist.api.interfaces.enteties.channels

import androidx.appcompat.view.ActionMode.Callback
import good.damn.tvlist.api.interfaces.enteties.shows.TvShows

interface TvChannels<out T> where T : TvChannel{
    val current: Iterable<T>
    fun moveNext(callback: () -> Result<Boolean>)
}