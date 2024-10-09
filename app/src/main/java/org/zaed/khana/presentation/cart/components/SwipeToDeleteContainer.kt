package org.zaed.khana.presentation.cart.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.presentation.theme.KhanaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteContainer(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    content: @Composable () -> Unit,
) {
    val state = rememberSwipeToDismissBoxState()
    SwipeToDismissBox(
        modifier = modifier.animateContentSize(),
        state = state,
        backgroundContent = {
            DeleteBackGround(swipeDismissState = state)
        },
        content = {
            content()
        },
        enableDismissFromEndToStart = true,
        enableDismissFromStartToEnd = false
    )
    when(state.currentValue){
        SwipeToDismissBoxValue.EndToStart -> {
            LaunchedEffect(key1 = state) {
                onDelete()
                state.snapTo(SwipeToDismissBoxValue.Settled)
            }
        }
        else -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeleteBackGround(
    modifier: Modifier = Modifier,
    swipeDismissState: SwipeToDismissBoxState,
) {
    val color = if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        Color.Red
    } else {
        Color.Transparent
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = Color.White)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun SwipeToDeleteContainerPreview() {
    KhanaTheme {
        LazyColumn (
            contentPadding = PaddingValues(32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(10){ index ->
                SwipeToDeleteContainer(onDelete = { /*TODO*/ }) {
                    Text(text = "Item: $index", modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth().background(MaterialTheme.colorScheme.background),)
                }
            }
        }

    }
}