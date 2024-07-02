package good.damn.tvlist.api.interfaces

import good.damn.tvlist.api.interfaces.enteties.channels.TvChannelDetails
import good.damn.tvlist.api.interfaces.enteties.channels.TvChannelPreview
import good.damn.tvlist.api.interfaces.enteties.channels.TvChannels
import good.damn.tvlist.api.interfaces.enteties.shows.TvShowDetails
import good.damn.tvlist.api.interfaces.enteties.shows.TvShows
import good.damn.tvlist.api.interfaces.enteties.shows.TvShowPreview

interface TvListClient {
    fun getChannels(callback: ()->Result<TvChannels<TvChannelPreview>>)
    fun getChannelById(id:Int, callback: ()->Result<TvChannelDetails>)
    fun getTvShows(callback: () -> Result<TvShows<TvShowPreview>>)
    fun getShowById(id:Int, callback: ()->Result<TvShowDetails>)

    fun searchChannelsByName(name: String, callback: ()->Result<TvChannels<TvChannelPreview>>)
    fun searchShowsByName(name: String, callback: ()->Result<TvShows<TvShowPreview>>)
}