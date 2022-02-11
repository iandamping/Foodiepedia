package com.ian.junemon.foodiepedia.compose.view.motion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.*
import com.ian.junemon.foodiepedia.compose.R


@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalMotionApi::class
)
@Composable
fun MotionLayoutHeader(progress: Float, scrollableBody: @Composable () -> Unit) {
    MotionLayout(
        start = JsonConstraintSetStart(),
        end = JsonConstraintSetEnd(),
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
    ) {





        Image(
            painter = painterResource(id = R.drawable.ic_preview),
            contentDescription = "poster",
            modifier = Modifier
                .layoutId("poster")
                .background(MaterialTheme.colors.primary),
            contentScale = ContentScale.FillWidth,
            alpha = 1f - progress
        )

        Box(
            Modifier
                .layoutId("content")
        ) {
            scrollableBody()
        }

    }
}


@Composable
private fun JsonConstraintSetStart() = ConstraintSet(
    """ {
	poster: { 
		width: "spread",
		start: ['parent', 'start', 0],
		end: ['parent', 'end', 0],
		top: ['parent', 'top', 0],
	},
	title: {
		top: ['poster', 'bottom', 16],
		start: ['parent', 'start', 16],
		custom: {
			textColor: "#000000", 
			textSize: 40
		}
	},
	content: {
		width: "spread",
		start: ['parent', 'start', 0],
		end: ['parent', 'end', 0],
		top: ['title', 'bottom', 16],
	}
} """
)

@Composable
private fun JsonConstraintSetEnd() = ConstraintSet(
    """ {
	poster: { 
		width: "spread",
		height: 56,
		start: ['parent', 'start', 0],
		end: ['parent', 'end', 0],
		top: ['parent', 'top', 0],
	},
	title: {
		top: ['parent', 'top', 0],
		start: ['parent', 'start', 0],
		end: ['parent', 'end', 0], 
		bottom: ['poster', 'bottom', 0],
		custom: {
			textColor: "#ffffff",
			textSize: 20
        }
	},
	content: {
		width: "spread",
		start: ['parent', 'start', 0],
		end: ['parent', 'end', 0],
		top: ['poster', 'bottom', 0],
	}
                  
} """
)