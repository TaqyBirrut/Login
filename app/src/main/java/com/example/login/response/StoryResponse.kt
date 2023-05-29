package com.example.login.response

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class StoryResponse(

	@ColumnInfo(name = "listStory")
	@field:SerializedName("listStory")
	val listStory: MutableList<ListStoryItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ListStoryItem(

	@ColumnInfo(name = "photoUrl")
	@field:SerializedName("photoUrl")
	val photoUrl: String,

	@ColumnInfo(name = "createdAt")
	@field:SerializedName("createdAt")
	val createdAt: String,

	@ColumnInfo(name = "name")
	@field:SerializedName("name")
	val name: String,

	@ColumnInfo(name = "description")
	@field:SerializedName("description")
	val description: String,

	@ColumnInfo(name = "lon")
	@field:SerializedName("lon")
	val lon: Any,

	@ColumnInfo(name = "id")
	@field:SerializedName("id")
	val id: String,

	@ColumnInfo(name = "lat")
	@field:SerializedName("lat")
	val lat: Any
)
