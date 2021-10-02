package com.example.eden.ui.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

/**
 * A single lined material themed OutlinedTextField with custom color
 * presets that match the app's theme.
 *
 * @param modifier a [Modifier] for this text field
 * @param value the input text to be shown in the text field
 * @param placeholder the optional placeholder to be displayed when
 * the text field is in focus and the input text is empty.
 * @param leadingIcon the optional leading icon to be displayed at the
 * beginning of the text field container.
 * @param trailingIcon  the optional trailing icon to be displayed at the
 * end of the text field container
 * @param keyboardActions keyboardActions when the input service emits an
 * IME action, the corresponding callback is called.
 * [KeyboardOptions.imeAction]
 * @param isError indicates if the text field's current value is in error.
 * If set to true, the label, bottom indicator and trailing icon by default
 * will be displayed in error color
 * @param visualTransformation transforms the visual representation of the
 * input [value]
 * @param label the optional label to be displayed inside the text field
 * container.
 * @param onValueChange the callback that is triggered when the input service
 * updates the text. An updated text comes as a parameter of the callback
 */
@Composable
fun EdenSingleLineTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    label: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder,
        textStyle = MaterialTheme.typography.body1,
        visualTransformation = visualTransformation,
        isError = isError,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = true,
        keyboardActions = keyboardActions,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.secondary,
            focusedLabelColor = MaterialTheme.colors.secondary
        )
    )
}