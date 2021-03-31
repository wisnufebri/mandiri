package com.test.wisnufebri.data.config.response

import com.google.gson.annotations.SerializedName
import com.test.wisnufebri.data.config.response.BaseListResponse
import com.test.wisnufebri.data.model.Image

data class PersonImagesResponse(
    @SerializedName("profiles")
    override var results: List<Image>
) : BaseListResponse<Image>