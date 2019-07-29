package com.app.f49.model.uploadImage

import com.google.gson.annotations.SerializedName

class UploadImageDTO {
    var uri: String? = null
    @SerializedName("imgStr")
    var imageBase64: String? = null
    var soHopDong: String? = null
}