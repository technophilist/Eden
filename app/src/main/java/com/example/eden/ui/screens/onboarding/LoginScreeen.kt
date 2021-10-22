package com.example.eden.ui.screens.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.eden.R
import com.example.eden.ui.components.EdenSingleLineTextField

@ExperimentalComposeUiApi
@Composable
fun LoginScreen() {
    // states for text fields
    var emailAddressText by rememberSaveable { mutableStateOf("") }
    var passwordText by rememberSaveable { mutableStateOf("") }

    // states for validation
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    /*
     * isCredentialsValid & isErrorMessageVisible need not be wrapped in
     * rememberSaveable because their state will be controlled by the
     * disposable effect, which uses the value of a live data in the viewmodel.
     * Since the viewmodel survives config changes, the disposable effect
     * will restart with the appropriate value.
     */
    var isCredentialsValid by remember { mutableStateOf(false) }
    var isErrorMessageVisible by remember { mutableStateOf(false) }
    /*
    * isLoginButtonEnabled state need not be wrapped in rememberSaveable
    * because this state will be computed only when its keys change.
    * Since the keys use remember saveable, and this state is not,
    * it will automatically be recomputed after a config change.
    */
    val isLoginButtonEnabled by remember(emailAddressText, passwordText) {
        derivedStateOf { emailAddressText.isNotBlank() && passwordText.isNotEmpty() }
    }

    // state for keyboard
    val keyboardController = LocalSoftwareKeyboardController.current

    // keyboard actions for the text fields
    val keyboardActions = KeyboardActions(onDone = {
        if (emailAddressText.isNotBlank() && passwordText.isNotEmpty()) {
            keyboardController?.hide()
            isLoading = true
        }
    })

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .paddingFromBaseline(top = 184.dp),
                text = stringResource(id = R.string.label_login_with_email),
                style = MaterialTheme.typography.h1
            )

            Spacer(modifier = Modifier.padding(16.dp))

            EdenSingleLineTextField(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(),
                value = emailAddressText,
                onValueChange = {
                    /*
                     * if isErrorMessageVisible is set to true then it indicates
                     * a failed login attempt.Remove the error message when the user
                     * is making an edit to the email address text.The prevents the
                     * error message from being displayed when the user is re-typing.
                     */
                    if (isErrorMessageVisible) {
                        isErrorMessageVisible = false
                    }
                    emailAddressText = it

                },
                isError = isErrorMessageVisible,
                keyboardActions = keyboardActions,
                label = { Text(text = stringResource(R.string.placeholder_email_address))},
            )

            Spacer(modifier = Modifier.padding(8.dp))

            EdenSingleLineTextField(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(),
                value = passwordText,
                onValueChange = {
                    /*
                     * if isErrorMessageVisible is set to true then it indicates
                     * a failed login attempt.Remove the error message when the user
                     * is making an edit to the password text.The prevents the
                     * error message from being displayed when the user is re-typing.
                     */
                    if (isErrorMessageVisible) {
                        isErrorMessageVisible = false
                    }
                    passwordText = it
                },
                label = { Text(text = stringResource(R.string.placeholder_password)) },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                isError = isErrorMessageVisible,
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable { isPasswordVisible = !isPasswordVisible },
                        imageVector = if (isPasswordVisible) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff,
                        contentDescription = ""
                    )
                },
                keyboardActions = keyboardActions,
            )

            if (isErrorMessageVisible && !isCredentialsValid) {
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(R.string.label_login_error_message),
                    color = MaterialTheme.colors.error
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .paddingFromBaseline(top = 24.dp),
                text = stringResource(id = R.string.label_terms_and_conditions),
                style = MaterialTheme.typography.body2
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Button(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                onClick = { isLoading = true },
                shape = MaterialTheme.shapes.medium,
                content = {
                    Text(
                        text = stringResource(id = R.string.label_login),
                        fontWeight = FontWeight.Bold
                    )
                },
                enabled = isLoginButtonEnabled,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.onSecondary
                )
            )
        }

        if (isLoading) LoadingProgressOverlay(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f)
        )
    }
}

@Composable
private fun LoadingProgressOverlay(modifier: Modifier) {
    Surface(
        modifier = modifier,
        color = Color.Black
    ) {
        Box {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
