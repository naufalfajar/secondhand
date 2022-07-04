package id.finalproject.binar.secondhand.model.network.request


import com.google.gson.annotations.SerializedName

data class RegisRequest(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone_number")
    val phoneNumber: Long
)