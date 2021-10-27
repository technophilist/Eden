package com.example.eden.auth

import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class FirebaseAuthenticationServiceTest {
    private val firebaseAuthService = FirebaseAuthenticationService()
    private val firebaseAuth = FirebaseAuth.getInstance()

    // test user' details
    private val testUserName = "testUserName"
    private val testEmail = "testEmail@testEmail.com"
    private val testPassword = "testPassword"
    private var isSignInTestExecuted = false

    @Before
    fun setUp() {
        if (firebaseAuthService.currentUser != null) {
            // If a user is already signed in, sign the user off.
            firebaseAuthService.signOut()
        }
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
        isSignInTestExecuted = true
        // give a set of valid credentials
        val authenticationResult = runBlocking {
            // when signIn is called with the credentials
            firebaseAuthService.signIn(testEmail, testPassword)
        }
        // the result must be an instance of AuthenticationResult.Success
        assert(authenticationResult is AuthenticationResult.Success)

    }

    @After
    fun tearDown() {
        // signInTest must be the last test to be executed.
        // If it has executed, delete the user from firebase.
        if (isSignInTestExecuted) firebaseAuth.currentUser!!.delete()
        else firebaseAuth.signOut()
    }

}