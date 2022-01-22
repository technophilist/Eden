package com.example.eden.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class FirebaseAuthenticationServiceTest {
    private lateinit var firebaseAuthService: AuthenticationService

    // test user's details
    private val testUserName = "testUserName"
    private val testEmail = "testEmail@testEmail.com"
    private val testPassword = "testPassword"

    @Before
    fun setUp() {
        firebaseAuthService = FirebaseAuthenticationService()
        val firebaseEmulatorHostName = "10.0.2.2"
        val firebaseEmulatorPort = 9099
        // config firebase to use the local emulator
        FirebaseAuth.getInstance()
            .useEmulator(firebaseEmulatorHostName, firebaseEmulatorPort)
    }

    @Test
    fun createAccountTest_validUserDetails_isSuccessfullyCreated() {
        // given a set of valid user details
        runBlocking {
            // when creating an account with the details
            firebaseAuthService.createAccount(
                username = testUserName,
                email = testEmail,
                password = testPassword
            )
        }
        // the account must be successfully created and be assigned to the
        // currentUser variable.
        assertNotNull(firebaseAuthService.currentUser)
    }

    @Test
    fun signInTest_existingUser_isSuccessful() {
        // give a set of credentials of an existing user
        val authenticationResult = runBlocking {
            // when signIn is called with the credentials
            firebaseAuthService.signIn(testEmail, testPassword)
        }
        // the result must be an instance of AuthenticationResult.Success
        assert(authenticationResult is AuthenticationResult.Success)
    }
}