package good.damn.tvlist.network.api.models

import good.damn.tvlist.extensions.extract
import good.damn.tvlist.extensions.extractArray
import org.json.JSONArray
import org.json.JSONObject

data class TVSearchResultChannels(
    val channels: JSONArray
) {

    companion object {
        fun createFromJSON(
            json: JSONObject
        ): TVSearchResultChannels? {

            val channels = json.extractArray(
                "channels"
            ) ?: return null

            return TVSearchResultChannels(
                channels
            )
        }
    }

}