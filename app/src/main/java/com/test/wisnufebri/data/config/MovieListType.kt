package com.test.wisnufebri.data.config

enum class MovieListType {
    POPULAR {
        override fun toString() = "Popular"
    },
    SEARCH {
        override fun toString() = "Search"
    },
    UPCOMING {
        override fun toString() = "Upcoming"
    },
    IN_THEATERS {
        override fun toString() = "In Theaters"
    }
}
