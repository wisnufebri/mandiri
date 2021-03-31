package com.test.wisnufebri.ui.tv_show_details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.test.wisnufebri.R
import com.test.wisnufebri.data.config.EventObserver
import com.test.wisnufebri.data.database.remote.TheMovieDatabaseAPI
import com.test.wisnufebri.databinding.FragmentTvShowDetailsBinding
import com.test.wisnufebri.util.BaseFragment
import com.test.wisnufebri.util.extension.openUrl
import com.test.wisnufebri.util.extension.showSnackBar

class TVShowDetailsFragment : BaseFragment(true) {

    private val args: TVShowDetailsFragmentArgs by navArgs()
    private val viewModel: TVShowDetailsViewModel by viewModels { TVShowDetailsViewModelFactory(args.tvShowIdArg) }
    private lateinit var viewDataBinding: FragmentTvShowDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            FragmentTvShowDetailsBinding.inflate(inflater, container, false)
                .apply {
                    viewmodel = viewModel
                    lifecycleOwner = this@TVShowDetailsFragment.viewLifecycleOwner
                }
        return viewDataBinding.root
    }

    override fun setupViewModelObservers() {
        viewModel.snackbarText.observe(viewLifecycleOwner, EventObserver { view?.showSnackBar(it) })
        viewModel.goToCastDetailsEvent.observe(
            viewLifecycleOwner,
            EventObserver { navigateToPersonDetails(it.id, it.name) })
        viewModel.goToVideoEvent.observe(
            viewLifecycleOwner,
            EventObserver { openUrl(TheMovieDatabaseAPI.getYoutubeWatchUrl(it.key)) })
    }

    private fun navigateToPersonDetails(personId: Int, personName: String) {
        val action =
            TVShowDetailsFragmentDirections.actionTvShowDetailsFragmentToPersonDetailsFragment(
                personId,
                personName
            )
        findNavController().navigate(action)
    }
}