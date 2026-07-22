package com.patflow.app.core.common

/**
 * Base contract for a suspending, single-shot use case. Feature-specific use
 * cases implement this once the domain layer is built.
 */
interface BaseUseCase<in Params, out T> {
    suspend operator fun invoke(params: Params): T
}
