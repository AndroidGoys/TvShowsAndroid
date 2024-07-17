package good.damn.tvlist.network.api.services

import androidx.annotation.WorkerThread
import good.damn.tvlist.App
import good.damn.tvlist.Unicode
import good.damn.tvlist.enums.SearchResultCategory
import good.damn.tvlist.interfaces.Typeable
import good.damn.tvlist.models.TVSearchResultTitle
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.TVChannel2
import good.damn.tvlist.network.api.models.TVSearchResult
import good.damn.tvlist.network.api.models.TVSearchResultChannels
import good.damn.tvlist.network.api.models.TVSearchResultShows
import good.damn.tvlist.network.api.models.TVShow
import org.json.JSONObject
import java.lang.reflect.Type
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class TVSearchService
: NetworkJSONService(
    App.CACHE_DIR
) {
    companion object {
        private const val TAG = "TVSearchService"
    }

    private val mChannelsService = TVChannel2Service()
    private val mShowsService = TVShowService()

    @WorkerThread
    fun getShowsByName(
        name: String,
        fromCache: Boolean = false
    ): ArrayList<Typeable?>? {

        val shows = mShowsService.getShows(
            name,
            0,
            25,
            fromCache
        )

        if (shows == null) {
            return null
        }

        val types = ArrayList<Typeable?>(
            shows.size + 1
        )

        types.add(
            TVSearchResultTitle(
                "Шоу"
            )
        )

        shows.forEach {
            types.add(
                TVSearchResult(
                    it.shortName ?: it.name,
                    it.previewUrl,
                    SearchResultCategory.SHOW,
                    it
                )
            )
        }

        return types
    }

    @WorkerThread
    fun getChannelsByName(
        name: String,
        fromCache: Boolean = false
    ): ArrayList<Typeable?>? {
        val channels = mChannelsService.getChannels(
            offset = 0,
            limit = 10,
            name,
            fromCache
        )

        if (channels == null) {
            return null
        }

        val types = ArrayList<Typeable?>(
            channels.size + 1
        )

        types.add(
            TVSearchResultTitle(
                "Каналы"
            )
        )

        channels.forEach {
            types.add(
                TVSearchResult(
                    it.shortName ?: it.name,
                    it.imageUrl,
                    SearchResultCategory.CHANNEL,
                    it
                )
            )
        }

        return types
    }

}