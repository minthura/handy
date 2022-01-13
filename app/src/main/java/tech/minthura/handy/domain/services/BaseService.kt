package tech.minthura.handy.domain.services

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import tech.minthura.handy.domain.models.Error
import tech.minthura.handy.domain.models.ErrorResponse
import tech.minthura.handy.domain.models.Errors
import tech.minthura.handy.domain.models.Result
import java.io.IOException
import javax.net.ssl.HttpsURLConnection

const val TAG = "Handy"

open class BaseService(private val dispatcher: CoroutineDispatcher) {

    suspend fun <T> coroutineLaunch(suspend: suspend () -> T) : Result<T> {
        val result = withContext(dispatcher) {
            try {
                val response = suspend()
                Result.success(response)
            } catch (e: Throwable) {
                handleApiError(e)
            }
        }
        return result
    }

    open fun <T> handleApiError(error: Throwable): Result<T> {
        if (error is HttpException) {
            return when (error.code()) {
                HttpsURLConnection.HTTP_UNAUTHORIZED -> Result.error(Error(Errors.UNAUTHORIZED, getErrorResponse(error)))
                HttpsURLConnection.HTTP_FORBIDDEN -> Result.error(Error(Errors.FORBIDDEN, getErrorResponse(error)))
                HttpsURLConnection.HTTP_INTERNAL_ERROR -> Result.error(Error(Errors.INTERNAL_SERVER_ERROR, getErrorResponse(error)))
                HttpsURLConnection.HTTP_BAD_REQUEST -> Result.error(Error(Errors.BAD_REQUEST, getErrorResponse(error)))
                else -> Result.error(Error(Errors.UNKNOWN, getErrorResponse(error)))
            }
        } else {
            error.printStackTrace()
            return if (error.message != null){
                Result.error(Error(Errors.UNKNOWN, ErrorResponse(error.message!!, 500)))
            } else {
                Result.error(Error(Errors.UNKNOWN, ErrorResponse("Unknown error", 500)))
            }
        }
    }

    private fun getErrorResponse(error : HttpException) : ErrorResponse? {
        error.response()?.let { it ->
            it.errorBody()?.let {
                return buildErrorResponse(it.string())
            }
        }
        return null
    }

    private fun buildErrorResponse(json : String) : ErrorResponse? {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter: JsonAdapter<ErrorResponse> = moshi.adapter(
            ErrorResponse::class.java
        )
        return try {
            jsonAdapter.fromJson(json)
        } catch (e : JsonDataException) {
            ErrorResponse("JsonDataException", 500)
        } catch (e : IOException) {
            ErrorResponse("IOException", 500)
        }

    }

}