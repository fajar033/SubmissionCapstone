package com.fajarsbkh.core.domain.usecase

import com.fajarsbkh.core.data.source.Resource
import com.fajarsbkh.core.domain.model.Movie
import com.fajarsbkh.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val imovieRepository:IMovieRepository): MovieUseCase {
    override fun getMovieNowPlaying(): Flow<Resource<List<Movie>>> =
        imovieRepository.getMovieNowPlaying()

    override suspend fun insert(movie: Movie) =
        imovieRepository.insertMovieToDB(movie)

    override fun getAllFavorite(): Flow<List<Movie>> =
        imovieRepository.getAllFavoriteMovieFromDB()

    override suspend fun delete(movie: Movie) =
        imovieRepository.deleteMovieFromDB(movie)

    override fun getFavoriteState(id: Int): Flow<Boolean> =
        imovieRepository.getFavoriteStateMovieFromDB(id)
}