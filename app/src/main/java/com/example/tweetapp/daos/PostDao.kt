package com.example.tweetapp.daos

import com.example.tweetapp.models.Post
import com.example.tweetapp.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
    val db = FirebaseFirestore.getInstance()
    val postCollections = db.collection("posts")
    val auth = Firebase.auth

    fun addPost(text: String){
        //!! - we re assuring that auth.currentuser cannot be null
        val currentUserId = auth.currentUser!!.uid
        //we can apply await operation to coroutines only that's why we have created it
        GlobalScope.launch {
        val userDao = UserDao()
        //since the below one we re getting is a task so we can apply await to it
        val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!
            //to convert that task into the object of user we have done this .toobject...

            val currentTime = System.currentTimeMillis()
            val post = Post(text, user,currentTime)
            postCollections.document().set(post)
        }
    }

    fun getPostbyId(postId: String): Task<DocumentSnapshot> {
        return postCollections.document(postId).get()


    }
    fun updateLikes(postId: String){
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val post= getPostbyId(postId).await().toObject(Post::class.java)!!
            val isLiked = post.likedBy.contains(currentUserId)
            if(isLiked){
                post.likedBy.remove(currentUserId)
            }
            else{
                post.likedBy.add(currentUserId)
            }
            postCollections.document(postId).set(post)
        }
    }
}