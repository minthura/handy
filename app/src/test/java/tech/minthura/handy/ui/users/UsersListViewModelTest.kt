package tech.minthura.handy.ui.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner
import tech.minthura.handy.TestCoroutineRule
import tech.minthura.handy.di.modules.testModule
import tech.minthura.handy.domain.UserRepository
import tech.minthura.handy.domain.models.Result
import tech.minthura.handy.domain.models.User
import tech.minthura.handy.domain.services.UserService
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UsersListViewModelTest : KoinTest {

    val usersListViewModel by inject<UsersListViewModel>()
    val userRepository by inject<UserRepository>()
    val userService by inject<UserService>()

    @Mock
    private lateinit var apiUsersObserver: Observer<Result<List<User>>>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            modules(
                listOf(testModule)
            )
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun testUserListViewModelGetUsers() {
        usersListViewModel.getUsers().observeForever(apiUsersObserver)
        Mockito.verify(apiUsersObserver).onChanged(ArgumentMatchers.any())
        usersListViewModel.getUsers().removeObserver(apiUsersObserver)
    }

    @Test
    fun testUserRepositoryGetUsers() : Unit = runBlocking {
        val users = userRepository.getUsers()
        assertNotNull(users.data)
        assertEquals(users.status, Result.Status.SUCCESS)
    }

    @Test
    fun testUserServiceGetUsers() : Unit = runBlocking {
        val users = userService.getUsers()
        assertNotNull(users.data)
        assertEquals(users.status, Result.Status.SUCCESS)
    }

}