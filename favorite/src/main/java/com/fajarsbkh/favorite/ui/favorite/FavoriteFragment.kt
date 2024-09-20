package com.fajarsbkh.favorite.ui.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fajarsbkh.core.domain.model.Movie
import com.fajarsbkh.core.ui.FavoriteAdapter
import com.fajarsbkh.core.ui.MovieAdapter
import com.fajarsbkh.favorite.R
import com.fajarsbkh.favorite.databinding.FragmentFavoriteBinding
import com.fajarsbkh.favorite.di.DaggerFavoriteComponent
import com.fajarsbkh.submissioncapstone.di.FavoriteModule
import com.fajarsbkh.submissioncapstone.ui.detail.DetailFragment
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteAdapter: FavoriteAdapter

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: FavoriteViewModel by viewModels{
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    FavoriteModule::class.java
                )
            ).build().inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backOnPressed()
        setupUI()
    }

    private fun setupUI() {
        favoriteAdapter = FavoriteAdapter {
            findNavController().navigate(
                com.fajarsbkh.submissioncapstone.R.id.action_favoriteFragment_to_detailFragment,
                Bundle().apply {
                    putParcelable(DetailFragment.MOVIE,it)
                }
            )
        }
        binding.rvFav.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
        viewModel.getFavorite.observe(viewLifecycleOwner){
            favoriteAdapter.differ.submitList(it)
            if (it.isNotEmpty()){
                binding.rvFav.visibility = View.VISIBLE
            }else{
                binding.rvFav.visibility = View.GONE
            }
        }
    }

    private fun backOnPressed() {
        binding.icBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}