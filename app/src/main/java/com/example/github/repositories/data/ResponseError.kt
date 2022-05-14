package com.example.github.repositories.data

import com.google.gson.annotations.SerializedName

data class ResponseError(
    @SerializedName("message") val message: String,
)