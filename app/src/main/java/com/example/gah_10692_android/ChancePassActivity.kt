package com.example.gah_10692_android

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.RequestQueue
import android.widget.Button
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.gah_10692_android.Model.User
import com.example.gah_10692_android.api.UserApi
import com.example.gah_10692_android.databinding.ActivityChancePassBinding
import com.example.gah_10692_android.databinding.ActivityLoginBinding
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class ChancePassActivity : AppCompatActivity() {
    private lateinit var chancePassLayout: ConstraintLayout
    private lateinit var binding: ActivityChancePassBinding
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chance_pass)
        binding = ActivityChancePassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chancePassLayout = binding.chancePassLayout
        val btnChancePass: Button = binding.confirmButton
        queue = Volley.newRequestQueue(this)

        btnChancePass.setOnClickListener {
            val email: String = binding.emailEditText.text.toString()
            val password: String = binding.newPasswordEditText.text.toString()
            val stringRequest: StringRequest = object :
                StringRequest(
                    Method.POST, UserApi.CHANGEPASS_URL,
                    Response.Listener { response ->
                        val gson = Gson()

                        val jsonObject = JSONObject(response)
                        val data = jsonObject.getJSONObject("data")
                        val user =
                            gson.fromJson(data.getJSONObject("user").toString(), User::class.java)

                        val moveHome = Intent(this@ChancePassActivity, LoginActivity::class.java)
                        val sp = getSharedPreferences("USER_LOGIN", Context.MODE_PRIVATE)
                        val editor = sp.edit()

                        editor.apply {
                            putInt("id", user.id_user!!.toInt())
                            putString("email", user.email)
                            putString("password", user.password)
                        }.apply()

                        startActivity(moveHome)
                        finish()
                    }, Response.ErrorListener { error ->

                        try {
                            val responseBody =
                                String(error.networkResponse.data, StandardCharsets.UTF_8)
                            val errors = JSONObject(responseBody)
                            val message = errors.getString("message")
                            Toast.makeText(
                                this@ChancePassActivity,
                                message,
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@ChancePassActivity,
                                "Error: " + e.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            e.printStackTrace()
                        }
                    }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = java.util.HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    return headers
                }

                @Throws(AuthFailureError::class)
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["email"] = email
                    params["password"] = password
                    return params
                }

            }
            queue!!.add(stringRequest)

        }
    }
}