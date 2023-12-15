package com.example.gah_10692_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.gah_10692_android.Model.Pegawai
import com.example.gah_10692_android.Model.User
import com.example.gah_10692_android.api.PegawaiApi
import com.example.gah_10692_android.api.UserApi
import com.example.gah_10692_android.databinding.ActivityLoginBinding
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class LoginActivity : AppCompatActivity() {
    private lateinit var loginLayout: ConstraintLayout
    private lateinit var binding: ActivityLoginBinding
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginLayout = binding.loginLayout
        val btnLogin: Button = binding.loginCustomerButton
        val btnLoginPegawai: Button = binding.loginPegawaiButton
        val btnRegister: Button = binding.registerButton
        val btnChancePass: Button = binding.forgetPasswordButton
        queue = Volley.newRequestQueue(this)

        btnRegister.setOnClickListener {
            val moveRegister = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(moveRegister)
        }

        btnChancePass.setOnClickListener {
            val moveChancePass = Intent(this@LoginActivity, ChancePassActivity::class.java)
            startActivity(moveChancePass)
        }

        btnLogin.setOnClickListener(View.OnClickListener {
            val email: String = binding.emailEditText.text.toString()
            val password: String = binding.passwordEditText.text.toString()

            val stringRequest: StringRequest = object :
                StringRequest(
                    Method.POST, UserApi.LOGIN_URL,
                    Response.Listener { response ->
                        val gson = Gson()

                        val jsonObject = JSONObject(response)
                        val data = jsonObject.getJSONObject("data")
                        val user = gson.fromJson(data.getJSONObject("user").toString(), User::class.java)

                        val moveHome = Intent(this@LoginActivity, ProfileActivity::class.java)
                        val sp = getSharedPreferences("USER_LOGIN", Context.MODE_PRIVATE)
                        val editor = sp.edit()

                        editor.apply {
                            putInt("id", user.id_user!!.toInt())
                            putString("email", user.email)
                            putString("password", user.password)
                            putString("token", data.getString("token"))
                        }.apply()

                        startActivity(moveHome)
                        finish()
                    }, Response.ErrorListener { error ->

                        try {
                            val responseBody =
                                String(error.networkResponse.data, StandardCharsets.UTF_8)
                            val errors = JSONObject(responseBody)
                            Toast.makeText(
                                this@LoginActivity,
                                errors.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@LoginActivity,
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
        })


        btnLoginPegawai.setOnClickListener(View.OnClickListener {
            val email: String = binding.emailEditText.text.toString()
            val password: String = binding.passwordEditText.text.toString()

            val stringRequest: StringRequest = object :
                StringRequest(
                    Method.POST, PegawaiApi.LOGIN_URL,
                    Response.Listener { response ->
                        val gson = Gson()

                        val jsonObject = JSONObject(response)
                        val data = jsonObject.getJSONObject("data")
                        val pegawai = gson.fromJson(data.getJSONObject("pegawai").toString(), Pegawai::class.java)

                        val moveHome = Intent(this@LoginActivity, HomeActivity::class.java)
                        val sp = getSharedPreferences("PEGAWAI_LOGIN", Context.MODE_PRIVATE)
                        val editor = sp.edit()

                        editor.apply {
                            putInt("id", pegawai.id_pegawai!!.toInt())
                            putString("email", pegawai.email)
                            putString("password", pegawai.password)
                        }.apply()

                        startActivity(moveHome)
                        finish()
                    }, Response.ErrorListener { error ->

                        try {
                            val responseBody =
                                String(error.networkResponse.data, StandardCharsets.UTF_8)
                            val errors = JSONObject(responseBody)
                            Toast.makeText(
                                this@LoginActivity,
                                errors.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@LoginActivity,
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
        })
    }
}