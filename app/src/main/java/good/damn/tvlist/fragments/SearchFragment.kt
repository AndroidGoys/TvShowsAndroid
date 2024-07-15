package good.damn.tvlist.fragments

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.adapters.recycler_view.TVSearchResultAdapter
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.leftParams
import good.damn.tvlist.extensions.normalWidth
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.network.api.services.TVSearchService
import good.damn.tvlist.views.buttons.ButtonBack
import good.damn.tvlist.views.decorations.MarginItemDecoration
import good.damn.tvlist.views.text_fields.TextFieldRound

class SearchFragment
: StackFragment(),
TextWatcher,
Runnable {

    companion object {
        private const val TAG = "SearchFragment"
    }

    private val mHandlerSearch = Handler(
        Looper.getMainLooper()
    )

    private val mSearchService = TVSearchService()

    private var mSearchResultAdapter: TVSearchResultAdapter? = null

    private var mSearchRequest = ""

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {
        val layout = FrameLayout(
            context
        )

        val topBar = FrameLayout(
            context
        ).apply {
            setBackgroundColor(0)

            boundsFrame(
                width = App.WIDTH,
                height = (getTopInset() +
                    measureUnit * 50.normalWidth()
                ).toInt()
            )
        }

        val searchField = TextFieldRound(
            context
        ).apply {

            boundsFrame(
                width = (measureUnit * 295.normalWidth()).toInt(),
                height = (measureUnit * 50.normalWidth()).toInt(),
                top = getTopInset().toFloat(),
                left = measureUnit * 66.normalWidth()
            )

            setTextSizePx(
                heightParams() * 0.29268f
            )

            typeface = App.font(
                R.font.open_sans_regular,
                context
            )

            strokeWidth = heightParams() * 0.02439f

            setTextColorId(
                R.color.text
            )
            strokeColor = App.color(
                R.color.lime
            )

            cornerRadius = heightParams() * 0.25f

            addTextChangedListener(
                this@SearchFragment
            )

            maxLines = 1
            minLines = 1
            inputType = InputType.TYPE_CLASS_TEXT

            val left = (widthParams() * 0.03728f)
                .toInt()
            setPadding(
                left,
                0,
                left,
                0
            )

            topBar.addView(
                this
            )
        }

        ButtonBack.createDefault(
            context
        ).apply {

            strokeColor = App.color(
                R.color.bigButtonIcon
            )

            setOnClickListener(
                this@SearchFragment::onClickBtnBack
            )

            val sh = searchField.heightParams()
            val h = (sh * 0.75f).toInt()
            val of = (sh - h) * 0.5f

            boundsFrame(
                top = getTopInset() + of,
                left = (searchField.leftParams() - h) * 0.5f,
                width = h,
                height = h
            )

            topBar.addView(
                this
            )
        }

        mSearchResultAdapter = TVSearchResultAdapter(
            (measureUnit *
                368.normalWidth()
                ).toInt(),
            mHolderHeight = (measureUnit *
                58.normalWidth()
                ).toInt()
        )

        val recyclerView = RecyclerView(
            context
        ).apply {
            setBackgroundColorId(
                R.color.background
            )

            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )

            val horizontal = (measureUnit *
                23.normalWidth()
            ).toInt()

            addItemDecoration(
                MarginItemDecoration(
                    horizontal,
                    (measureUnit * 16.normalWidth()).toInt(),
                    0,
                    0
                )
            )

            adapter = mSearchResultAdapter

            clipToPadding = false

            val pad = topBar.heightParams()
            setPadding(
                0,
                pad,
                0,
                pad
            )
        }

        layout.apply {
            addView(recyclerView)
            addView(topBar)
        }

        return layout
    }

    override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {
        // Check adapter validation ?
        if (s.isNullOrBlank()) {
            return
        }

        Log.d(TAG, "onTextChanged: $s")

        mHandlerSearch.removeCallbacks(
            this
        )
        mHandlerSearch.postDelayed(
            this,
            1250
        )
        mSearchRequest = s.toString()
    }

    override fun run() {

        Log.d(TAG, "run: ")
        mSearchResultAdapter?.cleanCurrentResult()

        mSearchService.getChannelsByName(
            mSearchRequest
        ) {
            if (it == null) {
                return@getChannelsByName
            }

            val adapter = mSearchResultAdapter
                ?: return@getChannelsByName

            adapter.addResult(
                it
            )

            App.ui {
                adapter.notifyDataSetChanged()
            }
        }
    }


    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) = Unit


    override fun afterTextChanged(
        s: Editable?
    ) = Unit
}

private fun SearchFragment.onClickBtnBack(
    view: View
) {
    popFragment(
        FragmentAnimation { f, fragment ->
            fragment.view?.x = App.WIDTH * -f
        }
    )
}