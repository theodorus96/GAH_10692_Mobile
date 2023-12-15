package com.example.gah_10692_android.api

class UserApi {
    companion object {
        val BASE_URL = "http://192.168.107.172/GAH_10692_Laravel/public/api/"

        val GET_USER = BASE_URL + "user/"
        val REGISTER_URL = BASE_URL + "user/register"
        val UPDATE_URL = BASE_URL + "user/"
        val LOGIN_URL = BASE_URL + "user/login"
        val CHANGEPASS_URL = BASE_URL + "user/change-password"
    }
}