package good.damn.tvlist.network.api.services

import android.util.Log
import good.damn.tvlist.App
import good.damn.tvlist.interfaces.Typeable
import good.damn.tvlist.models.TVSearchResultTitle
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.TVChannel2
import good.damn.tvlist.network.api.models.TVSearchResult
import good.damn.tvlist.network.api.models.TVSearchResultChannels
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class TVSearchService
: NetworkJSONService(
    App.CACHE_DIR
) {
    companion object {
        private const val TAG = "TVSearchService"
        private const val URL = "http://176.109.106.211:8080/api/channels"
    }

    fun getChannelsByName(
        name: String,
        completionBackground: (ArrayList<Typeable?>?) -> Unit
    ) {
        searchRequest(
            name,
            URL
        ) {
            if (it == null || it.length() == 0) {
                completionBackground(null)
                return@searchRequest
            }

            val chan = TVSearchResultChannels.createFromJSON(
                it
            )

            if (chan == null) {
                completionBackground(null)
                return@searchRequest
            }

            val len = chan.channels.length()

            val channels = ArrayList<Typeable?>(
                len + 1
            )

            channels.add(
                TVSearchResultTitle(
                    "Каналы"
                )
            )

            for (i in 0..<len) {
                val channel = TVChannel2.createFromJSON(
                    chan.channels.getJSONObject(i)
                ) ?: continue

                channels.add(
                    TVSearchResult(
                        channel.name,
                        channel.imageUrl
                    )
                )
            }

            completionBackground(
                channels
            )
        }
    }

    private fun searchRequest(
        name: String,
        url: String,
        completionBackground: (JSONObject?) -> Unit
    ) {
        App.IO.launch {
            val encodedName = URLEncoder.encode(
                name,
                StandardCharsets.UTF_8.name()
            )
            getJSON(
                "$url?limit=5&offset=0&name=$encodedName"
            ) {
                completionBackground(
                    it
                )
            }
        }
    }

}