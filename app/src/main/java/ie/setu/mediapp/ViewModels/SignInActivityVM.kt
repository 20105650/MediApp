package ie.setu.mediapp.ViewModels

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ie.setu.mediapp.view.SignUpActivity
import ie.setu.mediapp.Model.Users

class SignInActivityVM : ViewModel()  {

    val database = Firebase.database

    fun creatingAnNewUser(name:String,email:String,place:String,phone:String,cat:String,catName:String,pass:String,type:Int, callback: (Boolean,String?) -> Unit) {
        // Get a reference to your Firebase database
        println(" REG btn clicked")

        val myRef = database.getReference("users")

        val userId = myRef.push().key
        val user = SignUpActivity.User(
            id = "$userId",
            name = "$name",
            email = "$email",
            phone = "$phone",
            type = type,
            password = "$pass",
            catId = "$cat",
            catName = "$catName",
            place = "$place"
        )

        // Push the user data to the database under the generated key
        userId?.let {
                myRef.child(userId).setValue(user)
                .addOnSuccessListener {
                    // Data successfully written
                    println("Registratio Data successfully written to the database.")
                    callback(true,userId)
                }
                .addOnFailureListener { e ->
                    // Failed to write data
                    println("Error writing data to the database: $e")
                    callback(false,"")
                }
        } ?: run {
            println("Error generating unique key.")
        }
    } //creatingAnNewUser Ends here

    fun userValidation(email: String,password: String,isGoogleLogin: Boolean, callback: (Boolean,Users?) -> Unit) {
        /// val database = Firebase.database
        println("user validation enter")
        val usersRef = database.getReference("users")

        // Query the database to check if the email exists
        usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Check if any user has the same email
                    if (dataSnapshot.exists()) {
                        // Email exists, retrieve user info
                        val user = dataSnapshot.children.first().getValue(Users::class.java)
                        if (user != null) {
                            println("User exists:")
                            if (isGoogleLogin)
                            {
                                callback(true, user)
                            }
                            else {
                                if (user.password == password) {
                                    // Do something with user information
                                    callback(true, user)
                                } else {
                                    callback(false, null)
                                }
                            }
                        } else {
                            println("Email does not exist")
                            callback(false,null)
                        }

                    } else {
                        // Email does not exist
                        callback(false,null)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors
                    println("Error reading database: ${databaseError.toException()}")
                    callback(false,null) // Return null if an error occurs
                }
            }
        )
    } //Uservalidation Ends here
}