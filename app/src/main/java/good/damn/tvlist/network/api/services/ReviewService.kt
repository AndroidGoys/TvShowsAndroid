package good.damn.tvlist.network.api.services

import android.util.Log
import androidx.annotation.WorkerThread
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.extensions.extractArray
import good.damn.tvlist.models.Result
import good.damn.tvlist.network.NetworkJSONService
import good.damn.tvlist.network.api.models.TVReviewDistribution
import good.damn.tvlist.network.api.models.user.TVUserReview
import org.json.JSONObject

class ReviewService(
    id: Long,
    type: String
): NetworkJSONService(
    App.CACHE_DIR
) {

    private val mUrlReviews = "${App.URL}/api/$type/$id/reviews"
    private val mUrlDistros = "$mUrlReviews/distribution"
    private val mUrlUserReview = "$mUrlReviews/@my"

    companion object {
        private const val TAG = "ReviewService"
    }

    @WorkerThread
    fun getDistributionReviews(
        fromCache: Boolean = false
    ): TVReviewDistribution? {
        val response = if (fromCache)
            getCachedJson(mUrlDistros)
        else getNetworkJSON(mUrlDistros)

        Log.d(TAG, "getDistributionReviews: $response")

        if (response == null) {
            return null
        }

        return TVReviewDistribution.createFromJSON(
            response
        )
    }

    @WorkerThread
    fun getReviews(
        fromCache: Boolean = false,
        rating: Byte = 0
    ): ArrayList<TVUserReview>? {
        val url = if (rating <= 0 || rating > 5)
            mUrlReviews
        else "$mUrlReviews?assessment=$rating"

        val response = if (fromCache)
            getCachedJson(url)
        else getNetworkJSON(url)

        Log.d(TAG, "getReviews: RESPONSE: $response")

        if (response == null) {
            return null
        }

        val comments = response.extractArray(
            "comments"
        ) ?: return null

        val reviews = ArrayList<TVUserReview>(
            comments.length()
        )

        for (i in 0..<comments.length()) {
            val comment = TVUserReview.createFromJSON(
                comments.getJSONObject(i)
            ) ?: return null

            reviews.add(
                comment
            )
        }

        return reviews
    }


    @WorkerThread
    fun getUserReview(
        fromCache: Boolean = false
    ): Result<TVUserReview> {
        val json = if (fromCache)
            getCachedJson(mUrlUserReview)
        else getNetworkJSON(mUrlUserReview)

        Log.d(TAG, "getUserReview: $json ${App.TOKEN_AUTH?.accessToken}")

        if (json == null) {
            return Result()
        }

        val model = TVUserReview.createFromJSON(
            json
        ) ?: return Result(
            errorStringId = R.string.invalid_object
        )

        return Result(
            model
        )
    }

    @WorkerThread
    fun deleteUserReview(): Result<Void> {
        val response = execute(
            makeRequestDelete(
                mUrlUserReview
            )
        ) ?: return Result(
            errorStringId = R.string.invalid_response
        )

        if (response.code != 200) {
            return Result(
                errorStringId = R.string.invalid_response_object
            )
        }

        return Result()
    }

    @WorkerThread
    fun postReview(
        grade: Byte,
        comment: String = ""
    ): Result<TVUserReview> {
        val response = requestPostJSON(
            mUrlReviews,
            JSONObject().apply {
                put("assessment", grade.toInt())
                put("text", comment)
            }
        )

        Log.d(TAG, "postReview: ${response.result}")

        if (response.errorStringId != -1) {
            return Result(
                errorStringId = response.errorStringId
            )
        }

        return Result()
    }


}