package com.cesoft.organizate3.domain.usecase

import com.cesoft.organizate3.domain.UseCaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class SuspendUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    /** Executes the use case asynchronously and returns a [Result].
     *
     * @return a [Result].
     *
     * @param parameters the input parameters to run the use case with
     */
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

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}
