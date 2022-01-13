package tech.minthura.handy.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import tech.minthura.handy.domain.UserRepository
import tech.minthura.handy.domain.models.Result

class UsersListViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getUsers() = liveData {
        emit(Result.fetching())
        emit(userRepository.getUsers())
    }

}
