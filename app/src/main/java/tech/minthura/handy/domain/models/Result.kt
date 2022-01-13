package tech.minthura.handy.domain.models

class Result<out T>(val status: Status, val data: T?, val error: Error?) {

    enum class Status {
        SUCCESS,
        ERROR,
        FETCHING,
    }

    companion object {
        fun <T> success(data : T) = Result(Status.SUCCESS, data, null)
        fun error(error : Error) = Result(Status.ERROR, null, error)
        fun fetching() = Result(Status.FETCHING, null, null)
    }

}
