package com.ampersand.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ampersand.presentation.databinding.FragmentMainBinding
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

        val mainViewModel: MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        _binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.mainViewModel = mainViewModel

        val adapter = AsteroidAdapter(AsteroidListener { asteroidId ->
            mainViewModel.onAsteroidClicked(asteroidId)
        })


        binding.asteroidRecycler.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.loading.collect {
                    binding.statusLoadingWheel.visibility = if (it) View.VISIBLE else View.GONE
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
}