package good.damn.tvlist.api.interfaces.enteties.channels

import good.damn.tvlist.api.interfaces.enteties.TvEntity
import good.damn.tvlist.api.interfaces.enteties.shows.TvShows

interface TvChannel : TvEntity {
    val name: String
    val imageUrl: String

    fun getShows(callback: () -> Result<TvShows>)
    fun getDetails(callback: () -> Result<TvChannelDetails>)
}