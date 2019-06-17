package com.ian.junemon.foodiepedia.data.local_data.category

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 *
Created by Ian Damping on 17/06/2019.
Github = https://github.com/iandamping
 */
@Dao
interface LocalCategoryDao {
    @Query("SELECT * FROM category_data")
    fun loadAllIngredientData(): LiveData<List<LocalCategoryData>>

//    @Query("SELECT * FROM category_data WHERE localID = :id")
//    fun loadAllCategoryeDataById(id: Int?): LiveData<LocalCategoryData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategoryData(inputCategory: List<LocalCategoryData>?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCategoryData(updateCategory: LocalCategoryData?)

    @Query("DELETE FROM category_data")
    fun deleteAllData()

//    @Query("DELETE FROM category_data where localID = :selectedId")
//    fun deleteSelectedId(selectedId: Int)

}