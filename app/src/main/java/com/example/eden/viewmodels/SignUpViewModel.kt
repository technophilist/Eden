package com.example.eden.viewmodels

import android.net.Uri
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eden.auth.AuthenticationResult
import com.example.eden.auth.AuthenticationResult.FailureType.AccountCreation
import com.example.eden.auth.AuthenticationResult.FailureType.UserCollision
import com.example.eden.auth.AuthenticationService
import com.example.eden.utils.containsDigit
import com.example.eden.utils.containsLowercase
import com.example.eden.utils.containsUppercase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * An enum class used to model the different UI states associated with
 * a SignUp screen.
 */
enum class SignUpUiFailureType {
    INVALID_CREDENTIALS,
    USER_COLLISION,
    NETWORK_ERROR,
}

/**
 * A sealed class representing the different UI states associated
 * with a SignUpScreen.
 */
sealed class SignUpUiState {
    object Loading : SignUpUiState()
    object SignedOut : SignUpUiState()
    data class Failed(val cause: SignUpUiFailureType) : SignUpUiState()
}

/**
 * An interface that consists of all the fields and methods required
 * for a SignUpViewModel.
 */
interface SignUpViewModel {
    val uiState: State<SignUpUiState>

    /**
     * Used to create a new user account based on the provided [name],
     * [email],[password] and optional [profilePhotoUri]. The [onSuccess]
     * callback will be called in the event of a successful account
     * creation. In the case of a failure, the [uiState]'s value will
     * be set appropriately.
     */
    fun createNewAccount(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        profilePhotoUri: Uri? = null
    )

    /**
     * Used to change the [uiState] to a non-error state thereby
     * removing any associated error messages from the UI layer.
     *
     * This is mainly used to hide any error messages/highlighting
     * from the associated screen that's meant to be displayed
     * when the current state is one of the error
     * states - [SignUpUiFailureType.INVALID_CREDENTIALS] or
     * [SignUpUiFailureType.NETWORK_ERROR].
     */
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
    ) =
        password.length >= 8 && password.containsUppercase() && password.containsLowercase() && password.containsDigit()

    override fun createNewAccount(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        profilePhotoUri: Uri?,
    ) {
        if (!isValidEmail(email) || !isValidPassword(password)) _uiState.value =
            SignUpUiState.Failed(SignUpUiFailureType.INVALID_CREDENTIALS)
        else viewModelScope.launch(defaultDispatcher) {
            _uiState.value = SignUpUiState.Loading
            val authenticationResult =
                authenticationService.createAccount(name, email.trim(), password, profilePhotoUri)
            when (authenticationResult) {
                is AuthenticationResult.Success -> withContext(Dispatchers.Main) {
                    onSuccess()
                }
                is AuthenticationResult.Failure -> _uiState.value =
                    getUiStateForFailureType(authenticationResult.failureType)
            }
        }
    }

    override fun removeErrorMessage() {
        if (_uiState.value is SignUpUiState.Failed) {
            _uiState.value = SignUpUiState.SignedOut
        }
    }

    /**
     * Helper method used to get an instance of the associated
     * [SignUpUiState] for the provided [failureType].
     */
    private fun getUiStateForFailureType(failureType: AuthenticationResult.FailureType): SignUpUiState =
        SignUpUiState.Failed(
            when (failureType) {
                AuthenticationResult.FailureType.InvalidPassword, AuthenticationResult.FailureType.InvalidCredentials, AuthenticationResult.FailureType.InvalidEmail, AuthenticationResult.FailureType.InvalidUser -> SignUpUiFailureType.INVALID_CREDENTIALS
                AuthenticationResult.FailureType.NetworkFailure -> SignUpUiFailureType.NETWORK_ERROR
                UserCollision, AccountCreation -> SignUpUiFailureType.USER_COLLISION
            }
        )

}