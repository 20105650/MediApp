package ie.setu.mediapp.view

import android.content.Intent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import ie.setu.mediapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import ie.setu.mediapp.databinding.ActivitySignUpBinding
import androidx.core.view.isVisible
import ie.setu.mediapp.Model.Category
import ie.setu.mediapp.Model.CategoryList
import ie.setu.mediapp.ViewModels.SignInActivityVM
import android.content.SharedPreferences
import android.view.View
import android.widget.Spinner
import ie.setu.mediapp.ViewModels.CategoryActivityVM

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignInActivityVM by viewModels()
    private val catviewModel: CategoryActivityVM by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001
    var userType = 0
    var loginType = 0
    val categoryList = mutableListOf<CategoryList>()
    var cat_value = ""
    var cat_value_id = ""


    val catSpinArray = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

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
        sharedPreferences = getSharedPreferences("auth_pref", Context.MODE_PRIVATE)

        ////GOOGLE LOGIN
        configureGoogleSignIn()
        val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.setOnClickListener {
            signIn()
        }

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

        // Find the Spinner by its ID
        val spinner: Spinner = findViewById(R.id.mySpinner)

        // Create an ArrayAdapter
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            catSpinArray
        )
        // Set the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the Spinner
        spinner.adapter = adapter

        // Optionally, you can set an item selected listener
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Handle the selected item
                val selectedItem = parent.getItemAtPosition(position).toString()

                cat_value_id = categoryList[position].catId
                cat_value = categoryList[position].catName
                println("SANTHOSH Selected item: $selectedItem")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle no item selected case
            }
        }

        getAllCategories()



    }//onCreate -- Ends here


    private fun getAllCategories() {
        catviewModel.getCategories { categories ->
            if (categories != null) {
                // Successfully retrieved categories
                categoryList.removeAll(categoryList)
                categoryList.addAll(categories)
                catSpinArray.removeAll(catSpinArray)
                for (categorySnapshot in categories) {
                    catSpinArray.add(categorySnapshot.catName)
                }
                adapter.notifyDataSetChanged()
                //adapter.notifyDataSetChanged()
            } else {
                // Failed to retrieve categories
                println("Failed to retrieve categories")
            }
        }
    }


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
        val intent = Intent(this, DrActivity::class.java)
        startActivity(intent)
        finish() // Optional: finish the current activity so the user can't navigate back
    }
    private fun navigateToAdminHome() {
        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
        finish() // Optional: finish the current activity so the user can't navigate back
    }

    ////GOOGLE LOGIN
    private fun configureGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }



}