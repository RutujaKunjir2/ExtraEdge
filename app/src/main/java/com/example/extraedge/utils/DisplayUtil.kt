package com.example.extraedge.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.SystemClock
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import com.example.extraedge.R
import java.text.NumberFormat
import java.util.*

object DisplayUtil {
    var mLastClickTime: Long = 0
    // Prevents multiple clicks in succession
    val isInProgress: Boolean
        get() {
            val time = SystemClock.elapsedRealtime()
            if (time  - mLastClickTime < 900){
                // Multiple clicks in succession
                return true // Debounce
            }
            mLastClickTime = SystemClock.elapsedRealtime() // Proceed to action
            return false
        }

    fun dpToPx(dp: Int): Int {
        val metrics = Resources.getSystem().displayMetrics
        return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun formatLikes(amount: Long): String { /* DecimalFormat formatter = new DecimalFormat("#,###.00");
      return formatter.format(amount);*/
        return NumberFormat.getNumberInstance(Locale.US).format(amount)
    }

    fun setMargins(
        view: View,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        if (view.layoutParams is MarginLayoutParams) {
            val p = view.layoutParams as MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            view.requestLayout()
        }
    }

    fun generateLikeCount(id: Int, titleLength: Int, isPremium: Boolean): String {
        val milli = System.currentTimeMillis()
        return if (isPremium) {
            formatLikes(milli / 99999999 / titleLength * id)
        } else formatLikes(milli / 999999999 / titleLength * id)
    }

    fun enableDisableView(view: View, enabled: Boolean) {
        view.isEnabled = enabled
        if (view is ViewGroup) {
            val group = view
            for (idx in 0 until group.childCount) {
                enableDisableView(
                    group.getChildAt(
                        idx
                    ), enabled
                )
            }
        }
    }

    fun applyStatusBarColor(activity: Activity, color: Int) {
        //changing status bar color
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.statusBarColor = ContextCompat.getColor(activity, color)
    }

    fun slideIn(c: Context?, v: View) {
        slide(c, v, true)
    }

    fun slideInFast(c: Context?, v: View) {
        slideFast(c, v, true)
    }

    fun slideOut(c: Context?, v: View) {
        slide(c, v, false)
    }
    fun slideOutFast(c: Context?, v: View) {
        slideFast(c, v, false)
    }

    fun zoomIn(c: Context?, v: View) {
        zoom(c, v, true)
    }

    fun zoomOut(c: Context?, v: View) {
        zoom(c, v, false)
    }

    fun slide(
        c: Context?,
        v: View,
        isSlideIn: Boolean
    ) {
        var slide_anim = R.anim.slide_up_anim
        if (!isSlideIn) {
            slide_anim = R.anim.slide_down_anim
        }
        val animation = AnimationUtils.loadAnimation(
            c,
            slide_anim
        )
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            @SuppressLint("RestrictedApi")
            override fun onAnimationEnd(animation: Animation) {
                v.clearAnimation()
                v.visibility = if (isSlideIn) View.VISIBLE else View.GONE
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        v.startAnimation(animation)
    }

    fun slideFast(
        c: Context?,
        v: View,
        isSlideIn: Boolean
    ) {
        var slide_anim = R.anim.slide_up_anim_fast
        if (!isSlideIn) {
            slide_anim = R.anim.slide_down_anim_fast
        }
        val animation = AnimationUtils.loadAnimation(
            c,
            slide_anim
        )
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            @SuppressLint("RestrictedApi")
            override fun onAnimationEnd(animation: Animation) {
                v.clearAnimation()
                v.visibility = if (isSlideIn) View.VISIBLE else View.GONE
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        v.startAnimation(animation)
    }

    fun zoom(
        c: Context?,
        v: View,
        isZoomIn: Boolean
    ) {
        var slide_anim = R.anim.zoom_in
        if (!isZoomIn) {
            slide_anim = R.anim.zoom_out
        }
        val animation = AnimationUtils.loadAnimation(
            c,
            slide_anim
        )
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            @SuppressLint("RestrictedApi")
            override fun onAnimationEnd(animation: Animation) {
                v.visibility = if (isZoomIn) View.VISIBLE else View.GONE
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        v.startAnimation(animation)
    }
}