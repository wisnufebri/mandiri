package com.test.wisnufebri.data.database.repository

import androidx.lifecycle.MutableLiveData
import com.test.wisnufebri.data.database.remote.TheMovieDatabaseAPI
import com.test.wisnufebri.data.model.Credit
import com.test.wisnufebri.data.model.Image
import com.test.wisnufebri.data.model.Person
import com.test.wisnufebri.util.ServiceBuilder

class PersonRepository : BaseRepository() {
    private val peopleService =
        ServiceBuilder.buildService(TheMovieDatabaseAPI.PeopleService::class.java)

    suspend fun loadDetails(id: Int, errorText: (String) -> Unit) =
        loadCall({ peopleService.fetchDetails(id) }, MutableLiveData<Person>(), errorText)

    suspend fun loadCredits(id: Int, errorText: (String) -> Unit) =
        loadListCall(
            { peopleService.fetchCredits(id) },
            MutableLiveData<List<Credit>>(),
            errorText
        )

    suspend fun loadImages(id: Int, errorText: (String) -> Unit) =
        loadListCall({ peopleService.fetchImages(id) }, MutableLiveData<List<Image>>(), errorText)
}