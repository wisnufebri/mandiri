package com.test.wisnufebri.data.config.response

import com.google.gson.annotations.SerializedName
import com.test.wisnufebri.data.config.response.BaseListResponse
import com.test.wisnufebri.data.model.Cast

data class CreditsResponse(
    @SerializedName("cast")
    override var results: List<Cast>
) : BaseListResponse<Cast>
