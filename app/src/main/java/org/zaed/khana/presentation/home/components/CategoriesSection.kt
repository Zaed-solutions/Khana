package org.zaed.khana.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.data.model.Category
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.shimmerEffect

@Composable
fun CategoriesSection(
    isLoading: Boolean,
    categories: List<Category>,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = stringResource(R.string.category),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        if (isLoading) {
            CategoriesSectionShimmer(
                modifier = Modifier.padding(start = 16.dp)
            )
        } else {
            LazyRow(
                modifier = modifier,
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories.size) { index ->
                    val category = categories[index]
                    CategoryItem(
                        modifier = Modifier.animateItem(),
                        categoryImage = category.categoryImage,
                        categoryTitle = category.categoryTitle
                    )
                }
            }
        }
    }
}

@Composable
fun CategoriesSectionShimmer(modifier: Modifier = Modifier) {
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        repeat(4){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
                )
                Box(modifier = Modifier
                    .size(width = 88.dp, height = 24.dp)
                    .shimmerEffect()
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CategoriesSectionPreview() {
    val categories = listOf(
        Category(
            categoryImage = "https://us.123rf.com/450wm/pavelstasevich/pavelstasevich1904/pavelstasevich190400188/123197783-vector-illustration-flat-design-clothes-t-shirt-icon.jpg",
            categoryTitle = "Category 1"
        ),
        Category(
            categoryImage = "https://us.123rf.com/450wm/pavelstasevich/pavelstasevich1904/pavelstasevich190400188/123197783-vector-illustration-flat-design-clothes-t-shirt-icon.jpg",
            categoryTitle = "Category 2"
        ),
        Category(
            categoryImage = "https://us.123rf.com/450wm/pavelstasevich/pavelstasevich1904/pavelstasevich190400188/123197783-vector-illustration-flat-design-clothes-t-shirt-icon.jpg",
            categoryTitle = "Category 3"
        ),
        Category(
            categoryImage = "https://us.123rf.com/450wm/pavelstasevich/pavelstasevich1904/pavelstasevich190400188/123197783-vector-illustration-flat-design-clothes-t-shirt-icon.jpg",
            categoryTitle = "Category 4"
        ),
        Category(
            categoryImage = "https://us.123rf.com/450wm/pavelstasevich/pavelstasevich1904/pavelstasevich190400188/123197783-vector-illustration-flat-design-clothes-t-shirt-icon.jpg",
            categoryTitle = "Category 5"
        ),
    )
    KhanaTheme {
        CategoriesSection(false, categories)
    }
}