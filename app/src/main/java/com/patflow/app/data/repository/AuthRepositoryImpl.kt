package com.patflow.app.data.repository

import com.patflow.app.domain.model.UserProfile
import com.patflow.app.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    override val currentUser: Flow<UserProfile?> = flowOf(null)
    override val isAuthenticated: Flow<Boolean> = flowOf(false)

    override suspend fun signInWithGoogle(): Result<Unit> = Result.success(Unit)
    override suspend fun signInAnonymously(): Result<Unit> = Result.success(Unit)
    override suspend fun signOut(): Result<Unit> = Result.success(Unit)
}
