package com.test.wisnufebri.data.model

import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("profile_path")
    var profilePath: String?
)