package com.naufalfachrian.itunessearch.datasource.remote

import com.naufalfachrian.itunessearch.datasource.remote.entity.ListRemoteResponse
import com.naufalfachrian.itunessearch.datasource.remote.entity.MusicRemoteResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteDataSource {

    companion object {

        private const val BASE_REMOTE_URL = "https://itunes.apple.com/"

        private val okHttpConfig = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        private val retrofitConfig = Retrofit.Builder()
            .baseUrl(BASE_REMOTE_URL)
            .client(okHttpConfig)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val instance: RemoteDataSource = retrofitConfig.create(RemoteDataSource::class.java)

    }

    @GET("search")
    fun searchMusic(@Query("term") query: String, @Query("media") media: String = "music"): Call<ListRemoteResponse<MusicRemoteResponse>>

}