package id.finalproject.binar.secondhand.model.network.response.seller


import com.google.gson.annotations.SerializedName

data class GetBannerItem(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)