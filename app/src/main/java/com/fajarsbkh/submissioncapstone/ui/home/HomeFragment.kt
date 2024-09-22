package com.fajarsbkh.submissioncapstone.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajarsbkh.core.data.source.Resource
import com.fajarsbkh.core.ui.MovieAdapter
import com.fajarsbkh.submissioncapstone.R
import com.fajarsbkh.submissioncapstone.databinding.FragmentHomeBinding
import com.fajarsbkh.submissioncapstone.ui.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupUi()
    }

    private fun setupUi() {
        movieAdapter = MovieAdapter {
            findNavController().navigate(
                R.id.action_homeFragment_to_detailFragment,
                Bundle().apply { putParcelable(DetailFragment.MOVIE, it) })
        }
        binding.rvMovie.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun observeData() {
        viewModel.getMovieNow.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    binding.apply {
                        rvMovie.visibility = View.GONE
                        pbHome.visibility = View.VISIBLE
                    }
                }
                is Resource.Success -> {
                    binding.apply {
                        rvMovie.visibility = View.VISIBLE
                        pbHome.visibility = View.GONE
                    }
                    movieAdapter.differ.submitList(it.data)
                }
                is Resource.Error -> {
                    binding.apply {
                        rvMovie.visibility = View.GONE
                        pbHome.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.rvMovie.adapter = null
        super.onDestroyView()
        _binding = null
    }
}