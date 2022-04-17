package com.notes.miniapp.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class NoteValueModel(val note: String? = null)

data class NoteModel(
    val uuid: String,
    val model: NoteValueModel
)
