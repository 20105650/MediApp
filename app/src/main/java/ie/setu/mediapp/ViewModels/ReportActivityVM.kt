package ie.setu.mediapp.ViewModels

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import ie.setu.mediapp.Model.Report
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ie.setu.mediapp.Model.Appointment
import ie.setu.mediapp.Model.Appointments
import ie.setu.mediapp.Model.Reports

class ReportActivityVM : ViewModel()  {

    val database = Firebase.database

    fun createReport(report: Report, callback: (Boolean) -> Unit) {
        val appointmentRef: DatabaseReference = database.getReference("reports")
        val apId = report.rpId
        appointmentRef.child(apId).setValue(report)
            .addOnSuccessListener {
                // Successfully inserted
                callback(true)
            }
            .addOnFailureListener {
                // Failed to insert
                callback(false)
            }
    }

    fun getMyReportList(drId:String,callback: (List<Reports>?) -> Unit) {
        // Query to retrieve items with type = 1
        val usersRef: DatabaseReference = database.getReference("reports")
        val query = usersRef.orderByChild("drId").equalTo(drId) // Specify type as double

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<Reports>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Reports::class.java)
                    item?.let {
                        itemList.add(it)
                    }
                }
                callback(itemList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                callback(null)
            }
        })
    }

    fun getMyReportPtList(drId:String,callback: (List<Reports>?) -> Unit) {
        // Query to retrieve items with type = 1
        val usersRef: DatabaseReference = database.getReference("reports")
        val query = usersRef.orderByChild("ptId").equalTo(drId) // Specify type as double

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<Reports>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Reports::class.java)
                    item?.let {
                        itemList.add(it)
                    }
                }
                callback(itemList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                callback(null)
            }
        })
    }

    fun getMyBookingAllList(callback: (List<Appointment>?) -> Unit) {

//        val categoriesRef: DatabaseReference = database.getReference("categories")
//        categoriesRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val categoryList = mutableListOf<CategoryList>()
//                for (categorySnapshot in snapshot.children) {


        // Query to retrieve items with type = 1
        val usersRef: DatabaseReference = database.getReference("appointments")
        ///val query = usersRef.orderByChild("ptId").equalTo(ptId) // Specify type as double

        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<Appointment>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Appointment::class.java)
                    item?.let {
                        itemList.add(it)
                    }
                }
                callback(itemList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                callback(null)
            }
        })
    }

    fun getMyReportAllList(callback: (List<Reports>?) -> Unit) {
        // Query to retrieve items with type = 1
        val usersRef: DatabaseReference = database.getReference("reports")
        //val query = usersRef.orderByChild("ptId").equalTo(drId) // Specify type as double
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<Reports>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Reports::class.java)
                    item?.let {
                        itemList.add(it)
                    }
                }
                callback(itemList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                callback(null)
            }
        })
    }
    fun createAppointment(appointment: Appointments, callback: (Boolean) -> Unit) {
        val appointmentRef: DatabaseReference = database.getReference("appointments")
        val apId = appointment.apId
        appointmentRef.child(apId).setValue(appointment)
            .addOnSuccessListener {
                // Successfully inserted
                callback(true)
            }
            .addOnFailureListener {
                // Failed to insert
                callback(false)
            }
    }
    fun getMyBookingList(ptId:String,callback: (List<Appointment>?) -> Unit) {
        // Query to retrieve items with type = 1
        val usersRef: DatabaseReference = database.getReference("appointments")
        val query = usersRef.orderByChild("ptId").equalTo(ptId) // Specify type as double

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<Appointment>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Appointment::class.java)
                    item?.let {
                        itemList.add(it)
                    }
                }
                callback(itemList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                callback(null)
            }
        })
    }

    fun getMyAppoinmentList(ptId:String,callback: (List<Appointment>?) -> Unit) {
        // Query to retrieve items with type = 1
        val usersRef: DatabaseReference = database.getReference("appointments")
        val query = usersRef.orderByChild("drId").equalTo(ptId) // Specify type as double

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<Appointment>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Appointment::class.java)
                    item?.let {
                        itemList.add(it)
                    }
                }
                callback(itemList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                callback(null)
            }
        })
    }

    fun cancelBooking(apId: String, status: String, callback: (Boolean) -> Unit) {
        // Reference the category item in the database using catId
        val categoriesRef: DatabaseReference = database.getReference("appointments")
        val categoryRef = categoriesRef.child(apId)

        // Convert updated category to a map of properties to update
        val updates = mapOf(
            "apId" to apId,
            "status" to status
        )

        // Update the category in the database
        categoryRef.updateChildren(updates)
            .addOnSuccessListener {
                // Update successful
                callback(true)
            }
            .addOnFailureListener {
                // Update failed
                callback(false)
            }
    }

}