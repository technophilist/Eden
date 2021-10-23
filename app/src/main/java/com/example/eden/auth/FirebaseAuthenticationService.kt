package com.example.eden.auth
//
//import android.net.Uri
//import com.example.pocket.utils.createUser
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import kotlinx.coroutines.suspendCancellableCoroutine
//import kotlin.coroutines.resume
//import kotlin.coroutines.resumeWithException
//
//class FirebaseAuthenticationService : AuthenticationService {
//
//    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
//    override val currentUser get() = mAuth.currentUser?.toPocketUser()
//
//    private fun FirebaseUser.toPocketUser() = PocketUser(uid, displayName, email, photoUrl)
//
//    /**
//     * This method is used for signing-in a registered user with the provided [email] and [password].
//     * It uses [suspendCancellableCoroutine] and resumes with an instance of the [FirebaseUser] if sign-in
//     * was successful.Else,it resumes with an [AuthServiceSignInException].
//     */
//    private suspend fun signInToFirebase(
//        email: String,
//        password: String
//    ): FirebaseUser = suspendCancellableCoroutine { cancellableContinuation ->
//        mAuth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) cancellableContinuation.resume(mAuth.currentUser!!)
//                else cancellableContinuation.resumeWithException(
//                    AuthServiceSignInException(
//                        message = "An exception occurred when signing in",
//                        cause = task.exception
//                    )
//                )
//            }
//    }
//
//    /**
//     * This method is used for signing in a user with the provided [email] and [password].
//     * It returns [AuthenticationResult.Success] with the signed-in [PocketUser] if sign-in was successful.Else,
//     * it returns an instance of [AuthenticationResult.Failure] with the exception in it.
//     */
//    override suspend fun signIn(
//        email: String,
//        password: String
//    ): AuthenticationResult = runCatching {
//        val user = signInToFirebase(email, password).toPocketUser()
//        AuthenticationResult.Success(user)
//    }.getOrElse {
//        AuthenticationResult.Failure(it as AuthenticationServiceException)
//    }
//
//    /**
//     * This method is used for creating a user with the provided [username],[email],[password] and an optional
//     * profile photo ([profilePhotoUri]).
//     * It returns [AuthenticationResult.Success] with the created [PocketUser] if the user was created successfully.Else,
//     * it returns an instance of [AuthenticationResult.Failure] with the exception in it.
//     */
//    override suspend fun createAccount(
//        username: String,
//        email: String,
//        password: String,
//        profilePhotoUri: Uri?
//    ): AuthenticationResult = runCatching {
//        val firebaseUser = mAuth.createUser(username, email, password, profilePhotoUri)
//        AuthenticationResult.Success(firebaseUser.toPocketUser())
//    }.getOrElse {
//        AuthenticationResult.Failure(it as AuthenticationServiceException)
//    }
//
//    /**
//     * This method is used for signing-out the current signed-in user.
//     */
//    override suspend fun signOut() {
//        mAuth.signOut()
//    }
//
//}
