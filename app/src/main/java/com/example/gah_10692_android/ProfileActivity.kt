package com.example.gah_10692_android

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.gah_10692_android.Model.User
import com.example.gah_10692_android.Model.dataUser
import com.example.gah_10692_android.api.UserApi
import com.example.gah_10692_android.databinding.ActivityProfileBinding
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class ProfileActivity : AppCompatActivity() {

    var sharedPreferences: SharedPreferences? = null
    private var UserId: Int = 0
    private var password: String = ""
    private var queue: RequestQueue? = null
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queue = Volley.newRequestQueue(this)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListener()
    }

    private fun setupListener() {
        val id = getSharedPreferences("USER_LOGIN", Context.MODE_PRIVATE).getInt("id", 0).toLong()
        getUserById(id)
        val btnUpdateUser: Button = findViewById(R.id.buttonEdit)

        btnUpdateUser.setOnClickListener {
            updateUser(id)
        }
    }
        private fun getUserById(id: Long){
            binding.editTextEmail.text.toString()
            binding.editTextPassword.text.toString()
            binding.editTextNama.text.toString()
            binding.editTextNomorIdentitas.text.toString()
            binding.editTextNomorTelepon.text.toString()
            binding.editTextAlamat.text.toString()
            binding.editTextInstitusi.text.toString()

            val stringRequest: StringRequest = object :
                StringRequest(
                    Method.GET, UserApi.GET_USER + id,
                    Response.Listener { response ->
                        val gson = Gson()
                        val json = JSONObject(response)
                        Log.e("RESPONSEEEEE", json.toString())
                        var user = gson.fromJson(json.getJSONObject("data").toString(), User::class.java)
                        Log.e("USERRRRR", user.toString())
                        user.data_user = gson.fromJson(json.getJSONObject("data_user").toString(), dataUser::class.java)
                        val dataUser = user.data_user!!

                        binding.editTextEmail.setText(user.email)
                        binding.editTextPassword.setText(user.password)
                        binding.editTextNama.setText(dataUser.nama)
                        binding.editTextNomorIdentitas.setText(dataUser.no_identitas.toString())
                        binding.editTextNomorTelepon.setText(dataUser.nomor_telepon)
                        binding.editTextAlamat.setText(dataUser.alamat)
                        binding.editTextInstitusi.setText(dataUser.institusi)

                    },
                    Response.ErrorListener{ error ->

                        try{
                            val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                            val errors = JSONObject(responseBody)
                            Toast.makeText(this@ProfileActivity, errors.toString(), Toast.LENGTH_SHORT).show()
                        } catch (e: Exception){
                            Toast.makeText(this@ProfileActivity, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = "Bearer " + getSharedPreferences("USER_LOGIN", Context.MODE_PRIVATE).getString("token", "")
                    return headers
                }
            }
            queue!!.add(stringRequest)

        }

    private fun updateUser(id: Long){
         binding.editTextEmail.text.toString()
        binding.editTextPassword.text.toString()
        binding.editTextNama.text.toString()
        binding.editTextNomorIdentitas.text.toString()
        binding.editTextNomorTelepon.text.toString()
        binding.editTextAlamat.text.toString()
        binding.editTextInstitusi.text.toString()

        val user = User(
            binding.editTextEmail.text.toString(),
            binding.editTextPassword.text.toString()
        )

        val dataUser = dataUser(
            binding.editTextNama.text.toString(),
            binding.editTextNomorIdentitas.text.toString().toInt(),
            binding.editTextNomorTelepon.text.toString(),
            binding.editTextAlamat.text.toString(),
            binding.editTextInstitusi.text.toString(),
        )

        Log.e("ini user", user.toString())
        Log.e("ini dataUser", dataUser.toString())

        val stringRequest: StringRequest =
            object: StringRequest(Method.PUT, UserApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()
                val json = JSONObject(response)
                Log.e("ini RESPONSEEEEE", json.toString())
                var user = gson.fromJson(json.getJSONObject("data").toString(), User::class.java)
                Log.e("ini User", user.toString())
                user.data_user = gson.fromJson(json.getJSONObject("data_user").toString(), com.example.gah_10692_android.Model.dataUser::class.java)
                val dataUser = user.data_user!!

                if(user != null)
                    Toast.makeText(this@ProfileActivity, "Data User Berhasil Diubah", Toast.LENGTH_SHORT).show(
                    )
            }, Response.ErrorListener { error ->

                Log.e("ini error", String(error.networkResponse.data, StandardCharsets.UTF_8))

                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception){
                    Toast.makeText(this@ProfileActivity, "Data User Gagal Diubah", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }){
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = "Bearer " + getSharedPreferences("USER_LOGIN", Context.MODE_PRIVATE).getString("token", "")
                    return headers
                }

                @Throws(AuthFailureError::class)
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["email"] = user.email
                    params["password"] = user.password
                    params["nama"] = dataUser.nama
                    params["no_identitas"] = dataUser.no_identitas.toString()
                    params["nomor_telepon"] = dataUser.nomor_telepon
                    params["alamat"] = dataUser.alamat
                    params["institusi"] = dataUser.institusi

                    return params
                }

//                override fun getBodyContentType(): String {
//                    return "application/json"
//                }
            }
        queue!!.add(stringRequest)
    }

    }

