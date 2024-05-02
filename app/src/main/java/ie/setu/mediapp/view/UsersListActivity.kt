package ie.setu.mediapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.setu.mediapp.R
import ie.setu.mediapp.view.Adapter.ItemUsersListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.setu.mediapp.Model.Users
import android.widget.ImageView
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import ie.setu.mediapp.ViewModels.UserActivityVM

class UsersListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: ItemUsersListAdapter
    val usersList = mutableListOf<Users>()
    private val viewModel: UserActivityVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_users_list)
        // Initialize your RecyclerView and Adapter
        recyclerView = findViewById(R.id.recyclerView)
        findViewById<ImageView>(R.id.back_button).setOnClickListener {
            finish()
        }
        adapter = ItemUsersListAdapter(usersList, ::onSelectAction)
        // Set a horizontal LinearLayoutManager on the RecyclerView
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        getDrList()
    }
    private fun getDrList() {

        viewModel.getAllUsersList { items ->
            if (items != null) {
                // Successfully retrieved items of type 1
                usersList.removeAll(usersList)
                usersList.addAll(items)
                adapter.notifyDataSetChanged()
//                for (item in items) {
//                    println("Name: ${item.name}, Email: ${item.email}, Phone: ${item.phone}, Type: ${item.type}, CatId: ${item.catId}, Place: ${item.place}")
//                }
            } else {
                // Failed to retrieve items
                println("Failed to retrieve items of type 1.")
            }
        }
    }

    private fun onSelectAction(position: Int) {
//        val intent = Intent(this, AppointmentCreateActivity::class.java)
//        //val user = Users("John Doe", "john.doe@example.com", "1234567890", 1, "password", "catId", "place")
//        intent.putExtra("drName", usersList[position].name)
//        intent.putExtra("drId", usersList[position].id)
//        startActivity(intent)
    }


}