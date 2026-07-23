package com.patflow.app.feature.savings;

import com.patflow.app.domain.usecase.savings.GetSavingsGoalAnalyticsUseCase;
import com.patflow.app.domain.usecase.savings.GetSavingsGoalsUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class SavingsGoalViewModel_Factory implements Factory<SavingsGoalViewModel> {
  private final Provider<GetSavingsGoalsUseCase> getSavingsGoalsUseCaseProvider;

  private final Provider<GetSavingsGoalAnalyticsUseCase> getSavingsGoalAnalyticsUseCaseProvider;

  public SavingsGoalViewModel_Factory(
      Provider<GetSavingsGoalsUseCase> getSavingsGoalsUseCaseProvider,
      Provider<GetSavingsGoalAnalyticsUseCase> getSavingsGoalAnalyticsUseCaseProvider) {
    this.getSavingsGoalsUseCaseProvider = getSavingsGoalsUseCaseProvider;
    this.getSavingsGoalAnalyticsUseCaseProvider = getSavingsGoalAnalyticsUseCaseProvider;
  }

  @Override
  public SavingsGoalViewModel get() {
    return newInstance(getSavingsGoalsUseCaseProvider.get(), getSavingsGoalAnalyticsUseCaseProvider.get());
  }

  public static SavingsGoalViewModel_Factory create(
      Provider<GetSavingsGoalsUseCase> getSavingsGoalsUseCaseProvider,
      Provider<GetSavingsGoalAnalyticsUseCase> getSavingsGoalAnalyticsUseCaseProvider) {
    return new SavingsGoalViewModel_Factory(getSavingsGoalsUseCaseProvider, getSavingsGoalAnalyticsUseCaseProvider);
  }

  public static SavingsGoalViewModel newInstance(GetSavingsGoalsUseCase getSavingsGoalsUseCase,
      GetSavingsGoalAnalyticsUseCase getSavingsGoalAnalyticsUseCase) {
    return new SavingsGoalViewModel(getSavingsGoalsUseCase, getSavingsGoalAnalyticsUseCase);
  }
}
