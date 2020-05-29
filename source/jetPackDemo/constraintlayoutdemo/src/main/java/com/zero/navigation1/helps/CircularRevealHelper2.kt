package com.zero.navigation1.helps

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import androidx.constraintlayout.helper.widget.Layer
import androidx.constraintlayout.widget.ConstraintLayout

class CircularRevealHelper2 @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Layer(context, attrs, defStyleAttr) {

    override fun updatePostLayout(container: ConstraintLayout) {
        super.updatePostLayout(container)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val anim = ViewAnimationUtils.createCircularReveal(this, (left + right) / 2,
                    (top + bottom) / 2, 0f,
                    Math.hypot(((left + right) / 2 - left).toDouble(), ((top + bottom) / 2 - top).toDouble()).toFloat())
            anim.duration = 3000
            anim.start()
        }
    }

}