package com.test.wisnufebri.ui.show_all

import androidx.lifecycle.*
import com.test.wisnufebri.data.config.Event
import com.test.wisnufebri.data.config.GoToMovie
import com.test.wisnufebri.data.config.MovieListType
import com.test.wisnufebri.data.database.repository.MovieRepository
import com.test.wisnufebri.data.model.Movie
import com.test.wisnufebri.util.BaseViewModel
import com.test.wisnufebri.util.extension.appendList
import com.test.wisnufebri.util.extension.liveDataBlockScope

class ShowAllViewModelFactory(private val movieListType: MovieListType) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShowAllViewModel(movieListType) as T
    }
}

class ShowAllViewModel (movieListType: MovieListType) : BaseViewModel(), GoToMovie {

    private val movieRepository = MovieRepository()
    private val moviePage = MutableLiveData<Int>().apply { value = 1 }
    private val loadedMovieList: LiveData<List<Movie>>
    val movieList = MediatorLiveData<MutableList<Movie>>()

    override val goToMovieDetailsEvent: MutableLiveData<Event<Movie>> = MutableLiveData()


    init {
        loadedMovieList = when (movieListType) {
            MovieListType.POPULAR -> {
                moviePage.switchMap {
                    liveDataBlockScope {
                        movieRepository.loadPopularList(it) { mSnackBarText.postValue(Event(it)) }
                    }
                }
            }
            MovieListType.IN_THEATERS -> {
                moviePage.switchMap {
                    liveDataBlockScope {
                        movieRepository.loadInTheatersList(it) { mSnackBarText.postValue(Event(it)) }
                    }
                }
            }
            else -> {
                moviePage.switchMap {
                    liveDataBlockScope {
                        movieRepository.loadUpcomingList(it) { mSnackBarText.postValue(Event(it)) }
                    }
                }
            }
        }

        movieList.addSource(loadedMovieList) { it?.let { list -> movieList.appendList(list) } }
    }

    fun loadMoreMovies() {
        moviePage.value = moviePage.value?.plus(1)
    }
}