package tech.minthura.handy.domain.services

import kotlinx.coroutines.CoroutineDispatcher
import tech.minthura.handy.domain.models.Result
import tech.minthura.handy.domain.models.User

class UserService(private val apiService: RetrofitApiService, dispatcher: CoroutineDispatcher) : BaseService(dispatcher) {
    suspend fun getUsers() : Result<List<User>> {
        return coroutineLaunch {
            apiService.getUsers()
        }
    }
}
