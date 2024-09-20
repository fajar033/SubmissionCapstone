package com.fajarsbkh.core.domain.usecase

import com.fajarsbkh.core.data.source.Resource
import com.fajarsbkh.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMovieNowPlaying(): Flow<Resource<List<Movie>>>
    suspend fun insert(movie: Movie)
    fun getAllFavorite(): Flow<List<Movie>>
    suspend fun delete(movie: Movie)
    fun getFavoriteState(id: Int): Flow<Boolean>
}