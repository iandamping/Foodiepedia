package com.example.junemon.foodapi_mvvm.ui.detail

import androidx.lifecycle.Observer
import com.example.junemon.foodapi_mvvm.base.BasePresenter
import com.example.junemon.foodapi_mvvm.base.OnComplete
import com.example.junemon.foodapi_mvvm.base.OnShowDetailFoodData
import com.example.junemon.foodapi_mvvm.data.viewmodel.DetailFoodViewModel
import com.example.junemon.foodapi_mvvm.model.DetailFood

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class DetailFoodPresenter(private val vm: DetailFoodViewModel) : BasePresenter<DetailFoodView>() {
    private var listIngredient: MutableList<String> = mutableListOf()
    private var listMeasurement: MutableList<String> = mutableListOf()

    override fun onCreate() {
        view()?.initView()

    }

    fun setData(passedData: String?) {
        passedData?.let { vm.setDetailFoodData(it) }
        vm.liveDataState.observe(getLifeCycleOwner(), Observer {
            when (it) {
                is OnShowDetailFoodData -> {
                    view()?.showDetailData(it.data[0])
                    extractData(it.data[0])
                }
                is OnComplete -> setDialogShow(it.show)
            }
        })
    }


    private fun extractData(data: DetailFood.Meal) {
        listIngredient.run {
            if (!data.strIngredient1.equals("")) data.strIngredient1?.let { add(it) }
            if (!data.strIngredient2.equals("")) data.strIngredient2?.let { add(it) }
            if (!data.strIngredient3.equals("")) data.strIngredient3?.let { add(it) }
            if (!data.strIngredient4.equals("")) data.strIngredient4?.let { add(it) }
            if (!data.strIngredient5.equals("")) data.strIngredient5?.let { add(it) }
            if (!data.strIngredient6.equals("")) data.strIngredient6?.let { add(it) }
            if (!data.strIngredient7.equals("")) data.strIngredient7?.let { add(it) }
            if (!data.strIngredient8.equals("")) data.strIngredient8?.let { add(it) }
            if (!data.strIngredient9.equals("")) data.strIngredient9?.let { add(it) }
            if (!data.strIngredient10.equals("")) data.strIngredient10?.let { add(it) }
            if (!data.strIngredient11.equals("")) data.strIngredient11?.let { add(it) }
            if (!data.strIngredient12.equals("")) data.strIngredient12?.let { add(it) }
            if (!data.strIngredient13.equals("")) data.strIngredient13?.let { add(it) }
            if (!data.strIngredient14.equals("")) data.strIngredient14?.let { add(it) }
            if (!data.strIngredient15.equals("")) data.strIngredient15?.let { add(it) }
            if (!data.strIngredient16.equals("")) data.strIngredient16?.let { add(it) }
            if (!data.strIngredient17.equals("")) data.strIngredient17?.let { add(it) }
            if (!data.strIngredient18.equals("")) data.strIngredient18?.let { add(it) }
            if (!data.strIngredient19.equals("")) data.strIngredient19?.let { add(it) }
            if (!data.strIngredient20.equals("")) data.strIngredient20?.let { add(it) }
        }
        listMeasurement.run {
            if (!data.strMeasure1.equals("") && !data.strMeasure1.equals(" ")) data.strMeasure1?.let { add(it) }
            if (!data.strMeasure2.equals("") && !data.strMeasure2.equals(" ")) data.strMeasure2?.let { add(it) }
            if (!data.strMeasure3.equals("") && !data.strMeasure3.equals(" ")) data.strMeasure3?.let { add(it) }
            if (!data.strMeasure4.equals("") && !data.strMeasure4.equals(" ")) data.strMeasure4?.let { add(it) }
            if (!data.strMeasure5.equals("") && !data.strMeasure5.equals(" ")) data.strMeasure5?.let { add(it) }
            if (!data.strMeasure6.equals("") && !data.strMeasure6.equals(" ")) data.strMeasure6?.let { add(it) }
            if (!data.strMeasure7.equals("") && !data.strMeasure7.equals(" ")) data.strMeasure7?.let { add(it) }
            if (!data.strMeasure8.equals("") && !data.strMeasure8.equals(" ")) data.strMeasure8?.let { add(it) }
            if (!data.strMeasure9.equals("") && !data.strMeasure9.equals(" ")) data.strMeasure9?.let { add(it) }
            if (!data.strMeasure10.equals("") && !data.strMeasure10.equals(" ")) data.strMeasure10?.let { add(it) }
            if (!data.strMeasure11.equals("") && !data.strMeasure11.equals(" ")) data.strMeasure11?.let { add(it) }
            if (!data.strMeasure12.equals("") && !data.strMeasure12.equals(" ")) data.strMeasure12?.let { add(it) }
            if (!data.strMeasure13.equals("") && !data.strMeasure13.equals(" ")) data.strMeasure13?.let { add(it) }
            if (!data.strMeasure14.equals("") && !data.strMeasure14.equals(" ")) data.strMeasure14?.let { add(it) }
            if (!data.strMeasure15.equals("") && !data.strMeasure15.equals(" ")) data.strMeasure15?.let { add(it) }
            if (!data.strMeasure16.equals("") && !data.strMeasure16.equals(" ")) data.strMeasure16?.let { add(it) }
            if (!data.strMeasure17.equals("") && !data.strMeasure17.equals(" ")) data.strMeasure17?.let { add(it) }
            if (!data.strMeasure18.equals("") && !data.strMeasure18.equals(" ")) data.strMeasure18?.let { add(it) }
            if (!data.strMeasure19.equals("") && !data.strMeasure19.equals(" ")) data.strMeasure19?.let { add(it) }
            if (!data.strMeasure20.equals("") && !data.strMeasure20.equals(" ")) data.strMeasure20?.let { add(it) }
        }

        view()?.showIngredientData(listIngredient, listMeasurement)


    }
}