package ie.setu.mediapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.setu.mediapp.R
import android.content.Intent
import android.os.Handler


class SplashScreen : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            // Start main activity
            startActivity(Intent(this, SignInActivity::class.java))
            // Close this activity
            finish()
        }, SPLASH_DELAY)
    }
}