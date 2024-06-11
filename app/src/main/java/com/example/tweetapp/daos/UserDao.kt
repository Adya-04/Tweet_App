package com.example.tweetapp.daos

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//to store the entries of users in database
class UserDao {
    private val db=FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    fun addUser(user: com.example.tweetapp.models.User?){
      //We want to avoid blocking the main UI while storing details in Firebase, so we're performing this operation in a background thread.
        GlobalScope.launch (Dispatchers.IO){
            //if user is not null then enter this below part of code
            user?.let {
                usersCollection.document(user.uid).set(it)
        }

        }
    }

    fun getUserById (uId:String): Task<DocumentSnapshot> {
        return usersCollection.document(uId).get()

    }
}