package com.naufalfachrian.itunessearch

import com.naufalfachrian.itunessearch.datasource.remote.RemoteDataSource
import org.junit.Test

class RestApiUnitTest {

    @Test
    fun restApiUnitTest() {
        val query = "nanahira"
        val response = RemoteDataSource.instance.searchMusic(query)
            .execute()
        assert(response.isSuccessful)
        assert(response.code() == 200)
        assert(response.body() != null)
        assert(response.body()?.resultCount == response.body()?.results?.count())
    }

}