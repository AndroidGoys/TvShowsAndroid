package good.damn.tvlist.network.api.models.show

import good.damn.tvlist.extensions.extract
import good.damn.tvlist.extensions.toGregorianDateString
import org.json.JSONObject

data class TVUserReview(
    val userId: Long,
    val rating: Byte,
    val dateString: String,
    val textReview: String? = null
) {

    companion object {
        fun createFromJSON(
            json: JSONObject
        ): TVUserReview? {

            val userId = json.extract(
                "userId"
            ) as? Int ?: return null

            val rating = (json.extract(
                "assessment"
            ) as? Int)?.toByte() ?: 1

            val date = json.extract(
                "date"
            ) as? Int ?: 0

            val text = json.extract(
                "text"
            ) as? String

            return TVUserReview(
                userId.toLong(),
                rating,
                date.toGregorianDateString(),
                text
            )

        }
    }

}