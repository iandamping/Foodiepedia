package com.ian.junemon.foodiepedia.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.asLiveData
import com.ian.junemon.foodiepedia.compose.Constant.FILTER_ITEM
import com.ian.junemon.foodiepedia.compose.state.FoodUiState
import com.ian.junemon.foodiepedia.compose.ui.theme.FoodiepediaTheme
import com.ian.junemon.foodiepedia.compose.view.FilterFoodDialog
import com.ian.junemon.foodiepedia.compose.view.HomeScreen
import com.ian.junemon.foodiepedia.core.util.DataConstant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodActivity : ComponentActivity() {
    private val vm: FoodViewModel by viewModels()
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
    val filterFood by viewModel.filterData().observeAsState(initial = "")
    val listOfFood by viewModel.food.asLiveData().observeAsState(initial = FoodUiState.initial())
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        HomeScreen(
            filterFood = filterFood.ifEmpty { DataConstant.noFilterValue },
            foodState = listOfFood,
            setFilterFood = {
                viewModel.setFilterData(it)
            }
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodListPreview() {
    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(List(10) { "item $it" }) {
            FoodItemPreview(name = it)
        }
    }
}

@Composable
fun FoodItemPreview(name: String) {
    ConstraintLayout(modifier = Modifier.padding(8.dp)) {
        val (image, text) = createRefs()
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(percent = 10))
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            painter = painterResource(id = R.drawable.ic_preview),
            contentDescription = "preview"
        )


        Text(modifier = Modifier.constrainAs(text) {
            top.linkTo(image.bottom)
            start.linkTo(parent.start)
        }, text = name, style = MaterialTheme.typography.body2)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoodiepediaTheme {
        HomeScreen(filterFood = "",
            foodState = FoodUiState.initial(),
            setFilterFood = {

            })
    }
}

@Preview(showBackground = true)
@Composable
fun FoodPreview() {
    FoodiepediaTheme {
        FoodListPreview()
    }
}

@Preview(showBackground = true)
@Composable
fun FilterFoodDialogPreview() {
    FoodiepediaTheme {
        LazyColumn {
            items(FILTER_ITEM) { singleItem ->
                FilterFoodDialog(filterItem = singleItem, selectedFilterItem = {

                })
            }
        }

    }
}