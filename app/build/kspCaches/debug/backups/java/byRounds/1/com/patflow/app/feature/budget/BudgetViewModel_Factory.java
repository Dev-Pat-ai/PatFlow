package com.patflow.app.feature.budget;

import com.patflow.app.domain.usecase.budget.GetBudgetAnalyticsUseCase;
import com.patflow.app.domain.usecase.budget.GetBudgetsUseCase;
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
public final class BudgetViewModel_Factory implements Factory<BudgetViewModel> {
  private final Provider<GetBudgetsUseCase> getBudgetsUseCaseProvider;

  private final Provider<GetBudgetAnalyticsUseCase> getBudgetAnalyticsUseCaseProvider;

  public BudgetViewModel_Factory(Provider<GetBudgetsUseCase> getBudgetsUseCaseProvider,
      Provider<GetBudgetAnalyticsUseCase> getBudgetAnalyticsUseCaseProvider) {
    this.getBudgetsUseCaseProvider = getBudgetsUseCaseProvider;
    this.getBudgetAnalyticsUseCaseProvider = getBudgetAnalyticsUseCaseProvider;
  }

  @Override
  public BudgetViewModel get() {
    return newInstance(getBudgetsUseCaseProvider.get(), getBudgetAnalyticsUseCaseProvider.get());
  }

  public static BudgetViewModel_Factory create(
      Provider<GetBudgetsUseCase> getBudgetsUseCaseProvider,
      Provider<GetBudgetAnalyticsUseCase> getBudgetAnalyticsUseCaseProvider) {
    return new BudgetViewModel_Factory(getBudgetsUseCaseProvider, getBudgetAnalyticsUseCaseProvider);
  }

  public static BudgetViewModel newInstance(GetBudgetsUseCase getBudgetsUseCase,
      GetBudgetAnalyticsUseCase getBudgetAnalyticsUseCase) {
    return new BudgetViewModel(getBudgetsUseCase, getBudgetAnalyticsUseCase);
  }
}
