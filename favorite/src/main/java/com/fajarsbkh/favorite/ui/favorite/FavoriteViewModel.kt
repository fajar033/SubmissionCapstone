package com.fajarsbkh.favorite.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fajarsbkh.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {
    val getFavorite = movieUseCase.getAllFavorite().asLiveData()
}