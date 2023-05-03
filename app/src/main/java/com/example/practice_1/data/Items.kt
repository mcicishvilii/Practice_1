package com.example.practice_1.data

data class Items(
    val title:String,
    val desc: String,
    var image: Int?,
    val hasImage: HasImage
)

enum class HasImage {
    TRUE, FALSE
}


