package ie.setu.mediapp.view

import android.content.Intent
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import ie.setu.mediapp.R
import ie.setu.mediapp.databinding.ActivitySignInBinding
import ie.setu.mediapp.Model.Users
import ie.setu.mediapp.ViewModels.SignInActivityVM
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val viewModel: SignInActivityVM by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("auth_pref", Context.MODE_PRIVATE)

        //Not registered link click action
        binding.signupTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        //Loginbutton click action
        binding.loginButton.setOnClickListener {
            Log.d("tag", "login button clicked")
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                viewModel.userValidation(email,pass,false) { status,user ->
                    if (status)
                    {
                        Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show()
                        if (user != null) {
                            loginUser(user)
                        }
                    }
                    else
                    {
                        Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }


    }//onCreate Ends here

    private fun loginUser(user:Users) {
        // Update authentication status in SharedPreferences
        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
        sharedPreferences.edit().putString("name", user.name).apply()
        sharedPreferences.edit().putString("email", user.email).apply()
        sharedPreferences.edit().putInt("type", user.type).apply()
        sharedPreferences.edit().putString("id", user.id).apply()
        sharedPreferences.edit().putString("image", user.image).apply()

        when ( user.type) {
            0 ->  navigateToHome()
            1 -> navigateToDRHome()
            3 -> navigateToAdminHome()
            else -> { // Note the block
                print("user type is not available")
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, PatientActivity::class.java)
        startActivity(intent)
        finish() // Optional: finish the current activity so the user can't navigate back
    }
    private fun navigateToDRHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Optional: finish the current activity so the user can't navigate back
    }
    private fun navigateToAdminHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Optional: finish the current activity so the user can't navigate back
    }

}