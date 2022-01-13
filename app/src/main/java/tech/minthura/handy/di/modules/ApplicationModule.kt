package tech.minthura.handy.di.modules

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import tech.minthura.handy.R
import tech.minthura.handy.domain.UserRepository
import tech.minthura.handy.domain.services.RetrofitApiService
import tech.minthura.handy.domain.services.UserService
import tech.minthura.handy.ui.users.UsersListViewModel

val appModule = module {
    single { provideApiService(androidContext()) }
    single { provideDispatcher() }
    single { UserService(get(), get()) }
    single { UserRepository(get()) }
    viewModel { UsersListViewModel(get()) }
}

val testModule = module {
    single { provideApiService() }
    single { provideDispatcher() }
    single { UserService(get(), get()) }
    single { UserRepository(get()) }
    viewModel { UsersListViewModel(get()) }
}

fun provideApiService() : RetrofitApiService {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
    val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    return Retrofit.Builder()
        .baseUrl("http://192.168.100.6:8080/api/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(RetrofitApiService::class.java)
}

fun provideApiService(context: Context) : RetrofitApiService {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
    val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    return Retrofit.Builder()
        .baseUrl(context.getString(R.string.api_base_url))
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(RetrofitApiService::class.java)
}

fun provideDispatcher() : CoroutineDispatcher {
    return Dispatchers.IO
}
