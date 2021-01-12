package com.ian.junemon.foodiepedia.core.data.datasource.cache.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ian.junemon.foodiepedia.core.data.datasource.cache.model.SavedFood
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 10,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Dao
interface SavedFoodDao {

    @Query("DELETE FROM table_saved_food where saved_local_food_id = :selectedId")
    suspend fun deleteSelectedId(selectedId: Int)

    @Query("SELECT * FROM table_saved_food")
    fun loadFood(): Flow<List<SavedFood>>

    @Query("SELECT * FROM table_saved_food WHERE saved_food_category = :foodCategory")
    fun loadFoodByCategory(foodCategory: String): Flow<List<SavedFood>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(vararg tagsData: SavedFood)

    @Query("DELETE FROM table_saved_food")
    suspend fun deleteAllFood()
}