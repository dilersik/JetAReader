package com.example.jetareader.model

data class MUser(
    val id: String? = null,
    val userId: String,
    val displayName: String,
    val avatarUrl: String? = null,
    val quote: String? = null,
    val profession: String? = null
) {
    fun mapToFirestore(): MutableMap<String, Any?> {
        return mutableMapOf(
            "user_id" to userId,
            "display_name" to displayName,
            "quote" to quote,
            "profession" to profession,
            "avatar_url" to avatarUrl
        )
    }

}
