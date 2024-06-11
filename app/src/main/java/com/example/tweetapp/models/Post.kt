package com.example.tweetapp.models

data class Post (
    val text: String = "",
    val createdBy: User = User(),
    val createdAt: Long = 0L,
    val likedBy : ArrayList<String> = ArrayList()
)