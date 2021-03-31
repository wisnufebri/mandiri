package com.test.wisnufebri.data.config.response

interface BasePageListResponse<T> {
    var page: Int
    var results: List<T>
}