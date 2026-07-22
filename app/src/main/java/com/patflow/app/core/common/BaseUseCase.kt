package com.patflow.app.core.common

/**
 * Base contract for a suspending, single-shot use case. Feature-specific use
 * cases (domain/usecase/**) implement this once the domain layer is built in
 * the Bills feature phase — kept minimal here as foundation only.
 */
interface BaseUseCase<in Params, out T> {
    suspend operator fun invoke(params: Params): T
}
