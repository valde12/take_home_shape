package com.example.dogapp.api

import com.example.dogapp.models.dogImageList
import com.example.dogapp.models.dogResponseList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogAPI {
    @GET("breeds/list/all")
    fun getAllBreeds() : Call<dogResponseList>

    @GET("breed/{breed}/images")
    fun getBreedImages(
        @Path("breed") breed: String
    ) : Call<dogImageList>

    @GET("breed/{breed}/{subBreed}/images")
    fun getSubBreedImages(
        @Path("breed") breed: String,
        @Path("subBreed") subBreed: String
    ) : Call<dogImageList>
}