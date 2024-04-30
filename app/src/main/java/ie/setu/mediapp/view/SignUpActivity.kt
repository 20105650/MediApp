package ie.setu.mediapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import ie.setu.mediapp.R
import ie.setu.mediapp.databinding.ActivitySignUpBinding
import androidx.core.view.isVisible
import ie.setu.mediapp.ViewModels.SignInActivityVM
import android.content.SharedPreferences

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignInActivityVM by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    var userType = 0
    var loginType = 0
    var cat_value = ""
    var cat_value_id = ""

    data class User(
        val id: String = "",
        val name: String = "",
        val email: String = "",
        val phone: String = "",
        val type: Int = 0,
        val password: String = "",
        val catId: String = "",
        val catName: String = "",
        val place: String = ""
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Login link click action
        binding.signinTextView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        uiUpdate()
        val userRadioGroups = findViewById<RadioGroup>(R.id.userRadioGroup)
        val userRdBtn1 = findViewById<RadioButton>(R.id.patient_radio_btn)
        val userRdBtn2 = findViewById<RadioButton>(R.id.doctor_radio_btn)

        val loginRadioGroups = findViewById<RadioGroup>(R.id.loginRadioGroup)
        val loginRdBtn1 = findViewById<RadioButton>(R.id.email_radio_btn)
        val loginRdBtn2 = findViewById<RadioButton>(R.id.google_radio_btn)
        //User type radio button change
        userRadioGroups.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            when (radio) {
                userRdBtn1 -> {
                    // some code
                    userType = 0
                    uiUpdate()
                }
                userRdBtn2 -> {
                    // some code
                    userType = 1
                    uiUpdate()
                }
            }
        } //User type radio button change  -- Ends here

        //login type radio button change
        loginRadioGroups.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            when (radio) {
                loginRdBtn1 -> {
                    // some code
                    loginType = 0
                    uiUpdate()
                }
                loginRdBtn2 -> {
                    // some code
                    loginType = 1
                    uiUpdate()
                }
            }
        } //login type radio button change -- Ends here

        //Signup button onclick action
        binding.button.setOnClickListener {
            println("Rigistration page signup button clicked")
            val name = binding.nameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val place = binding.placeEt.text.toString()
            val phone = binding.phoneEt.text.toString()
            ///val cat = binding.catEt.text.toString()
            val pass = binding.passET.text.toString()

            if (userType == 0)
            {
                println("user is a patient")
                if (email.isNotEmpty() && pass.isNotEmpty() && name.isNotEmpty()) {
                    println("patient going to register")
                    loginUser(name,email,place,phone,cat_value_id,cat_value,pass,userType)
                }
                else
                {
                    Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                if (email.isNotEmpty() && pass.isNotEmpty() && name.isNotEmpty() && place.isNotEmpty() && phone.isNotEmpty() && cat_value != "") {
                    loginUser(name,email,place,phone,cat_value_id,cat_value,pass,userType)
                }
                else
                {
                    Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
                }
            }
        }  //Signup button onclick action -- Ends here

    }//onCreate -- Ends here


    // signup Ui updation basedon radio button change
    fun uiUpdate() {

        if (loginType == 0)
        {
            if (userType == 0)
            {
                binding.nameLayout.isVisible = true
                binding.emailLayout.isVisible = true
                binding.placeLayout.isVisible = false
                binding.phoneLayout.isVisible = false
                binding.catLayout.isVisible = false
                binding.passwordLayout.isVisible = true
                binding.button.isVisible = true
                binding.signInButton.isVisible = false
            }
            else
            {
                binding.nameLayout.isVisible = true
                binding.emailLayout.isVisible = true
                binding.placeLayout.isVisible = true
                binding.phoneLayout.isVisible = true
                binding.catLayout.isVisible = true
                binding.passwordLayout.isVisible = true
                binding.button.isVisible = true
                binding.signInButton.isVisible = false
            }
        }
        else
        {
            if (userType == 0)
            {
                binding.nameLayout.isVisible = false
                binding.emailLayout.isVisible = false
                binding.placeLayout.isVisible = false
                binding.phoneLayout.isVisible = false
                binding.catLayout.isVisible = false
                binding.passwordLayout.isVisible = false
                binding.button.isVisible = false
                binding.signInButton.isVisible = true
            }
            else
            {
                binding.nameLayout.isVisible = false
                binding.emailLayout.isVisible = false
                binding.placeLayout.isVisible = true
                binding.phoneLayout.isVisible = true
                binding.catLayout.isVisible = true
                binding.passwordLayout.isVisible = false
                binding.button.isVisible = false
                binding.signInButton.isVisible = true
            }
        }
    }  // signup Ui updation basedon radio button change -- Ends here

    // loginUser Function -- Starts here
    private fun loginUser(name:String,email:String,place:String,phone:String,cat:String,catName:String,pass:String,type:Int) {
        println("Entered loginuser function")
        viewModel.creatingAnNewUser(name,email,place,phone,cat,catName,pass,userType){ status,id ->
            if (status)
            {
                Toast.makeText(this, "Registration successfully", Toast.LENGTH_SHORT).show()
                // Update authentication status in SharedPreferences
                sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                sharedPreferences.edit().putString("name", name).apply()
                sharedPreferences.edit().putString("email", email).apply()
                sharedPreferences.edit().putInt("type", userType).apply()
                sharedPreferences.edit().putString("id", id).apply()

                when (type) {
                    0 ->  navigateToHome()
                    1 -> navigateToDRHome()
                    3 -> navigateToAdminHome()
                    else -> { // Note the block
                        print("user type is not available")
                    }
                }
            }
            else
            {
                println("Registration failed")
                Toast.makeText(this, "Registration failed, please try again", Toast.LENGTH_SHORT).show()
            }
        }
    } // loginUser Function -- Ends here

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
        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
        finish() // Optional: finish the current activity so the user can't navigate back
    }



}