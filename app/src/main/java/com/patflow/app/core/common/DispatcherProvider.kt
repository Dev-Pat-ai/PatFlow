package com.patflow.app.core.common

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Abstraction over coroutine dispatchers so ViewModels/use cases are
 * unit-testable without instrumentation (Architecture §2 NFR — Testability).
 * Bound to a real implementation via DispatcherModule (di/).
 */
interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}
