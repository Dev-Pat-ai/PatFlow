package com.patflow.app.domain.usecase.dashboard;

import com.patflow.app.domain.repository.BillRepository;
import com.patflow.app.domain.repository.BudgetRepository;
import com.patflow.app.domain.repository.IncomeRepository;
import com.patflow.app.domain.repository.PaymentRepository;
import com.patflow.app.domain.repository.SavingsGoalRepository;
import com.patflow.app.domain.usecase.budget.GetBudgetAnalyticsUseCase;
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
public final class GetDashboardDataUseCase_Factory implements Factory<GetDashboardDataUseCase> {
  private final Provider<BillRepository> billRepositoryProvider;

  private final Provider<PaymentRepository> paymentRepositoryProvider;

  private final Provider<IncomeRepository> incomeRepositoryProvider;

  private final Provider<BudgetRepository> budgetRepositoryProvider;

  private final Provider<SavingsGoalRepository> savingsRepositoryProvider;

  private final Provider<GetBudgetAnalyticsUseCase> getBudgetAnalyticsUseCaseProvider;

  private final Provider<GetSavingsGoalAnalyticsUseCase> getGoalAnalyticsUseCaseProvider;

  public GetDashboardDataUseCase_Factory(Provider<BillRepository> billRepositoryProvider,
      Provider<PaymentRepository> paymentRepositoryProvider,
      Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<SavingsGoalRepository> savingsRepositoryProvider,
      Provider<GetBudgetAnalyticsUseCase> getBudgetAnalyticsUseCaseProvider,
      Provider<GetSavingsGoalAnalyticsUseCase> getGoalAnalyticsUseCaseProvider) {
    this.billRepositoryProvider = billRepositoryProvider;
    this.paymentRepositoryProvider = paymentRepositoryProvider;
    this.incomeRepositoryProvider = incomeRepositoryProvider;
    this.budgetRepositoryProvider = budgetRepositoryProvider;
    this.savingsRepositoryProvider = savingsRepositoryProvider;
    this.getBudgetAnalyticsUseCaseProvider = getBudgetAnalyticsUseCaseProvider;
    this.getGoalAnalyticsUseCaseProvider = getGoalAnalyticsUseCaseProvider;
  }

  @Override
  public GetDashboardDataUseCase get() {
    return newInstance(billRepositoryProvider.get(), paymentRepositoryProvider.get(), incomeRepositoryProvider.get(), budgetRepositoryProvider.get(), savingsRepositoryProvider.get(), getBudgetAnalyticsUseCaseProvider.get(), getGoalAnalyticsUseCaseProvider.get());
  }

  public static GetDashboardDataUseCase_Factory create(
      Provider<BillRepository> billRepositoryProvider,
      Provider<PaymentRepository> paymentRepositoryProvider,
      Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<SavingsGoalRepository> savingsRepositoryProvider,
      Provider<GetBudgetAnalyticsUseCase> getBudgetAnalyticsUseCaseProvider,
      Provider<GetSavingsGoalAnalyticsUseCase> getGoalAnalyticsUseCaseProvider) {
    return new GetDashboardDataUseCase_Factory(billRepositoryProvider, paymentRepositoryProvider, incomeRepositoryProvider, budgetRepositoryProvider, savingsRepositoryProvider, getBudgetAnalyticsUseCaseProvider, getGoalAnalyticsUseCaseProvider);
  }

  public static GetDashboardDataUseCase newInstance(BillRepository billRepository,
      PaymentRepository paymentRepository, IncomeRepository incomeRepository,
      BudgetRepository budgetRepository, SavingsGoalRepository savingsRepository,
      GetBudgetAnalyticsUseCase getBudgetAnalyticsUseCase,
      GetSavingsGoalAnalyticsUseCase getGoalAnalyticsUseCase) {
    return new GetDashboardDataUseCase(billRepository, paymentRepository, incomeRepository, budgetRepository, savingsRepository, getBudgetAnalyticsUseCase, getGoalAnalyticsUseCase);
  }
}
