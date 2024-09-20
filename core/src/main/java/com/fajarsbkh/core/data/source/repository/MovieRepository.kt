package com.fajarsbkh.core.data.source.repository

import com.fajarsbkh.core.data.source.NetworkBoundResource
import com.fajarsbkh.core.data.source.Resource
import com.fajarsbkh.core.data.source.local.LocalDataSource
import com.fajarsbkh.core.data.source.remote.RemoteDataSource
import com.fajarsbkh.core.data.source.remote.network.ApiResponse
import com.fajarsbkh.core.data.source.remote.response.GetMovieResponse
import com.fajarsbkh.core.domain.model.Movie
import com.fajarsbkh.core.domain.repository.IMovieRepository
import com.fajarsbkh.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): IMovieRepository {
    override fun getMovieNowPlaying(): Flow<Resource<List<Movie>>> =
        object: NetworkBoundResource<List<Movie>, GetMovieResponse>(){
            override suspend fun createCall(): Flow<ApiResponse<GetMovieResponse>> =
                remoteDataSource.getMovieNowPlaying()

            override fun loadFromNetwork(data: GetMovieResponse): Flow<List<Movie>> =
                DataMapper.mapListResponseToDomain(data.results)
        }.asFlow()

    override suspend fun insertMovieToDB(movie: Movie) =
        localDataSource.insert(DataMapper.mapDomainToEntity(movie))

    override fun getAllFavoriteMovieFromDB(): Flow<List<Movie>> =
        localDataSource.getAllFavorite().map {
            DataMapper.mapListEntityToDomain(it)
        }

    override suspend fun deleteMovieFromDB(movie: Movie) =
        localDataSource.delete(DataMapper.mapDomainToEntity(movie))

    override fun getFavoriteStateMovieFromDB(id: Int): Flow<Boolean> =
        localDataSource.getFavoriteState(id)
}