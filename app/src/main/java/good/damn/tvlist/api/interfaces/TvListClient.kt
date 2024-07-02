package good.damn.tvlist.api.interfaces

import good.damn.tvlist.api.interfaces.enteties.channels.TvChannelDetails
import good.damn.tvlist.api.interfaces.enteties.channels.TvChannels
import good.damn.tvlist.api.interfaces.enteties.shows.TvShowDetails
import good.damn.tvlist.api.interfaces.enteties.shows.TvShows

interface TvListClient {
    fun getChannels(callback: ()->Result<TvChannels>)
    fun getChannelById(id:Int, callback: ()->Result<TvChannelDetails>)
    fun getTvShows(callback: () -> Result<TvShows>)
    fun getShowById(id:Int, callback: ()->Result<TvShowDetails>)
}