package good.damn.tvlist.api.interfaces

import good.damn.tvlist.api.interfaces.enteties.channels.TvChannelDetails
import good.damn.tvlist.api.interfaces.enteties.channels.TvChannelPreview
import good.damn.tvlist.api.interfaces.enteties.channels.TvChannels
import good.damn.tvlist.api.interfaces.enteties.shows.TvShowDetails
import good.damn.tvlist.api.interfaces.enteties.shows.TvShows
import good.damn.tvlist.api.interfaces.enteties.shows.TvShowPreview

interface TvListClient {
    fun getChannels(callback: (result: Result<TvChannels<TvChannelPreview>>)->Unit)
    fun getChannelById(id:Int, callback: (result: Result<TvChannelDetails>)->Unit)
    fun getTvShows(callback: (result: Result<TvShows<TvShowPreview>>)->Unit)
    fun getShowById(id:Int, callback: (result: Result<TvShowDetails>)->Unit)

    fun searchChannelsByName(name: String, callback: (result: Result<TvChannels<TvChannelPreview>>)->Unit)
    fun searchShowsByName(name: String, callback: (result: Result<TvShows<TvShowPreview>>)->Unit)
}