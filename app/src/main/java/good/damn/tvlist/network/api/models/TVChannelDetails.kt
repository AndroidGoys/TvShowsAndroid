package good.damn.tvlist.network.api.models

import good.damn.tvlist.extensions.extract
import good.damn.tvlist.extensions.extractArray
import good.damn.tvlist.extensions.getAt
import org.json.JSONObject

data class TVChannelDetails(
    val id: Int,
    val name: String,
    val description: String? = null,
    val rating: Float,
    val viewUrl: String?,
    val imageUrl: String? = null
) {

    companion object {
        fun createFromJSON(
            json: JSONObject
        ): TVChannelDetails? {

            val id = json.extract(
                "id"
            ) as? Int ?: return null

            val name = json.extract(
                "name"
            ) as? String ?: return null

            val desc = json.extract(
                "description"
            ) as? String

            val rating = (json.extract(
                "assessment"
            ) as? Double)?.toFloat() ?: 0.0f

            val viewUrl = json.extractArray(
                "viewUrls"
            )?.getAt(0) as? String

            val imageUrl = json.extract(
                "imageUrl"
            ) as? String

            return TVChannelDetails(
                id,
                name,
                desc,
                rating,
                viewUrl,
                imageUrl
            )

        }
    }

}