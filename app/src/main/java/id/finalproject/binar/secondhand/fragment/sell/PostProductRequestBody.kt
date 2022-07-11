package id.finalproject.binar.secondhand.fragment.sell

import okhttp3.MultipartBody
import okhttp3.RequestBody

class PostProductRequestBody(
    val nameBody: RequestBody,
    val priceBody: RequestBody,
    val descriptionBody: RequestBody,
    val categoryBody: RequestBody,
    val locationBody: RequestBody,
    val image: MultipartBody.Part
)
