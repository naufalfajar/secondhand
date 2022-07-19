package id.finalproject.binar.secondhand.model.network.response


import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String
)