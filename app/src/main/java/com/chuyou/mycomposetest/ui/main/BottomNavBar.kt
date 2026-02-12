package com.chuyou.mycomposetest.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Navigation
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import com.chuyou.mycomposetest.R
import com.chuyou.mycomposetest.route.RouteNavKey

val BOTTOM_ITEM = mapOf(
    RouteNavKey.Main.Home to BottomBarItem(Icons.Outlined.Home, R.string.string_home),
    RouteNavKey.Main.QuestionAnswer to BottomBarItem(Icons.Outlined.QuestionAnswer, R.string.string_qa),
    RouteNavKey.Main.Navigation to BottomBarItem(Icons.Outlined.Navigation, R.string.string_navigation),
    RouteNavKey.Main.Mine to BottomBarItem(Icons.Outlined.Person, R.string.string_mine),
)

@Composable
fun BottomNavBar(
    selectKey: NavKey,
    onClick: (NavKey) -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomAppBar(modifier = modifier) {
        BOTTOM_ITEM.forEach { (route, item) ->
            val selected = route == selectKey
            NavigationBarItem(
                selected = selected,
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = stringResource(item.label)
                    )
                },
                label = { Text(stringResource(item.label)) },
                onClick = {
                    onClick(route)
                }
            )
        }
    }
}