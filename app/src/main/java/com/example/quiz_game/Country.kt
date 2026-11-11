package com.example.quiz_game
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

@JsonClass(generateAdapter = true)
data class Country(
    val name: Name,
    val flags: Flag
)

@JsonClass(generateAdapter = true)
data class Name(
    val common: String
)

@JsonClass(generateAdapter = true)
data class Flag(
    val png: String
)

interface ApiService {
    @GET("/v3.1/all?fields=name,flags")
    suspend fun getAllCountries(): List<Country>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("https://restcountries.com/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

object API {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

object PartitaCorrente {
    var downloaded: List<Country> = emptyList()
}
