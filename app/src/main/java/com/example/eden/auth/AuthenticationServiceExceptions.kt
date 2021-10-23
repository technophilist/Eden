package com.example.eden.auth

enum class AuthenticationServiceExceptionType {
    /**
     * Indicates that the exception was caused due to an
     * invalid email address.
     */
    InvalidEmail,

    /**
     * Indicates that the exception was caused due to an
     * invalid password.
     */
    InvalidPassword,

    /**
     * Indicates that the exception was caused by
     * one or more of the credentials being invalid.
     */
    InvalidCredentials,

    /**
     * Indicates that the exception was caused by
     * an attempt to create an already existing user
     * with the same credentials.
     */
    UserCollision,

    /**
     * Indicates that an exception was caused during
     * the creation of a new user account.
     */
    AccountCreation,

    /**
     * Indicates that the exception was caused as
     * a result of an attempt made to fetch the details
     * of a non-existent user.
     */
    InvalidUser
}

/**
 * An exception class that will be thrown by [AuthenticationService] in
 * the event of an exception. This class is mainly used in conjunction
 * with [AuthenticationResult.Failure] class.
 *
 * The [AuthenticationResult] class is the model class that is used for all
 * authentication purposes in the entire app. This allows to easily switch out the
 * [AuthenticationService] without breaking other code that depends up the exceptions
 * thrown by the different implementations of [AuthenticationService].
 * It must also be ensured that any implementation of [AuthenticationService] makes
 * use of this class to represent an exception.
 */
class AuthenticationServiceException(
    message: String? = null,
    cause: Throwable? = null,
    val exceptionType: AuthenticationServiceExceptionType
) : Exception(message, cause)

