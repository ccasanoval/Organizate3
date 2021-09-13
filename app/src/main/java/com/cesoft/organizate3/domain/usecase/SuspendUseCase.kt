package com.cesoft.organizate3.domain.usecase

import com.cesoft.organizate3.domain.UseCaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class SuspendUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: P): UseCaseResult<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters).let {
                    UseCaseResult.Success(it)
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("SuspendUseCase", e.toString())
            UseCaseResult.Error(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}
