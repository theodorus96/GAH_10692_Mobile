package com.example.gah_10692_android


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.gah_10692_android.databinding.ActivityLoginBinding
import com.example.gah_10692_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginButton: Button = binding.loginButtonMain


        loginButton.setOnClickListener {
            val moveLogin = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(moveLogin)
        }
    }
}