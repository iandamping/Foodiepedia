package com.ian.junemon.foodiepedia.data.local_data

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
@Dao
interface LocalFoodDao {
    @Query("SELECT * FROM food_data")
    fun loadAllFoodData(): LiveData<List<LocalFoodData>>

    @Query("SELECT * FROM food_data WHERE localID = :id")
    fun loadAllFoodeDataById(id: Int?): LiveData<LocalFoodData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFoodData(inputFood: LocalFoodData?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFoodData(updateFood: LocalFoodData?)

    @Query("DELETE FROM food_data")
    fun deleteAllData()

    @Query("DELETE FROM food_data where localID = :selectedId")
    fun deleteSelectedId(selectedId: Int)
}