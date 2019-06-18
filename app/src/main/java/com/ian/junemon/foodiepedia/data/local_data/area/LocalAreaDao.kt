package com.ian.junemon.foodiepedia.data.local_data.area

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 *
Created by Ian Damping on 17/06/2019.
Github = https://github.com/iandamping
 */
@Dao
interface LocalAreaDao {
    @Query("SELECT * FROM area_data")
    fun loadAllIngredientData(): LiveData<List<LocalAreaData>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAreaData(inputArea: List<LocalAreaData>?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAreaData(updateArea: LocalAreaData?)

    @Query("DELETE FROM area_data")
    fun deleteAllData()

}