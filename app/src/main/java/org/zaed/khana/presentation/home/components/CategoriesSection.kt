package org.zaed.khana.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.data.model.Category
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun CategoriesSection(
    categories: List<Category>,
    modifier: Modifier = Modifier
) {
    Column {
        Text(text = stringResource(R.string.category))
        Spacer(modifier = Modifier.size(16.dp))
        LazyRow(
            contentPadding = PaddingValues(16.dp),
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories.size){ index ->
                val category = categories[index]
                CategoryItem(
                    categoryImage = category.categoryImage,
                    categoryTitle = category.categoryTitle
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
        CategoriesSection(categories)
    }
}