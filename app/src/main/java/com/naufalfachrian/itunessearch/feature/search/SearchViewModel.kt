package com.naufalfachrian.itunessearch.feature.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naufalfachrian.itunessearch.datasource.remote.RemoteDataSource
import com.naufalfachrian.itunessearch.datasource.remote.entity.ListRemoteResponse
import com.naufalfachrian.itunessearch.datasource.remote.entity.MusicRemoteResponse
import com.naufalfachrian.itunessearch.entity.Music
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    val query = MutableLiveData<String>()

    val musics = MutableLiveData<List<Music>>().apply { value = listOf() }

    val errorMessage = MutableLiveData<String>()

    fun searchMusic(searchQuery: String) {
        RemoteDataSource.instance.searchMusic(searchQuery)
            .enqueue(object : Callback<ListRemoteResponse<MusicRemoteResponse>> {
                override fun onResponse(call: Call<ListRemoteResponse<MusicRemoteResponse>>, response: Response<ListRemoteResponse<MusicRemoteResponse>>) {
                    if (call.request().url.queryParameter("term") == query.value) {
                        musics.value = response.body()?.results ?: listOf()
                    }
                }

                override fun onFailure(call: Call<ListRemoteResponse<MusicRemoteResponse>>, t: Throwable) {
                    errorMessage.value = t.localizedMessage
                }
            })
    }

}