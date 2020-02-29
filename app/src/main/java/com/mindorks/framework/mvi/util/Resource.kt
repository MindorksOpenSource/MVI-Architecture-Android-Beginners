package com.mindorks.framework.mvi.util

import com.mindorks.mvibeginner.util.Status

data class Resource<out T> private constructor(val status: Status, val data: T?) {

    companion object {
        fun <T> success(data: T? = null): Resource<T> = Resource(Status.SUCCESS, data)

        fun <T> error(data: T? = null): Resource<T> = Resource(Status.ERROR, data)

        fun <T> loading(data: T? = null): Resource<T> = Resource(Status.LOADING, data)

        fun <T> init(data: T? = null): Resource<T> = Resource(Status.INITIAL, data)
    }
}