package good.damn.tvlist.network.api.models

import good.damn.tvlist.extensions.extractArray
import org.json.JSONArray
import org.json.JSONObject

data class TVSearchResultShows(
    val shows: JSONArray
) {

    companion object {
        fun createFromJSON(
            json: JSONObject
        ): TVSearchResultShows? {

            val channels = json.extractArray(
                "shows"
            ) ?: return null

            return TVSearchResultShows(
                channels
            )
        }
    }

}