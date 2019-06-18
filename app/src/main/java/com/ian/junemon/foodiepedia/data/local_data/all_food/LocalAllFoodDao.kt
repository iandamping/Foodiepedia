package com.ian.junemon.foodiepedia.data.local_data.all_food

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 *
Created by Ian Damping on 17/06/2019.
Github = https://github.com/iandamping
 */
@Dao
interface LocalAllFoodDao {
    @Query("SELECT * FROM all_food_data")
    fun loadAllIngredientData(): LiveData<List<LocalAllFoodData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFoodData(inputAllFood: List<LocalAllFoodData>?)

    @Query("DELETE FROM all_food_data")
    fun deleteAllData()

}