package com.taher.footballdata.utilities

data class DataWrapper<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): DataWrapper<T> {
            return DataWrapper(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String?, data: T? = null): DataWrapper<T> {
            return DataWrapper(Status.ERROR, data, msg)
        }

        fun <T> none(): DataWrapper<T> {
            return DataWrapper(Status.ERROR, null, null)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR
    }
}