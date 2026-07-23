package com.patflow.app.domain.usecase.savings

import com.patflow.app.domain.model.SavingsContribution
import com.patflow.app.domain.repository.SavingsGoalRepository
import javax.inject.Inject

/**
 * Use case for logging a contribution toward a goal (Architecture §1.13).
 */
class AddSavingsContributionUseCase @Inject constructor(
    private val repository: SavingsGoalRepository
) {
    suspend operator fun invoke(contribution: SavingsContribution): Long = 
        repository.insertContribution(contribution)
}
