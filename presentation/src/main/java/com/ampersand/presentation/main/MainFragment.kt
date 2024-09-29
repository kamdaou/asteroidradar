package com.ampersand.presentation.main

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.ampersand.presentation.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val mainViewModel: MainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        _binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.mainViewModel = mainViewModel

        val adapter = AsteroidAdapter(AsteroidListener { asteroidId ->
            mainViewModel.onAsteroidClicked(asteroidId, isTablet = isTablet(this.context))
        })


        binding.asteroidRecycler.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.uiState.collect {
                    binding.statusLoadingWheel.visibility = if (it.loading) View.VISIBLE else View.GONE
                    if (it.navigateToAsteroidDetail != null) {
                        this@MainFragment.findNavController().navigate(
                            MainFragmentDirections.actionShowDetail(
                                it.navigateToAsteroidDetail
                            )
                        )
//                        tell the fragment that navigation was done
                        mainViewModel.onAsteroidDetailNavigated()
                    }

                    if (it.error != null) {
                        Snackbar.make(
                            this@MainFragment.requireView(),
                            it.error.asString(this@MainFragment.requireContext()),
                            Snackbar.LENGTH_LONG
                        ).show()
                        mainViewModel.onErrorShown()
                    }
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.asteroids.collect { asteroids ->
                    asteroids.apply {
                        adapter.submitList(asteroids)
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun isTablet(context: Context?): Boolean {
        if (context == null) return false
        return (context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }
}