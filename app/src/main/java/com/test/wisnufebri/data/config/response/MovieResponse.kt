package com.test.wisnufebri.data.config.response

import com.google.gson.annotations.SerializedName
import com.test.wisnufebri.data.config.response.BasePageListResponse
import com.test.wisnufebri.data.model.Movie

data class MoviesResponse(
    @SerializedName("page")
    override var page: Int,

    @SerializedName("results")
    override var results: List<Movie>
) : BasePageListResponse<Movie>
