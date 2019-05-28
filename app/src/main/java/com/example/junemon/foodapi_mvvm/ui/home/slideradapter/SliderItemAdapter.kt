package com.example.junemon.foodapi_mvvm.ui.home.slideradapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.model.AllFood
import com.example.junemon.foodapi_mvvm.ui.detail.DetailFoodActivity
import com.example.junemon.foodapi_mvvm.util.Constant
import com.example.junemon.foodapi_mvvm.util.inflates
import com.example.junemon.foodapi_mvvm.util.loadUrl
import com.example.junemon.foodapi_mvvm.util.startActivity
import kotlinx.android.synthetic.main.item_slider.view.*

/**
 *
Created by Ian Damping on 16/04/2019.
Github = https://github.com/iandamping
 */
class SliderItemAdapter(private val data: List<AllFood.Meal>, private val ctx: Context?) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val views = container.inflates(R.layout.item_slider)
        views.ivSliderImage.loadUrl(data[position].strMealThumb)
        views.ivSliderImage?.setOnClickListener {

            ctx?.startActivity<DetailFoodActivity> {
                putExtra(Constant.intentDetailKey, this@SliderItemAdapter.data[position].idMeal)

            }
        }
        container.addView(views)
        return views
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int = data.size
}