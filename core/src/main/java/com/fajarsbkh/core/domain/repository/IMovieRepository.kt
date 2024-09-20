package com.fajarsbkh.core.domain.repository

import com.fajarsbkh.core.data.source.Resource
import com.fajarsbkh.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovieNowPlaying(): Flow<Resource<List<Movie>>>
    suspend fun insertMovieToDB(movie:Movie)
    fun getAllFavoriteMovieFromDB(): Flow<List<Movie>>
    suspend fun deleteMovieFromDB(movie:Movie)
    fun getFavoriteStateMovieFromDB(id:Int): Flow<Boolean>
}