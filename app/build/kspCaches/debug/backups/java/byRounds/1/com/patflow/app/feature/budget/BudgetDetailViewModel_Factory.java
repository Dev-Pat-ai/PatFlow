package com.patflow.app.feature.budget;

import androidx.lifecycle.SavedStateHandle;
import com.patflow.app.domain.usecase.budget.ArchiveBudgetUseCase;
import com.patflow.app.domain.usecase.budget.DeleteBudgetUseCase;
import com.patflow.app.domain.usecase.budget.GetBudgetAnalyticsUseCase;
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
public final class BudgetDetailViewModel_Factory implements Factory<BudgetDetailViewModel> {
  private final Provider<GetBudgetAnalyticsUseCase> getBudgetAnalyticsUseCaseProvider;

  private final Provider<DeleteBudgetUseCase> deleteBudgetUseCaseProvider;

  private final Provider<ArchiveBudgetUseCase> archiveBudgetUseCaseProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public BudgetDetailViewModel_Factory(
      Provider<GetBudgetAnalyticsUseCase> getBudgetAnalyticsUseCaseProvider,
      Provider<DeleteBudgetUseCase> deleteBudgetUseCaseProvider,
      Provider<ArchiveBudgetUseCase> archiveBudgetUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.getBudgetAnalyticsUseCaseProvider = getBudgetAnalyticsUseCaseProvider;
    this.deleteBudgetUseCaseProvider = deleteBudgetUseCaseProvider;
    this.archiveBudgetUseCaseProvider = archiveBudgetUseCaseProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public BudgetDetailViewModel get() {
    return newInstance(getBudgetAnalyticsUseCaseProvider.get(), deleteBudgetUseCaseProvider.get(), archiveBudgetUseCaseProvider.get(), savedStateHandleProvider.get());
  }

  public static BudgetDetailViewModel_Factory create(
      Provider<GetBudgetAnalyticsUseCase> getBudgetAnalyticsUseCaseProvider,
      Provider<DeleteBudgetUseCase> deleteBudgetUseCaseProvider,
      Provider<ArchiveBudgetUseCase> archiveBudgetUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new BudgetDetailViewModel_Factory(getBudgetAnalyticsUseCaseProvider, deleteBudgetUseCaseProvider, archiveBudgetUseCaseProvider, savedStateHandleProvider);
  }

  public static BudgetDetailViewModel newInstance(
      GetBudgetAnalyticsUseCase getBudgetAnalyticsUseCase, DeleteBudgetUseCase deleteBudgetUseCase,
      ArchiveBudgetUseCase archiveBudgetUseCase, SavedStateHandle savedStateHandle) {
    return new BudgetDetailViewModel(getBudgetAnalyticsUseCase, deleteBudgetUseCase, archiveBudgetUseCase, savedStateHandle);
  }
}
