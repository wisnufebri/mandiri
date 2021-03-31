package com.test.wisnufebri.ui.person_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.wisnufebri.data.config.Event
import com.test.wisnufebri.data.config.GoToCredit
import com.test.wisnufebri.data.config.GoToImage
import com.test.wisnufebri.data.database.repository.PersonRepository
import com.test.wisnufebri.data.model.Credit
import com.test.wisnufebri.data.model.Image
import com.test.wisnufebri.data.model.Person
import com.test.wisnufebri.util.BaseViewModel
import com.test.wisnufebri.util.extension.liveDataBlockScope

class PersonDetailsViewModelFactory(private val personId: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PersonDetailsViewModel(personId) as T
    }
}

class PersonDetailsViewModel(personId: Int) : BaseViewModel(), GoToImage, GoToCredit {

    private val personRepository = PersonRepository()
    val person: LiveData<Person>
    val imageList: LiveData<List<Image>>
    val creditList: LiveData<List<Credit>>

    override val goToImageEvent: MutableLiveData<Event<Image>> = MutableLiveData()
    override val goToCreditEvent: MutableLiveData<Event<Credit>> = MutableLiveData()

    init {
        person = liveDataBlockScope {
            personRepository.loadDetails(personId) { mSnackBarText.postValue(Event(it)) }
        }

        imageList = liveDataBlockScope {
            personRepository.loadImages(personId) { mSnackBarText.postValue(Event(it)) }
        }

        creditList = liveDataBlockScope {
            personRepository.loadCredits(personId) { mSnackBarText.postValue(Event(it)) }
        }
    }
}