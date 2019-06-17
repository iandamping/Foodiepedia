package com.ian.junemon.foodiepedia.data.local_data.filter

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 *
Created by Ian Damping on 17/06/2019.
Github = https://github.com/iandamping
 */
@Dao
interface LocalFilterDao {
    @Query("SELECT * FROM filter_data")
    fun loadAllIngredientData(): LiveData<List<LocalFilterData>>

//    @Query("SELECT * FROM filter_data WHERE localID = :id")
//    fun loadAllFiltereDataById(id: Int?): LiveData<LocalFilterData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilterData(inputFilter: List<LocalFilterData>?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFilterData(updateFilter: LocalFilterData?)

    @Query("DELETE FROM filter_data")
    fun deleteAllData()

//    @Query("DELETE FROM filter_data where localID = :selectedId")
//    fun deleteSelectedId(selectedId: Int)
}