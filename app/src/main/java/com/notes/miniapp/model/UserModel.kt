package com.notes.miniapp.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserModel(val username: String? = null, val email: String? = null)
