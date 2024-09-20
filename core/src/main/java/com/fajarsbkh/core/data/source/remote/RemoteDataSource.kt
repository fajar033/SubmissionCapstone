package com.fajarsbkh.core.data.source.remote

import com.fajarsbkh.core.data.source.remote.network.ApiResponse
import com.fajarsbkh.core.data.source.remote.network.MovieService
import com.fajarsbkh.core.data.source.remote.response.GetMovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val movieService: MovieService) {
    suspend fun getMovieNowPlaying(): Flow<ApiResponse<GetMovieResponse>>{
        return flow {
            try {
                val response = movieService.getMovieNowPlaying()
                emit(ApiResponse.Success(response))
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}