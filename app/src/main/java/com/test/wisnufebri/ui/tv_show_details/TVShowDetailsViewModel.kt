package com.test.wisnufebri.ui.tv_show_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.wisnufebri.data.config.Event
import com.test.wisnufebri.data.config.GoToCast
import com.test.wisnufebri.data.config.GoToVideo
import com.test.wisnufebri.data.database.repository.TvRepository
import com.test.wisnufebri.data.model.Cast
import com.test.wisnufebri.data.model.TvShowDetails
import com.test.wisnufebri.data.model.Video
import com.test.wisnufebri.util.BaseViewModel
import com.test.wisnufebri.util.extension.liveDataBlockScope

class TVShowDetailsViewModelFactory(private val tvShowId: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TVShowDetailsViewModel(tvShowId) as T
    }
}

class TVShowDetailsViewModel(tvShowId: Int) : BaseViewModel(), GoToVideo, GoToCast {

    private val tvShowRepository = TvRepository()
    val tvShow: LiveData<TvShowDetails>
    val videoList: LiveData<List<Video>>
    val castList: LiveData<List<Cast>>

    override val goToVideoEvent: MutableLiveData<Event<Video>> = MutableLiveData()
    override val goToCastDetailsEvent: MutableLiveData<Event<Cast>> = MutableLiveData()

    init {
        tvShow = liveDataBlockScope {
            tvShowRepository.loadDetails(tvShowId) { mSnackBarText.postValue(Event(it)) }
        }

        videoList = liveDataBlockScope {
            tvShowRepository.loadVideos(tvShowId) { mSnackBarText.postValue(Event(it)) }
        }

        castList = liveDataBlockScope {
            tvShowRepository.loadCredits(tvShowId) { mSnackBarText.postValue(Event(it)) }
        }
    }
}