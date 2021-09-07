package com.cesoft.organizate3.domain

sealed class UseCaseResult<out R> {
    data class Success<out T>(val data: T) : UseCaseResult<T>()
    data class Error(val exception: Exception) : UseCaseResult<Nothing>()
    object Loading : UseCaseResult<Nothing>()
}

val <T> UseCaseResult<T>.data: T?
    get() = (this as? UseCaseResult.Success)?.data
