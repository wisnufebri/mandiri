package com.test.wisnufebri.ui.movie

import androidx.lifecycle.*
import com.test.wisnufebri.data.config.Event
import com.test.wisnufebri.data.config.GoToMovie
import com.test.wisnufebri.data.database.repository.MovieRepository
import com.test.wisnufebri.data.model.Movie
import com.test.wisnufebri.util.BaseViewModel
import com.test.wisnufebri.util.extension.appendList
import com.test.wisnufebri.util.extension.liveDataBlockScope

class MoviesViewModel : BaseViewModel(), GoToMovie {

    private val movieRepository = MovieRepository()
    private val loadedMovies: LiveData<List<Movie>>
    private val moviesPage = MutableLiveData<Int>().apply { value = 1 }

    override val goToMovieDetailsEvent: MutableLiveData<Event<Movie>> = MutableLiveData()

    val movieList = MediatorLiveData<MutableList<Movie>>()

    init {
        loadedMovies = moviesPage.switchMap {
            liveDataBlockScope {
                movieRepository.loadDiscoverList(it) { mSnackBarText.postValue(Event(it)) }
            }
        }

        movieList.addSource(loadedMovies) { it?.let { list -> movieList.appendList(list) } }
    }

    fun loadMoreMovies() {
        moviesPage.value = moviesPage.value?.plus(1)
    }
}