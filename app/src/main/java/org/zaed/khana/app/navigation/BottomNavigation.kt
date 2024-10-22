package org.zaed.khana.app.navigation

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.presentation.theme.KhanaTheme


@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentRoute: String,
    onNavigate: (Destinations) -> Unit
) {
    BottomAppBar(
        modifier = modifier
            .padding(horizontal = 16.dp,)
            .padding(bottom = 16.dp)
            .clip(RoundedCornerShape(64.dp)),
    ) {
        BottomNavigation.entries
            .forEach { navigationItem ->
                val isSelected by remember(currentRoute) {
                    derivedStateOf { currentRoute == navigationItem.route::class.qualifiedName }
                }
                NavigationBarItem(
                    selected = isSelected,
                    alwaysShowLabel = false,
                    icon = {
                        if (isSelected) {
                            Icon(
                                painter = painterResource(id = navigationItem.selectedIcon),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = navigationItem.unselectedIcon),
                                contentDescription = null
                            )
                        }
                    },
                    onClick = {
                        onNavigate(navigationItem.route)
                    },
                )
            }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun BottomBarPreview() {
    var currentRoute by remember{
        mutableStateOf("org.zaed.khana.app.navigation.Destinations.HomeScreen")
    }
    KhanaTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    currentRoute = currentRoute
                ) { route ->
                    currentRoute = route::class.qualifiedName.orEmpty()

                }
            }
        ) {
            Box(Modifier.padding(it))
        }
    }
}

enum class BottomNavigation(
    @DrawableRes
    val selectedIcon: Int,
    @DrawableRes
    val unselectedIcon: Int,
    val route: Destinations
) {
    HOME(R.drawable.ic_home_selected, R.drawable.ic_home_unselected, Destinations.HomeScreen()),
    CART(
        R.drawable.ic_shopping_bag_selected,
        R.drawable.ic_shopping_bag_unselected,
        Destinations.CartScreen
    ),
    WISHLIST(
        R.drawable.ic_heart_selected,
        R.drawable.ic_heart_unselected,
        Destinations.WishlistScreen
    ),
    PROFILE(
        R.drawable.ic_account_circle_selected,
        R.drawable.ic_account_circle_unselected,
        Destinations.ProfileScreen
    );
}