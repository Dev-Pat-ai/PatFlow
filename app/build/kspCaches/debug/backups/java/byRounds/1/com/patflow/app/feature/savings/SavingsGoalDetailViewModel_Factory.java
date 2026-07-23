package com.patflow.app.feature.savings;

import androidx.lifecycle.SavedStateHandle;
import com.patflow.app.domain.usecase.savings.AddSavingsContributionUseCase;
import com.patflow.app.domain.usecase.savings.DeleteSavingsGoalUseCase;
import com.patflow.app.domain.usecase.savings.GetSavingsGoalAnalyticsUseCase;
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
public final class SavingsGoalDetailViewModel_Factory implements Factory<SavingsGoalDetailViewModel> {
  private final Provider<GetSavingsGoalAnalyticsUseCase> getGoalAnalyticsUseCaseProvider;

  private final Provider<AddSavingsContributionUseCase> addContributionUseCaseProvider;

  private final Provider<DeleteSavingsGoalUseCase> deleteGoalUseCaseProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public SavingsGoalDetailViewModel_Factory(
      Provider<GetSavingsGoalAnalyticsUseCase> getGoalAnalyticsUseCaseProvider,
      Provider<AddSavingsContributionUseCase> addContributionUseCaseProvider,
      Provider<DeleteSavingsGoalUseCase> deleteGoalUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.getGoalAnalyticsUseCaseProvider = getGoalAnalyticsUseCaseProvider;
    this.addContributionUseCaseProvider = addContributionUseCaseProvider;
    this.deleteGoalUseCaseProvider = deleteGoalUseCaseProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public SavingsGoalDetailViewModel get() {
    return newInstance(getGoalAnalyticsUseCaseProvider.get(), addContributionUseCaseProvider.get(), deleteGoalUseCaseProvider.get(), savedStateHandleProvider.get());
  }

  public static SavingsGoalDetailViewModel_Factory create(
      Provider<GetSavingsGoalAnalyticsUseCase> getGoalAnalyticsUseCaseProvider,
      Provider<AddSavingsContributionUseCase> addContributionUseCaseProvider,
      Provider<DeleteSavingsGoalUseCase> deleteGoalUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new SavingsGoalDetailViewModel_Factory(getGoalAnalyticsUseCaseProvider, addContributionUseCaseProvider, deleteGoalUseCaseProvider, savedStateHandleProvider);
  }

  public static SavingsGoalDetailViewModel newInstance(
      GetSavingsGoalAnalyticsUseCase getGoalAnalyticsUseCase,
      AddSavingsContributionUseCase addContributionUseCase,
      DeleteSavingsGoalUseCase deleteGoalUseCase, SavedStateHandle savedStateHandle) {
    return new SavingsGoalDetailViewModel(getGoalAnalyticsUseCase, addContributionUseCase, deleteGoalUseCase, savedStateHandle);
  }
}
