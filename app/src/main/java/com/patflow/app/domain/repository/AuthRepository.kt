package com.patflow.app.domain.repository

import com.patflow.app.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

/**
 * Abstraction for authentication services (v2.0 Foundation).
 */
interface AuthRepository {
    val currentUser: Flow<UserProfile?>
    val isAuthenticated: Flow<Boolean>
    
    suspend fun signInWithGoogle(): Result<Unit>
    suspend fun signInAnonymously(): Result<Unit>
    suspend fun signOut(): Result<Unit>
}
