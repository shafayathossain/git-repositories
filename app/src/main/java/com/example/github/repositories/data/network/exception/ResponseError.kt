package com.example.github.repositories.data.network.exception

import com.google.gson.annotations.SerializedName

data class ResponseError(
    @SerializedName("message") val message: String,
)