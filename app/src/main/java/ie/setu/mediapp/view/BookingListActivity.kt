package ie.setu.mediapp.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.setu.mediapp.Model.Appointment
import ie.setu.mediapp.R
import ie.setu.mediapp.ViewModels.ReportActivityVM
import ie.setu.mediapp.view.Adapter.ItemBookingAdapter

class BookingListActivity : AppCompatActivity() {


    val bookingList = mutableListOf<Appointment>()
    private val viewModel: ReportActivityVM by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemBookingAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_booking_list)
        // Get the SharedPreferences instance
        sharedPreferences = getSharedPreferences("auth_pref", Context.MODE_PRIVATE)
        // Initialize your RecyclerView and Adapter
        recyclerView = findViewById(R.id.bookingRecyclerView)
        ///recyclerView = findViewById(R.id.slotRecyclerView)
        adapter = ItemBookingAdapter(bookingList, ::onSelectAction)
        //recyclerView.layoutManager = LinearLayoutManager(this)
        // Set a horizontal LinearLayoutManager on the RecyclerView
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        findViewById<ImageView>(R.id.back_button).setOnClickListener {
            finish()
        }

        getBookingList()
    }

    private fun getBookingList() {

        viewModel.getMyBookingAllList() { items ->
            if (items != null) {
                // Successfully retrieved items of type 1
                bookingList.removeAll(bookingList)
                bookingList.addAll(items)
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
//        viewModel.cancelBooking(bookingList[position].apId, "canceled") { isSuccess ->
//            if (isSuccess) {
//                // Update successful
//                println("Successfully canceled.")
//                getBookingList()
//            } else {
//                // Update failed
//                println("Failed to update booking.")
//                Toast.makeText(this, "Failed to  update booking.", Toast.LENGTH_SHORT).show()
//            }
//        }
    }
}

