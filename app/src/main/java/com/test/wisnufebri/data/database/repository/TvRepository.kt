package com.test.wisnufebri.data.database.repository

import androidx.lifecycle.MutableLiveData
import com.test.wisnufebri.data.database.remote.TheMovieDatabaseAPI
import com.test.wisnufebri.data.model.Cast
import com.test.wisnufebri.data.model.TvShow
import com.test.wisnufebri.data.model.TvShowDetails
import com.test.wisnufebri.data.model.Video
import com.test.wisnufebri.util.ServiceBuilder

class TvRepository : BaseRepository() {
    private val tvService =
        ServiceBuilder.buildService(TheMovieDatabaseAPI.TvService::class.java)

    suspend fun loadDiscoverList(id: Int, errorText: (String) -> Unit) =
        loadPageListCall(
            {tvService.fetchDiscoveryList(id)},
            MutableLiveData<List<TvShow>>(),
            errorText
        )

    suspend fun loadDetails(id: Int, errorText: (String) -> Unit) =
        loadCall({tvService.fetchDetails(id)}, MutableLiveData<TvShowDetails>(), errorText)

    suspend fun loadCredits(id: Int, errorText: (String) -> Unit) =
        loadListCall({ tvService.fetchCredits(id) }, MutableLiveData<List<Cast>>(), errorText)

    suspend fun loadVideos(id: Int, errorText: (String) -> Unit) =
        loadListCall({ tvService.fetchVideos(id) }, MutableLiveData<List<Video>>(), errorText)
}