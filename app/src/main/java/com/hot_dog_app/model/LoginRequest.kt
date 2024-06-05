package com.hot_dog_app.model

import com.google.gson.annotations.SerializedName

class LoginRequest(
    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String
)