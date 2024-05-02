package ie.setu.mediapp.ViewModels

import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import ie.setu.mediapp.Model.Slots
import ie.setu.mediapp.Model.SlotsList


class SlotActivityVM : ViewModel()  {
    val database = Firebase.database

    fun insertSlots(slot: Slots, callback: (Boolean) -> Unit) {
        val categoriesRef: DatabaseReference = database.getReference("slots")
        val categoryId = slot.slotId
        categoriesRef.child(categoryId).setValue(slot)
            .addOnSuccessListener {
                // Successfully inserted
                callback(true)
            }
            .addOnFailureListener {
                // Failed to insert
                callback(false)
            }
    }

    fun getSlots(callback: (List<SlotsList>?) -> Unit) {
        val categoriesRef: DatabaseReference = database.getReference("slots")
        categoriesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val slotsList = mutableListOf<SlotsList>()
                for (categorySnapshot in snapshot.children) {
                    val slotId = categorySnapshot.child("slotId").getValue(String::class.java)
                    val slotName = categorySnapshot.child("slotName").getValue(String::class.java)
                    if (slotId != null && slotName != null) {
                        slotsList.add(SlotsList(slotId, slotName,false,false))
                    }
                }
                callback(slotsList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                callback(null)
            }
        })
    }

    fun deleteSlots(slotId: String, callback: (Boolean) -> Unit) {
        val categoriesRef: DatabaseReference = database.getReference("slots")
        // Locate the category with the given catId in the categoriesRef
        categoriesRef.child(slotId).removeValue()
            .addOnSuccessListener {
                // Successfully deleted
                callback(true)
            }
            .addOnFailureListener {
                // Failed to delete
                callback(false)
            }
    }

    fun editSlots(slotId: String, updatedSlotsName: String, callback: (Boolean) -> Unit) {
        // Reference the category item in the database using catId
        val categoriesRef: DatabaseReference = database.getReference("slots")
        val categoryRef = categoriesRef.child(slotId)

        // Convert updated category to a map of properties to update
        val updates = mapOf(
            "slotId" to slotId,
            "slotName" to updatedSlotsName
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