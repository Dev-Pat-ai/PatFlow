package com.patflow.app.domain.usecase.settings

import com.patflow.app.domain.model.UserPreferences
import com.patflow.app.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for observing current user preferences and profile state.
 */
class GetUserSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<UserPreferences> = repository.getUserPreferences()
}
