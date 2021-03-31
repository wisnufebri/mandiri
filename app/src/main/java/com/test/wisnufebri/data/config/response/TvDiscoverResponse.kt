package com.test.wisnufebri.data.config.response

import com.google.gson.annotations.SerializedName
import com.test.wisnufebri.data.config.response.BasePageListResponse
import com.test.wisnufebri.data.model.TvShow

data class TvDiscoverResponse(
    @SerializedName("page")
    override var page: Int,

    @SerializedName("results")
    override var results: List<TvShow>
) : BasePageListResponse<TvShow>