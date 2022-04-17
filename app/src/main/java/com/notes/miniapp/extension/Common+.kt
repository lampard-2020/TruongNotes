package com.notes.miniapp.extension

fun String?.normalizeData(): String {
    return if (this.isNullOrBlank()) {
        ""
    } else {
        var result = this.trim().replace("\\s+".toRegex(), " ")
        result = result.capitalize()
        result
    }
}
