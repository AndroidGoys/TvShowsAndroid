package good.damn.tvlist.fragments.ui.main.stream

import android.content.Context
import android.view.View
import android.webkit.WebView
import android.widget.AbsoluteLayout
import good.damn.tvlist.fragments.StackFragment

class StreamFragment
: StackFragment() {

    companion object {
        private const val TAG = "StreamFragment"

        fun newInstance(
            url: String
        ) = StreamFragment().apply {
            this.url = url
        }
    }

    var url: String? = null

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {

        val url = url

        if (url.isNullOrBlank()) {
            return View(context)
        }

        val webView = WebView(
            context
        )

        webView.loadUrl(url)

        return webView
    }

}