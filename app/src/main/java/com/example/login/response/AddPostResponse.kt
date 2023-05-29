package com.example.login.response

import com.google.gson.annotations.SerializedName

data class AddPostResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
