package com.example.repobrowser.ui.model

import java.io.StringReader

data class RepositoryView(val name: String, val description: String?) {
    override fun equals(other: Any?): Boolean {
        return when(other) {
            this -> true
            is RepositoryView ->  other.name == name
            else -> false
        }
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}