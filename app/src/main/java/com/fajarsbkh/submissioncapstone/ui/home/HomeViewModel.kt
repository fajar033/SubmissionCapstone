package com.fajarsbkh.submissioncapstone.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fajarsbkh.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel(){
    val getMovieNow = movieUseCase.getMovieNowPlaying().asLiveData()
}