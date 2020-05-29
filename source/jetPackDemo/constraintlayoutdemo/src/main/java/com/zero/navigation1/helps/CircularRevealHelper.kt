package com.zero.navigation1.helps

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.math.hypot

class CircularRevealHelper @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintHelper(context, attrs, defStyleAttr) {

    override fun updatePostLayout(container: ConstraintLayout) {
        super.updatePostLayout(container)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val views = getViews(container)
            for (view in views) {
                val anim = ViewAnimationUtils.createCircularReveal(view, view.width / 2,
                        view.height / 2, 0f,
                        hypot((view.height / 2).toDouble(), (view.width / 2).toDouble()).toFloat())
                anim.duration = 3000
                anim.start()
            }
        }
    }
}