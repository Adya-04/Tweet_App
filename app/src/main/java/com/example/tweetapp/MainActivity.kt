package com.example.tweetapp


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tweetapp.daos.PostDao
import com.example.tweetapp.databinding.ActivityMainBinding
import com.example.tweetapp.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), IPostAdapter {
    private lateinit var postDao: PostDao
    private lateinit var adapter:PostAdapter
    private lateinit var binding: ActivityMainBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.fab.setOnClickListener{

            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }

        setUpRecyclerView()

        //acton bar thing
        setSupportActionBar(binding.toolbar)

        Log.d("MainActivity Tag","oncreate called")


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.opt_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when(item.itemId){
           R.id.Logout -> {
               // Handle logout action
               logout()
               true
           }
           R.id.CreatePost -> {
               // Handle create post action
               openCreatePostActivity()
               true
           }
           else -> super.onOptionsItemSelected(item)
           // Pass unhandled menu items to superclass

        }
    }

    private fun openCreatePostActivity() {
        // Navigate to the CreatePostActivity
        val intent = Intent(this, CreatePostActivity::class.java)
        startActivity(intent)
    }

    private fun logout() {
        auth.signOut()
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
        Toast.makeText(this,"Logout Successful !",Toast.LENGTH_SHORT).show()

    }

    private fun setUpRecyclerView() {
        postDao = PostDao()
        // Get the reference to the Firestore collection for posts
        val postsCollection = postDao.postCollections
        // Create a Firestore query to sort posts by creation time in descending order
        val query = postsCollection.orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
        // Set up FirestoreRecyclerOptions to configure the adapter
                                                                      // Map query results to instances of the Post class
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post::class.java).build()
        // Create an instance of the adapter with the FirestoreRecyclerOptions
        adapter = PostAdapter(recyclerViewOptions,this)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        //FirestoreRecyclerOptions:
        //This class is part of the Firebase UI library and is used to configure adapters that display Firestore data in RecyclerViews.
        //It helps in binding Firestore query results directly to the RecyclerView.
    }

    override fun onStart() {
        super.onStart()
        //As soon as our app starts, adapter starts listening all the changes
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }
}