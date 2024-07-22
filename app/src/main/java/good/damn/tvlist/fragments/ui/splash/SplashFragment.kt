package good.damn.tvlist.fragments.ui.splash

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import good.damn.tvlist.App
import good.damn.tvlist.R
import good.damn.tvlist.animators.SimpleAnimator
import good.damn.tvlist.extensions.bottomMargin
import good.damn.tvlist.extensions.boundsFrame
import good.damn.tvlist.extensions.setBackgroundColorId
import good.damn.tvlist.extensions.setTextSizePx
import good.damn.tvlist.extensions.withAlpha
import good.damn.tvlist.fragments.StackFragment
import good.damn.tvlist.views.CircleView
import good.damn.tvlist.views.GroupViewAnimation
import good.damn.tvlist.views.spell.SpellView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.math.sqrt

class SplashFragment
: StackFragment() {

    var onAnimationEnd: (()->Unit)? = null

    companion object {
        private const val TAG = "SplashFragment"
    }
    
    override fun onCreateView(
        context: Context,
        measureUnit: Int
    ): View {

        val layout = FrameLayout(
            context
        )
        val circleView = CircleView(
            context
        )
        val textViewAppName = SpellView(
            context
        )
        val textViewCompany = TextView(
            context
        )
        val textViewPowered = TextView(
            context
        )

        textViewCompany.alpha = 0f
        textViewPowered.alpha = 0f

        App.font(
            R.font.open_sans_extra_bold,
            context
        )?.let { extraBold ->
            textViewAppName.typeface = extraBold
            textViewCompany.typeface = extraBold
            textViewPowered.typeface = extraBold
        }


        // Text
        textViewAppName.text = context.getString(
            R.string.app_name
        ).uppercase()

        textViewCompany.setText(
            R.string.limeX
        )
        textViewPowered.setText(
            R.string.powered
        )

        circleView.circleColor = App.color(
            R.color.lime
        )

        App.color(
            R.color.background
        ).let {
            layout.setBackgroundColor(
                it
            )
        }

        App.color(
            R.color.accentColor
        ).let {
            textViewAppName.textColor = it
            textViewCompany.setTextColor(
                it
            )
            textViewPowered.setTextColor(
                it.withAlpha(0.42f)
            )
        }


        // Text Size
        textViewAppName.textSize = measureUnit * 0.1111f
        textViewCompany.setTextSizePx(
            measureUnit * 0.0966f
        )
        textViewPowered.setTextSizePx(
            measureUnit * 0.02898f
        )





        // Bounds
        textViewAppName.boundsFrame(
            Gravity.CENTER,
            width = measureUnit,
            height = textViewAppName.textSize.toInt()
        )
        textViewPowered.boundsFrame(
            Gravity.CENTER_HORIZONTAL or
                Gravity.BOTTOM,
            bottom = measureUnit * 0.13043f
        )
        textViewCompany.boundsFrame(
            Gravity.CENTER_HORIZONTAL or
                Gravity.BOTTOM,
            bottom = textViewPowered.bottomMargin()
                + textViewPowered.textSize
        )


        layout.apply {
            addView(
                circleView
            )
            addView(
                textViewAppName
            )
            addView(
                textViewCompany
            )
            addView(
                textViewPowered
            )
        }


        val halfWidth = App.WIDTH * 0.53f
        val halfHeight = App.HEIGHT * 0.53f

        val length = sqrt(
            halfWidth * halfWidth + halfHeight * halfHeight
        )

        val animator = SimpleAnimator(
            circleView
        )

        animator.onFrameUpdate = { factor, view ->
            circleView.circleRadius = length * factor
            circleView.invalidate()
        }

        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = 1550

        animator.onEndAnimation = { view ->
            GroupViewAnimation().apply {
                addView(textViewCompany)
                addView(textViewPowered)
                onFrameUpdate = { factor, view ->
                    view.alpha = factor
                }
                onAnimationEnd = {
                    Handler(Looper.getMainLooper()).postDelayed(
                        {this@SplashFragment.onAnimationEnd?.invoke()},
                        1000
                    )
                }
                interpolator = AccelerateDecelerateInterpolator()
                duration = 1250
                start()
            }
        }

        textViewAppName.startAnimation()
        animator.start()

        return layout
    }



}