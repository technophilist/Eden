package com.example.eden.ui.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * This class represents a menu option with the specified [label]
 * and an [action] to be performed with the option is clicked
 * on.
 * This is mainly to be used in conjunction with [IconWithDropDownMenu].
 */
data class MenuOption(
    val label: String,
    val action: () -> Unit
)

/**
 * A clickable icon that will display a list of [MenuOption]'s when
 * it is clicked upon.
 * @param icon the icon that is to be displayed.
 * @param menuOptions list of options and their associated actions .
 * @param isDropDownMenuExpanded indicates whether the menu is currently
 * open and visible to the user.
 * @param onDismissRequest called when the user requests to dismiss the menu,
 * such as by tapping outside the menu's bounds.
 * @param onClick the action to perform when the icon is clicked.This is mainly
 * used to toggle the visibility of the menu.
 * @param modifier optional [Modifier] that will be applied to the icon to be
 * displayed.
 */
@Composable
private fun IconWithDropDownMenu(
    icon: ImageVector,
    menuOptions: List<MenuOption>,
    isDropDownMenuExpanded: Boolean,
    onDismissRequest: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(onClick = onClick) {
        Icon(
            modifier = modifier,
            imageVector = icon,
            contentDescription = ""
        )
        DropdownMenu(
            expanded = isDropDownMenuExpanded,
            onDismissRequest = onDismissRequest
        ) {
            for (menuOption in menuOptions) {
                DropdownMenuItem(
                    onClick = menuOption.action,
                    content = { Text(text = menuOption.label) }
                )
            }
        }
    }
}
