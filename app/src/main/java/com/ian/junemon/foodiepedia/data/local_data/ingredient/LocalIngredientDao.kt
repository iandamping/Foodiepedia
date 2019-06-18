package com.ian.junemon.foodiepedia.data.local_data.ingredient

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 *
Created by Ian Damping on 17/06/2019.
Github = https://github.com/iandamping
 */
@Dao
interface LocalIngredientDao {
    @Query("SELECT * FROM ingredient_data")
    fun loadAllIngredientData(): LiveData<List<LocalIngredientData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredientData(inputIngredient: List<LocalIngredientData>?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateIngredientData(updateIngredient: LocalIngredientData?)

    @Query("DELETE FROM ingredient_data")
    fun deleteAllData()

}