package tech.minthura.handy.domain.services

import retrofit2.http.GET
import tech.minthura.handy.domain.models.User

interface RetrofitApiService {
    @GET("users")
    suspend fun getUsers() : List<User>
}