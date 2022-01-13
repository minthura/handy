package tech.minthura.handy.domain

import tech.minthura.handy.domain.models.Result
import tech.minthura.handy.domain.models.User
import tech.minthura.handy.domain.services.UserService

class UserRepository(private val userService: UserService) {

    suspend fun getUsers() : Result<List<User>> {
        return userService.getUsers()
    }

}
