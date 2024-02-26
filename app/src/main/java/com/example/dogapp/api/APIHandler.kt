package com.example.dogapp.api

import android.util.Log
import com.example.dogapp.dogImageList
import com.example.dogapp.dogResponseList
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.moshi.MoshiConverterFactory
class APIHandler private constructor() {

    var breedCache: dogResponseList? = null

    private val api = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(DogAPI::class.java)

    suspend fun getAllBreeds() : dogResponseList? {

        if (breedCache != null) {
            return breedCache
        }

        val response = api.getAllBreeds().awaitResponse()
        if(response.isSuccessful){
            val data = response.body()
            if(data != null){
                Log.e("APIHandler", data.message.toString())
                breedCache = data
                return data
            }
        }
        return null
    }

    suspend fun getBreedImages(breed: String) : dogImageList? {
        val response = api.getBreedImages(breed).awaitResponse()
        Log.e("APIHandler", "getBreedImages")
        if(response.isSuccessful){
            val data = response.body()
            Log.e("APIHandler", data.toString())
            if(data != null){
                Log.e("APIHandler", data.message.toString())
                return data
            }
        }
        return null
    }

    suspend fun getSubBreedImages(breed: String, subBreed: String) : dogImageList? {
        val response = api.getSubBreedImages(breed, subBreed).awaitResponse()
        Log.e("APIHandler", "getSubBreedImages")
        if(response.isSuccessful){
            val data = response.body()
            Log.e("APIHandler", data.toString())
            if(data != null){
                Log.e("APIHandler", data.message.toString())
                return data
            }
        }
        return null
    }

    companion object {
        @Volatile
        private var INSTANCE: APIHandler? = null

        fun getInstance(): APIHandler {
            return INSTANCE ?: synchronized(this) {
                val instance = APIHandler()
                INSTANCE = instance
                instance
            }
        }
    }

}