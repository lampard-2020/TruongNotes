package com.notes.miniapp.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class FirebaseAuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthenticationRepository {
    override fun currentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override suspend fun signUp(email: String, password: String): Boolean = withContext(Dispatchers.IO) {
        val result: Boolean = suspendCoroutine { cont ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        cont.resume(true)
                    } else {
                        cont.resume(false)
                    }
                }
        }
        return@withContext result
    }

    override suspend fun signIn(email: String, password: String): Boolean = withContext(Dispatchers.IO) {
        val result: Boolean = suspendCoroutine { cont ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        cont.resume(true)
                    } else {
                        cont.resume(false)
                    }
                }
        }
        return@withContext result
    }
}
