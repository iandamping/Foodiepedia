package com.ian.junemon.foodiepedia.core.presentation.view

import android.view.*
import androidx.fragment.app.FragmentActivity
import com.ian.junemon.foodiepedia.core.R
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ViewHelperImpl @Inject constructor() : ViewHelper {

    override fun fullScreenAnimation(activity: FragmentActivity) {
        with(activity) {
            overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

    }

    override fun animationOnly(activity: FragmentActivity) {
        activity.overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity)
    }

    override fun visible(view: View) {
        view.visibility = View.VISIBLE
    }

    override fun gone(view: View) {
        view.visibility = View.GONE
    }

    override fun inflates(layout: Int, viewGroup: ViewGroup): View {
        return with(viewGroup) {
            LayoutInflater.from(context).inflate(layout, this, false)
        }
    }
}