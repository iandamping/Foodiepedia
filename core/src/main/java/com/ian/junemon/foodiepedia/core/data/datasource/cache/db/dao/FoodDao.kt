package com.ian.junemon.foodiepedia.core.data.datasource.cache.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ian.junemon.foodiepedia.core.data.datasource.cache.model.Food
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 24,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Dao
interface FoodDao {
    @Query("SELECT * FROM table_food")
    fun loadFood(): Flow<List<Food>>

    @Query("SELECT * FROM table_food WHERE food_category = :foodCategory")
    fun loadFoodByCategory(foodCategory: String): Flow<List<Food>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFood(vararg tagsData: Food)

    @Query("DELETE FROM table_food")
    suspend fun deleteAllFood()

}