package com.fajarsbkh.submissioncapstone.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.fajarsbkh.core.domain.model.Movie
import com.fajarsbkh.submissioncapstone.R
import com.fajarsbkh.submissioncapstone.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var detailMovie: Movie
    private var isFavorite by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getParcelable<Movie>(MOVIE)
        if (movie != null){
            detailMovie = movie
        }

        binding.apply {
            Glide.with(requireContext()).load(movie?.image).into(ivBackdrop)
            Glide.with(requireContext()).load(movie?.movieImage).into(ivDetailMovie)
            tvNameMovieDetail.text = movie?.title
            tvAboutContent.text = movie?.overview
            tvYear.text = movie?.releaseDate
            tvVote.text = movie?.voteCount.toString()
            icBack.setOnClickListener {
                it.findNavController().popBackStack()
            }
        }
        movie?.id?.let {
            viewModel.getFavoriteState(it).observe(viewLifecycleOwner){favorite ->
                binding.btnFavorite.apply {
                    if (favorite == true){
                        setImageResource(R.drawable.baseline_bookmark_24)
                    }else{
                        setImageResource(R.drawable.baseline_bookmark_border_24)
                    }
                    isFavorite = favorite
                }

            }
        }
        favoriteOnClick()
    }

    private fun favoriteOnClick() {
        binding.btnFavorite.apply {
            setOnClickListener {
                if (!isFavorite){
                    viewModel.insertFavorite(detailMovie)
                    setImageResource(R.drawable.baseline_bookmark_24)
                    isFavorite = true
                }else{
                    viewModel.deleteFavorite(detailMovie)
                    setImageResource(R.drawable.baseline_bookmark_border_24)
                    isFavorite = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object{
        const val MOVIE = "MOVIE"
    }
}