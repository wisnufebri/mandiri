package com.test.wisnufebri.ui.person_details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.test.wisnufebri.R
import com.test.wisnufebri.data.config.EventObserver
import com.test.wisnufebri.data.database.remote.TheMovieDatabaseAPI
import com.test.wisnufebri.databinding.FragmentPersonDetailsBinding
import com.test.wisnufebri.util.BaseFragment
import com.test.wisnufebri.util.extension.openUrl
import com.test.wisnufebri.util.extension.showSnackBar


class PersonDetailsFragment : BaseFragment(true) {

    private val args: PersonDetailsFragmentArgs by navArgs()
    private val viewModel: PersonDetailsViewModel by viewModels { PersonDetailsViewModelFactory(args.personIdArg) }
    private lateinit var viewDataBinding: FragmentPersonDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            FragmentPersonDetailsBinding.inflate(inflater, container, false)
                .apply {
                    viewmodel = viewModel
                    lifecycleOwner = this@PersonDetailsFragment.viewLifecycleOwner
                }
        return viewDataBinding.root
    }

    override fun setupViewModelObservers() {
        viewModel.snackbarText.observe(viewLifecycleOwner, EventObserver { view?.showSnackBar(it) })
        viewModel.goToImageEvent.observe(
            viewLifecycleOwner,
            EventObserver { openUrl(TheMovieDatabaseAPI.getProfileUrl(it.filePath)) })

        viewModel.goToCreditEvent.observe(viewLifecycleOwner, EventObserver {
            if (it.mediaType == "movie") {
                navigateToMovieDetails(it.id, it.title)
            } else if (it.mediaType == "tv") {
                navigateToTvShowDetails(it.id, it.title)
            }
        })
    }

    private fun navigateToMovieDetails(id: Int, title: String) {
        val action =
            PersonDetailsFragmentDirections.actionPersonDetailsFragmentToMovieDetailsFragment(
                id,
                title
            )
        findNavController().navigate(action)
    }

    private fun navigateToTvShowDetails(id: Int, title: String) {
        val action =
            PersonDetailsFragmentDirections.actionPersonDetailsFragmentToTvShowDetailsFragment(
                id,
                title
            )
        findNavController().navigate(action)
    }
}