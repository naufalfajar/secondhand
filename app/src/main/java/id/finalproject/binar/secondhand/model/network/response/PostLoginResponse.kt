package id.finalproject.binar.secondhand.model.network.response


import com.google.gson.annotations.SerializedName

data class PostLoginResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)