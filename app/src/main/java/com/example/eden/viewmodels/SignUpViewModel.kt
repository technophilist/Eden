package com.example.eden.viewmodels

import android.net.Uri
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eden.auth.AuthenticationResult
import com.example.eden.auth.AuthenticationService
import com.example.eden.utils.containsDigit
import com.example.eden.utils.containsLowercase
import com.example.eden.utils.containsUppercase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class SignUpUiFailureType {
    INVALID_CREDENTIALS,
    USER_COLLISION,
    NETWORK_ERROR,
}

sealed class SignUpUiState {
    object Success : SignUpUiState()
    object Loading : SignUpUiState()
    object SignedOut : SignUpUiState()
    data class Failed(val cause: SignUpUiFailureType) : SignUpUiState()
}


interface SignUpViewModel {
    val uiState: State<SignUpUiState>
    fun createNewAccount(
        name: String,
        email: String,
        password: String,
        profilePhotoUri: Uri? = null
    )
    fun removeErrorMessage()
}

class EdenSignUpViewModel(
    private val authenticationService: AuthenticationService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(), SignUpViewModel {
    private val _uiState = mutableStateOf<SignUpUiState>(SignUpUiState.SignedOut)
    override val uiState = _uiState as State<SignUpUiState>

    /**
     * The method is used to check whether the [email] is valid .An email is valid
     * if, and only if, it is not blank(ie. is not empty and doesn't contain whitespace characters)
     * and matches the [Patterns.EMAIL_ADDRESS] regex.
     */
    private fun isValidEmail(email: String) =
        email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    /**
     * The method is used to check whether the [password] is valid.A password is valid if, and only if,
     * it is of length 8 , contains atleast one uppercase and lowercase letter and contains atleast one digit.
     */
    private fun isValidPassword(
        password: String
    ) = password.length >= 8 && password.containsUppercase() && password.containsLowercase() && password.containsDigit()

    override fun createNewAccount(
        name: String,
        email: String,
        password: String,
        profilePhotoUri: Uri?
    ) {
        if (!isValidEmail(email) || !isValidPassword(password)) _uiState.value =
            SignUpUiState.Failed(SignUpUiFailureType.INVALID_CREDENTIALS)
        else viewModelScope.launch(defaultDispatcher) {
            _uiState.value = SignUpUiState.Loading
            val authenticationResult =
                authenticationService.createAccount(name, email.trim(), password, profilePhotoUri)
            _uiState.value = when (authenticationResult) {
                is AuthenticationResult.Success -> SignUpUiState.Success
                is AuthenticationResult.Failure -> getUiStateForFailureType(authenticationResult.failureType)
            }
        }
    }

    override fun removeErrorMessage() {
        if(_uiState.value is SignUpUiState.Failed){
            _uiState.value = SignUpUiState.SignedOut
        }
    }

    private fun getUiStateForFailureType(failureType: AuthenticationResult.FailureType): SignUpUiState =
        SignUpUiState.Failed(
            when (failureType) {
                AuthenticationResult.FailureType.InvalidPassword, AuthenticationResult.FailureType.InvalidCredentials, AuthenticationResult.FailureType.InvalidUser -> SignUpUiFailureType.INVALID_CREDENTIALS
                AuthenticationResult.FailureType.NetworkFailure -> SignUpUiFailureType.NETWORK_ERROR
                AuthenticationResult.FailureType.UserCollision, AuthenticationResult.FailureType.AccountCreation -> SignUpUiFailureType.USER_COLLISION
                else -> throw IllegalStateException("Unexpected failure type.")
            }
        )

}