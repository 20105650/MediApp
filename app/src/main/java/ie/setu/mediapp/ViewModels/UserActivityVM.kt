package ie.setu.mediapp.ViewModels

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ie.setu.mediapp.Model.Users


class UserActivityVM : ViewModel()  {
    val database = Firebase.database

    fun getDrList(callback: (List<Users>?) -> Unit) {
        // Query to retrieve items with type = 1
        val usersRef: DatabaseReference = database.getReference("users")
        val query = usersRef.orderByChild("type").equalTo(1.0) // Specify type as double

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<Users>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Users::class.java)
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

    fun updateUserProfile(id: String, img: String, callback: (Boolean) -> Unit) {
        // Reference the category item in the database using catId
        val categoriesRef: DatabaseReference = database.getReference("users")
        val categoryRef = categoriesRef.child(id)

        // Convert updated category to a map of properties to update
        val updates = mapOf(
            "id" to id,
            "image" to img
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

    fun getAllUsersList(callback: (List<Users>?) -> Unit) {
        // Query to retrieve items with type = 1
        val usersRef: DatabaseReference = database.getReference("users")
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<Users>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Users::class.java)
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

    fun updateDrLocation(id: String,latitude: String,longitude: String, callback: (Boolean) -> Unit) {
        // Reference the category item in the database using catId
        val categoriesRef: DatabaseReference = database.getReference("users")
        val categoryRef = categoriesRef.child(id)

        // Convert updated category to a map of properties to update
        val updates = mapOf(
            "id" to id,
            "latitude" to latitude,
            "longitude" to longitude
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

    fun updateUserDetails(id: String,name: String,height: String,weight: String,dob: String,gent: String, callback: (Boolean) -> Unit) {
        // Reference the category item in the database using catId

        val categoriesRef: DatabaseReference = database.getReference("users")
        val categoryRef = categoriesRef.child(id)

        // Convert updated category to a map of properties to update
        val updates = mapOf(
            "id" to id,
            "name" to name,
            "height" to height,
            "weight" to weight,
            "dob" to dob,
            "gent" to gent
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

    fun getUserData(id: String,callback: (Users?) -> Unit) {
        val usersRef: DatabaseReference = database.getReference("users")
        val query = usersRef.orderByChild("id").equalTo(id) // Specify type as double
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //val itemList = mutableListOf<Users>()
                val user = snapshot.children.first().getValue(Users::class.java)
                if (user != null)
                {
                    callback(user)
                }
                else
                {
                    callback(null)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                callback(null)
            }
        })
    }



}