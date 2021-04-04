package com.test.wisnufebri.ui.show_all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.test.wisnufebri.data.config.EventObserver
import com.test.wisnufebri.data.config.observe
import com.test.wisnufebri.data.model.Movie
import com.test.wisnufebri.databinding.FragmentShowAllBinding
import com.test.wisnufebri.util.BaseFragment
import com.test.wisnufebri.util.extension.showSnackBar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_show_all.*
import java.util.*

class ShowAllFragment : BaseFragment(true) {

    private lateinit var locale: Locale

    private val args: ShowAllFragmentArgs by navArgs()
    private val viewModel: ShowAllViewModel by viewModels { ShowAllViewModelFactory(args.movieListTypeArg) }
    private lateinit var viewDataBinding: FragmentShowAllBinding
    private val mAdapterSearchResult by lazy { GroupAdapter<ViewHolder>() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentShowAllBinding.inflate(inflater, container, false)
            .apply {
                viewmodel = viewModel
                lifecycleOwner = this@ShowAllFragment.viewLifecycleOwner
            }
        observer()
        return viewDataBinding.root
    }

    private fun observer() {
        observe(viewModel.movieList, ::initData)
    }

    private fun initData(list: List<Movie>?) {
        searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = true

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.isEmpty()) {
                        showAllRecyclerView.isGone
                    } else {
                        showAllRecyclerView.isVisible

                        val listMovie = list?.filter { param ->
                            val textSearch = newText.toLowerCase()
                            val value = param.title.toLowerCase()

                            value.contains(textSearch) || value.startsWith(textSearch)
                        }
                        if (listMovie != null) {
                            if (listMovie.isEmpty()) {
                                tvNoResult.isVisible
                            } else {
                                tvNoResult.isGone
                                showAllRecyclerView.isVisible
                            }
                        }
                    }
                }
                return true
            }
        })
    }

    override fun setupViewModelObservers() {
        viewModel.snackbarText.observe(viewLifecycleOwner, EventObserver { view?.showSnackBar(it) })
        viewModel.goToMovieDetailsEvent.observe(
            viewLifecycleOwner,
            EventObserver { navigateToMovieDetails(it.id, it.title) })
    }

    private fun navigateToMovieDetails(movieId: Int, movieTitle: String) {
        val action = ShowAllFragmentDirections.actionShowAllFragmentToMovieDetailsFragment(
            movieId,
            movieTitle
        )
        findNavController().navigate(action)
    }
}