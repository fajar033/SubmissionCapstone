package com.fajarsbkh.core.data.source.remote.network

import com.fajarsbkh.core.data.source.remote.response.GetMovieResponse
import retrofit2.http.GET

interface MovieService {

    @GET("now_playing?api_key=$api_key")
    suspend fun getMovieNowPlaying(): GetMovieResponse

    companion object {
        private const val api_key = "050f07f04692401169a01ac8b2bf7a8c"
    }
}