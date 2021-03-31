package com.test.wisnufebri.data.config.response

import com.google.gson.annotations.SerializedName
import com.test.wisnufebri.data.config.response.BaseListResponse
import com.test.wisnufebri.data.model.Video

data class VideosResponse(
    @SerializedName("results")
    override var results: List<Video>
) : BaseListResponse<Video>
