package com.ian.junemon.foodiepedia.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.asLiveData
import androidx.navigation.compose.rememberNavController
import com.ian.junemon.foodiepedia.compose.navigation.FoodNavigationHost
import com.ian.junemon.foodiepedia.compose.state.FoodUiState
import com.ian.junemon.foodiepedia.compose.ui.theme.FoodiepediaTheme
import com.ian.junemon.foodiepedia.core.util.DataConstant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodActivity : ComponentActivity() {
    private val vm: FoodViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodiepediaTheme {
                MyApp(vm)
            }
        }
    }
}

@Composable
fun MyApp(viewModel: FoodViewModel) {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

        FoodNavigationHost(
            viewModel = viewModel,
            navController = navController
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    FoodiepediaTheme {
//        HomeScreen(filterFood = "",
//            foodState = FoodUiState.initial(),
//            setFilterFood = {
//
//            }, userSearch = "",
//            setUserSearch = {})
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun FoodPreview() {
//    FoodiepediaTheme {
//        FoodListPreview()
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun FilterFoodDialogPreview() {
//    FoodiepediaTheme {
//        LazyColumn {
//            items(FILTER_ITEM) { singleItem ->
//                FilterFoodDialog(filterItem = singleItem, selectedFilterItem = {
//
//                })
//            }
//        }
//
//    }
//}

//@OptIn(ExperimentalMaterialApi::class)
//@Preview(showBackground = true)
//@Composable
//fun CollapsableToolbarPreview() {
//    FoodiepediaTheme {
//        FoodDetailScreen()
//    }
//}