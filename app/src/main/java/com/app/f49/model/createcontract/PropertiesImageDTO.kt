package com.app.f49.model.createcontract

import com.google.gson.annotations.SerializedName

class PropertiesImageDTO {
    @SerializedName("Name")
    var name: String? = null
    @SerializedName("DataAsURLs")
    var dataAsURLs:String? = null
    @SerializedName("DataAsURL")
    var dataAsURL:String? = null
}
