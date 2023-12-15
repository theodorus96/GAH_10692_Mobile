package com.example.gah_10692_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.gah_10692_android.Model.User
import com.example.gah_10692_android.Model.dataUser
import com.example.gah_10692_android.api.UserApi
import com.example.gah_10692_android.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queue = Volley.newRequestQueue(this)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener{
            createUser()
        }
    }
        private fun createUser() {
            val user = User(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )

            val dataUser = dataUser(
                binding.namaEditText.text.toString(),
                binding.noIdentitasEditText.text.toString().toInt(),
                binding.nomorTeleponEditText.text.toString(),
                binding.alamatEditText.text.toString(),
                binding.institusiEditText.text.toString(),
            )
            val stringRequest : StringRequest =
                object : StringRequest(
                    Method.POST, UserApi.REGISTER_URL,
                    Response.Listener { response ->
                        Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                        val moveLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(moveLogin)
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String> {
                        val params: MutableMap<String, String> =
                            HashMap()
                        params["email"] = user.email
                        params["password"] = user.password
                        params["nama"] = dataUser.nama
                        params["no_identitas"] = dataUser.no_identitas.toString()
                        params["nomor_telepon"] = dataUser.nomor_telepon
                        params["alamat"] = dataUser.alamat
                        params["institusi"] = dataUser.institusi
                        return params
                    }
                }
            queue!!.add(stringRequest)
        }

    }
