package com.example.eden.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eden.auth.AuthenticationResult
import com.example.eden.auth.AuthenticationResult.FailureType.*
import com.example.eden.auth.AuthenticationService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * An enum class used to model the different UI states associated with
 * a login screen.
 */
enum class LoginUiState {
    LOADING,
    WRONG_CREDENTIALS,
    NETWORK_ERROR,
    SIGNED_OUT
}

/**
 * An interface that consists of all the fields and methods required
 * for a LogInViewModel.
 */
interface LogInViewModel {
    val uiState: State<LoginUiState>

    /**
     * Used to authenticate an existing user with the specified
     * [emailAddress] and [password]. The [onSuccess] callback will be
     * called when authentication was successful.
     */
    fun authenticate(emailAddress: String, password: String, onSuccess: () -> Unit)

    /**
     * Used to change the [uiState] to a non-error state thereby
     * removing any associated error messages from the UI layer.
     *
     * This is mainly used to hide any error messages/highlighting
     * from the associated screen that's meant to be displayed
     * when the current state is one of the error
     * states - [LoginUiState.WRONG_CREDENTIALS] or
     * [LoginUiState.NETWORK_ERROR].
     */
    fun removeErrorMessage()
}

class EdenLogInViewModel(
    private val authenticationService: AuthenticationService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(), LogInViewModel {
    private var _uiState = mutableStateOf(LoginUiState.SIGNED_OUT)
    override val uiState = _uiState as State<LoginUiState>

    override fun authenticate(emailAddress: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch(defaultDispatcher) {
            _uiState.value = LoginUiState.LOADING
            when (val result = authenticationService.signIn(emailAddress.trimEnd(), password)) {
                is AuthenticationResult.Success -> withContext(Dispatchers.Main) {
                    onSuccess()
                }
                is AuthenticationResult.Failure -> setUiStateForFailureType(result.failureType)
            }
        }
    }

    override fun removeErrorMessage() {
        if (_uiState.value == LoginUiState.WRONG_CREDENTIALS || _uiState.value == LoginUiState.NETWORK_ERROR) {
            _uiState.value = LoginUiState.SIGNED_OUT
        }
    }

    /**
     * Helper method used to assign the appropriate [uiState] based on
     * the provided [failureType].
     *
     * @throws IllegalStateException when a [UserCollision] or
     * [AccountCreation] enum is returned, since these two failures
     * can never manifest themselves when the user is signing in.
     */
    private fun setUiStateForFailureType(failureType: AuthenticationResult.FailureType) {
        _uiState.value = when (failureType) {
            InvalidEmail, InvalidPassword, InvalidCredentials, InvalidUser -> LoginUiState.WRONG_CREDENTIALS
            NetworkFailure -> LoginUiState.NETWORK_ERROR
            UserCollision, AccountCreation -> throw IllegalStateException("UserCollision or AccountCreation failure cannot occur during log-in flow.")
        }
    }
}