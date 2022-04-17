package com.notes.miniapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.notes.miniapp.model.NoteModel
import com.notes.miniapp.model.NoteValueModel
import com.notes.miniapp.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class FirebaseDatabaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) :
    DatabaseRepository {

    override suspend fun getListNotes(): List<NoteModel>? = withContext(Dispatchers.IO) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val result: List<NoteModel>? = suspendCoroutine { cont ->
                firebaseDatabase.reference.child("notes")
                    .child(userId)
                    .get()
                    .addOnSuccessListener {
                        it.children.map { }
                        if (it.children.count() <= 0) {
                            cont.resume(listOf())
                        } else {
                            val result = it.children.map { note ->
                                val noteValue = note.getValue(NoteValueModel::class.java)
                                val key = note.key ?: return@map null
                                val value = noteValue ?: return@map null
                                return@map NoteModel(key, value)
                            }.filterNotNull()
                            cont.resume(result)
                        }
                    }.addOnFailureListener {
                        cont.resume(null)
                    }
            }
            return@withContext result
        } else {
            return@withContext null
        }
    }

    override suspend fun addNote(text: String): Boolean = withContext(Dispatchers.IO) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val result: Boolean = suspendCoroutine { cont ->
                firebaseDatabase.reference.child("notes")
                    .child(userId)
                    .child(UUID.randomUUID().toString())
                    .setValue(NoteValueModel(text))
                    .addOnSuccessListener {
                        cont.resume(true)
                    }.addOnFailureListener {
                        cont.resume(false)
                    }
            }
            return@withContext result
        } else {
            return@withContext false
        }
    }

    override suspend fun removeNote(noteModel: NoteModel): Boolean = withContext(Dispatchers.IO) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val result: Boolean = suspendCoroutine { cont ->
                firebaseDatabase.reference.child("notes")
                    .child(userId)
                    .child(noteModel.uuid)
                    .removeValue()
                    .addOnSuccessListener {
                        cont.resume(true)
                    }.addOnFailureListener {
                        cont.resume(false)
                    }
            }
            return@withContext result
        } else {
            return@withContext false
        }
    }

    override suspend fun updateUserInfo(userInfo: UserModel): Boolean = withContext(Dispatchers.IO) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val result: Boolean = suspendCoroutine { cont ->
                firebaseDatabase.reference.child("users")
                    .child(userId)
                    .setValue(userInfo)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            cont.resume(true)
                        } else {
                            cont.resume(false)
                        }
                    }
            }
            return@withContext result
        } else {
            return@withContext false
        }
    }
}
