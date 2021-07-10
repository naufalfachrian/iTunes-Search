package com.naufalfachrian.itunessearch.datasource.remote.entity

import com.google.gson.annotations.SerializedName

data class ListRemoteResponse<T>(

    @SerializedName("resultCount")
    val resultCount : Int,

    @SerializedName("results")
    val results : List<T>

)