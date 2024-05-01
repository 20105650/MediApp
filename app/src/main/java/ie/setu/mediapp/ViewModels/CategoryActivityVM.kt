package ie.setu.mediapp.ViewModels

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ie.setu.mediapp.view.CategoryActivity
import ie.setu.mediapp.Model.Category
import ie.setu.mediapp.Model.CategoryList

class CategoryActivityVM : ViewModel() {
    val database = Firebase.database
    fun insertCategory(category: Category, callback: (Boolean) -> Unit) {
        val categoriesRef: DatabaseReference = database.getReference("categories")
        val categoryId = category.catId
        categoriesRef.child(categoryId).setValue(category)
            .addOnSuccessListener {
                // Successfully inserted
                callback(true)
            }
            .addOnFailureListener {
                // Failed to insert
                callback(false)
            }
    }

    fun getCategories(callback: (List<CategoryList>?) -> Unit) {
        val categoriesRef: DatabaseReference = database.getReference("categories")
        categoriesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categoryList = mutableListOf<CategoryList>()
                for (categorySnapshot in snapshot.children) {
                    val catId = categorySnapshot.child("catId").getValue(String::class.java)
                    val catName = categorySnapshot.child("catName").getValue(String::class.java)
                    if (catId != null && catName != null) {
                        categoryList.add(CategoryList(catId, catName,false,false))
                    }
                }
                callback(categoryList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                callback(null)
            }
        })
    }

    fun deleteCategory(catId: String, callback: (Boolean) -> Unit) {
        val categoriesRef: DatabaseReference = database.getReference("categories")
        // Locate the category with the given catId in the categoriesRef
        categoriesRef.child(catId).removeValue()
            .addOnSuccessListener {
                // Successfully deleted
                callback(true)
            }
            .addOnFailureListener {
                // Failed to delete
                callback(false)
            }
    }
    fun editCategory(catId: String, updatedCategoryName: String, callback: (Boolean) -> Unit) {
        // Reference the category item in the database using catId
        val categoriesRef: DatabaseReference = database.getReference("categories")
        val categoryRef = categoriesRef.child(catId)

        // Convert updated category to a map of properties to update
        val updates = mapOf(
            "catId" to catId,
            "catName" to updatedCategoryName
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