package com.example.android.memeworld

/**
* Memeclass object which contains information about a particular meme
*/
class MemeClass(
    val subreddit:String,
    val author:String,
    val title:String,
    val imageUrl:String,
    val likeCount:Long,
    val postLink:String
) {
}