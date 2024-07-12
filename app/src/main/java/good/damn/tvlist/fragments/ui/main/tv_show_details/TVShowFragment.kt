package good.damn.tvlist.fragments.ui.main.tv_show_details

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.viewpager2.widget.ViewPager2
import good.damn.shaderblur.views.BlurShaderView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.adapters.FragmentAdapter
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.heightParams
import good.damn.tvlist.extensions.rgba
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.widthParams
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.fragments.animation.FragmentAnimation
import good.damn.tvlist.network.api.models.TVProgram
import good.damn.tvlist.utils.ViewUtils
import good.damn.tvlist.views.buttons.ButtonBack

class TVShowFragment
: StackFragment(),
View.OnClickListener {

    var program: TVProgram? = null

    private var mViewPager: ViewPager2? = null
    private var mBlurView: BlurShaderView? = null
    private var mTextViewTitle: AppCompatTextView? = null

    private val mShowPageFragment = TVShowPageFragment()
    private val mShowReviewsFragment = TVShowReviewsFragment()

    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {

        val layout = FrameLayout(
            context
        )
        val layoutTopBar = FrameLayout(
            context
        )
        mTextViewTitle = AppCompatTextView(
            context
        )

        mViewPager = ViewPager2(
            context
        )

        mShowPageFragment.program = program

        val viewPager = mViewPager
            ?: return View(context)

        ViewUtils.topBarStyleMain(
            layoutTopBar,
            measureUnit,
            getTopInset()
        )

        mBlurView = BlurShaderView(
            context,
            viewPager,
            blurRadius = 5,
            scaleFactor = 0.35f,
            shadeColor = App.color(
                R.color.background
            ).withAlpha(
                0.5f
            ).rgba()
        ).apply {
            boundsFrame(
                width = layoutTopBar.widthParams(),
                height = layoutTopBar.heightParams()
            )
            layoutTopBar.addView(this)
            startRenderLoop()
        }

        ButtonBack.createFrame(
            context,
            measureUnit
        ).apply {

            ViewUtils.topBarStyleBtnBack(
                layoutTopBar,
                this,
                getTopInset()
            )

            setOnClickListener(
                this@TVShowFragment::onClickBtnBack
            )

            layoutTopBar.addView(
                this
            )
        }

        mTextViewTitle?.apply {
            ViewUtils.topBarStyleTitle(
                layoutTopBar,
                this,
                getTopInset()
            )

            alpha = 0f

            text = program?.shortName ?: program?.name

            layoutTopBar.addView(
                this
            )
        }

        viewPager.apply {
            isUserInputEnabled = false

            setBackgroundColorId(
                R.color.background
            )

            val topInset = getTopInset() + layoutTopBar.heightParams()

            mShowPageFragment.topPadding = topInset

            adapter = FragmentAdapter(
                arrayOf(
                    mShowPageFragment,
                    mShowReviewsFragment
                ),
                childFragmentManager,
                lifecycle
            )
        }

        layout.apply {
            addView(viewPager)
            addView(layoutTopBar)
        }

        // Setup listeners
        mTextViewTitle?.let {
            var isShown = false
            val triggerY = App.HEIGHT * 0.42f
            mShowPageFragment.onScrollChangeListener = { scrollY ->
                if (!isShown && scrollY > triggerY) {
                    isShown = true
                    it.animate()
                        .alpha(1.0f)
                        .start()
                }

                if (isShown && scrollY < triggerY) {
                    isShown = false
                    it.animate()
                        .alpha(0.0f)
                        .start()
                }
            }
        }

        mShowPageFragment.onClickReviewsListener = this

        return layout
    }

    override fun onResume() {
        super.onResume()
        mBlurView?.apply {
            startRenderLoop()
            onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        mBlurView?.apply {
            stopRenderLoop()
            onPause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBlurView?.clean()
    }

    private fun onClickBtnBack(
        v: View
    ) {
        mViewPager?.apply {
            if (currentItem > 0) {
                currentItem--
                fadeInTextViewTitle()
                return
            }
        }

        popFragment(
            FragmentAnimation { f, fragment ->
                fragment.view?.alpha = 1.0f - f
            }
        )
    }

    // on click - show reviews
    override fun onClick(
        v: View?
    ) {
        mViewPager?.currentItem = 1
        fadeInTextViewTitle()
    }

    private fun fadeInTextViewTitle() {
        mTextViewTitle?.apply {
            if (alpha > 0.8f) {
                return
            }

            animate()
                .alpha(1.0f)
                .start()
        }
    }

    companion object {
        fun newInstance(
            program: TVProgram
        ) = TVShowFragment().apply {
            this.program = program
        }
    }
}