package com.ian.junemon.foodiepedia.data.local_data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ian.junemon.foodiepedia.data.local_data.all_food.LocalAllFoodDao
import com.ian.junemon.foodiepedia.data.local_data.all_food.LocalAllFoodData
import com.ian.junemon.foodiepedia.data.local_data.all_food_category_detail.LocalAllFoodCategoryDetailDao
import com.ian.junemon.foodiepedia.data.local_data.all_food_category_detail.LocalAllFoodCategoryDetailData
import com.ian.junemon.foodiepedia.data.local_data.area.LocalAreaDao
import com.ian.junemon.foodiepedia.data.local_data.area.LocalAreaData
import com.ian.junemon.foodiepedia.data.local_data.detail.LocalFoodDao
import com.ian.junemon.foodiepedia.data.local_data.detail.LocalFoodData
import com.ian.junemon.foodiepedia.data.local_data.ingredient.LocalIngredientDao
import com.ian.junemon.foodiepedia.data.local_data.ingredient.LocalIngredientData

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
@Database(entities = [LocalFoodData::class, LocalAllFoodData::class, LocalAllFoodCategoryDetailData::class, LocalAreaData::class,
    LocalIngredientData::class], version = 2, exportSchema = false)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): LocalFoodDao
    abstract fun allFoodDao(): LocalAllFoodDao
    abstract fun allFoodCategoryDetailDao(): LocalAllFoodCategoryDetailDao
    abstract fun areaDao(): LocalAreaDao
    abstract fun ingredientDao(): LocalIngredientDao
}