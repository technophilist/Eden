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

enum class LoginUiState {
    SUCCESS,
    LOADING,
    WRONG_CREDENTIALS,
    NETWORK_ERROR,
    SIGNED_OUT
}

interface LogInViewModel {
    val uiState: State<LoginUiState>
    fun authenticate(emailAddress: String, password: String)
    fun removeErrorMessage()
}

class EdenLogInViewModel(
    private val authenticationService: AuthenticationService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(), LogInViewModel {
    private var _uiState = mutableStateOf(LoginUiState.SIGNED_OUT)
    override val uiState = _uiState as State<LoginUiState>

    override fun authenticate(emailAddress: String, password: String) {
        viewModelScope.launch(defaultDispatcher) {
            _uiState.value = LoginUiState.LOADING
            when (val result = authenticationService.signIn(emailAddress.trimEnd(), password)) {
                is AuthenticationResult.Success -> _uiState.value = LoginUiState.SUCCESS
                is AuthenticationResult.Failure -> setUiStateForFailureType(result.failureType)
            }
        }
    }

    override fun removeErrorMessage() {
        if (_uiState.value == LoginUiState.WRONG_CREDENTIALS || _uiState.value == LoginUiState.NETWORK_ERROR) {
            _uiState.value = LoginUiState.SIGNED_OUT
        }
    }

    private fun setUiStateForFailureType(failureType: AuthenticationResult.FailureType) {
        _uiState.value = when (failureType) {
            InvalidEmail, InvalidPassword, InvalidCredentials, InvalidUser -> LoginUiState.WRONG_CREDENTIALS
            NetworkFailure -> LoginUiState.NETWORK_ERROR
            UserCollision, AccountCreation -> throw IllegalStateException("UserCollision and AccountCreation failure cannot occur during log-in flow.")
        }
    }
}