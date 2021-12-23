package com.example.eden.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Represents a chip that is meant to be used for applying a filter.
 *
 * The chip will have an outlined style when it is not selected.If
 * it is selected, the background will be filled using the
 * [selectedBackgroundColor] and a [Icons.Filled.Done] icon will be
 * displayed at the start of the composable.
 *
 * @param onClick callback to be called when the filter chip
 * is clicked.
 * @param isSelected indicates whether the chip is selected or not.
 * @param outlinedBorderColor the border color to use when the filter
 * chip is not selected.
 * @param selectedBackgroundColor the background color to use when
 * the filter chip [isSelected].
 * @param shape the shape of the chip.
 * @param content the content of the chip.
 */
@ExperimentalMaterialApi
@Composable
fun FilterChip(
    onClick: () -> Unit,
    isSelected: Boolean = false,
    outlinedBorderColor: Color = MaterialTheme.colors.secondary,
    selectedBackgroundColor: Color = MaterialTheme.colors.secondary,
    shape: Shape = CircleShape,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Max),
        onClick = onClick,
        border = if (isSelected) null else BorderStroke(1.dp, outlinedBorderColor),
        color = if (isSelected) selectedBackgroundColor else MaterialTheme.colors.surface,
        shape = shape,
    ) {
        Row(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            )
        ) {
            if (isSelected) {
                Icon(
                    modifier = Modifier
                        .align(CenterVertically)
                        .padding(end = 8.dp)
                        .size(16.dp),
                    imageVector = Icons.Filled.Done,
                    contentDescription = null
                )
            }
            content()
        }
    }
}