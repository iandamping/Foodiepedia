package com.ian.junemon.foodiepedia.data.local_data.all_food_category_detail

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 *
Created by Ian Damping on 17/06/2019.
Github = https://github.com/iandamping
 */
@Dao
interface LocalAllFoodCategoryDetailDao {
    @Query("SELECT * FROM all_food_category_detail")
    fun loadAllIngredientData(): LiveData<List<LocalAllFoodCategoryDetailData>>

//    @Query("SELECT * FROM all_food_category_detail WHERE localID = :id")
//    fun loadAllAllFoodCategoryDetaileDataById(id: Int?): LiveData<LocalAllFoodCategoryDetailData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFoodCategoryDetailData(inputAllFoodCategoryDetail: List<LocalAllFoodCategoryDetailData>?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAllFoodCategoryDetailData(updateAllFoodCategoryDetail: LocalAllFoodCategoryDetailData?)

    @Query("DELETE FROM all_food_category_detail")
    fun deleteAllData()

//    @Query("DELETE FROM all_food_category_detail where localID = :selectedId")
//    fun deleteSelectedId(selectedId: Int)
}