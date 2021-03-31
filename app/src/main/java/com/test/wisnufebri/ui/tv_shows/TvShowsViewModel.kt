package com.test.wisnufebri.ui.tv_shows

import androidx.lifecycle.*
import com.test.wisnufebri.data.config.Event
import com.test.wisnufebri.data.config.GoToTvShow
import com.test.wisnufebri.data.database.repository.TvRepository
import com.test.wisnufebri.data.model.TvShow
import com.test.wisnufebri.util.BaseViewModel
import com.test.wisnufebri.util.extension.appendList
import com.test.wisnufebri.util.extension.liveDataBlockScope

class TvShowsViewModel : BaseViewModel(), GoToTvShow {

    private val tvShowRepository = TvRepository()
    private val loadedTvShowList: LiveData<List<TvShow>>
    private val tvShowsPage = MutableLiveData<Int>().apply { value = 1 }

    val tvShowList = MediatorLiveData<MutableList<TvShow>>()

    override val goToTvShowEvent: MutableLiveData<Event<TvShow>> = MutableLiveData()

    init {
        loadedTvShowList = tvShowsPage.switchMap {
            liveDataBlockScope {
                tvShowRepository.loadDiscoverList(it) { mSnackBarText.postValue(Event(it)) }
            }
        }
        tvShowList.addSource(loadedTvShowList) { it?.let { list -> tvShowList.appendList(list) } }
    }

    fun loadMoreTvShows() {
        tvShowsPage.value = tvShowsPage.value?.plus(1)
    }
}