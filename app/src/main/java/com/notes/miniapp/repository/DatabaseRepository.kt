package com.notes.miniapp.repository

import com.notes.miniapp.model.NoteModel
import com.notes.miniapp.model.UserModel

interface DatabaseRepository {
    suspend fun getListNotes(): List<NoteModel>?
    suspend fun addNote(text: String): Boolean
    suspend fun removeNote(noteModel: NoteModel): Boolean
    suspend fun updateUserInfo(userInfo: UserModel): Boolean
}
