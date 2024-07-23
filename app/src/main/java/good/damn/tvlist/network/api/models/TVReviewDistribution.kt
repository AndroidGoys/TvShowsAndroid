package good.damn.tvlist.network.api.models

import good.damn.tvlist.extensions.extract
import good.damn.tvlist.extensions.extractObject
import org.json.JSONObject

data class TVReviewDistribution(
    val count: Int,
    val distribution: Array<TVDistribution>
) {
    companion object {
        fun createFromJSON(
            json: JSONObject
        ): TVReviewDistribution? {
            val dist = json.extractObject(
                "distribution"
            ) ?: return null

            var sum = 0
            val distArr = Array(5) {
                val key = (5 - it).toString()
                val keyCount = dist.extract(
                    key
                ) as? Int ?: 0

                sum += keyCount

                TVDistribution(
                    key,
                    keyCount
                )
            }

            return TVReviewDistribution(
                sum,
                distArr
            )
        }
    }
}