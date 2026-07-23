package com.patflow.app.domain.usecase.insights;

import com.patflow.app.domain.repository.BillRepository;
import com.patflow.app.domain.repository.BudgetRepository;
import com.patflow.app.domain.repository.IncomeRepository;
import com.patflow.app.domain.repository.PaymentRepository;
import com.patflow.app.domain.repository.SavingsGoalRepository;
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
public final class GetSmartInsightsUseCase_Factory implements Factory<GetSmartInsightsUseCase> {
  private final Provider<BillRepository> billRepositoryProvider;

  private final Provider<PaymentRepository> paymentRepositoryProvider;

  private final Provider<IncomeRepository> incomeRepositoryProvider;

  private final Provider<BudgetRepository> budgetRepositoryProvider;

  private final Provider<SavingsGoalRepository> savingsRepositoryProvider;

  public GetSmartInsightsUseCase_Factory(Provider<BillRepository> billRepositoryProvider,
      Provider<PaymentRepository> paymentRepositoryProvider,
      Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<SavingsGoalRepository> savingsRepositoryProvider) {
    this.billRepositoryProvider = billRepositoryProvider;
    this.paymentRepositoryProvider = paymentRepositoryProvider;
    this.incomeRepositoryProvider = incomeRepositoryProvider;
    this.budgetRepositoryProvider = budgetRepositoryProvider;
    this.savingsRepositoryProvider = savingsRepositoryProvider;
  }

  @Override
  public GetSmartInsightsUseCase get() {
    return newInstance(billRepositoryProvider.get(), paymentRepositoryProvider.get(), incomeRepositoryProvider.get(), budgetRepositoryProvider.get(), savingsRepositoryProvider.get());
  }

  public static GetSmartInsightsUseCase_Factory create(
      Provider<BillRepository> billRepositoryProvider,
      Provider<PaymentRepository> paymentRepositoryProvider,
      Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<SavingsGoalRepository> savingsRepositoryProvider) {
    return new GetSmartInsightsUseCase_Factory(billRepositoryProvider, paymentRepositoryProvider, incomeRepositoryProvider, budgetRepositoryProvider, savingsRepositoryProvider);
  }

  public static GetSmartInsightsUseCase newInstance(BillRepository billRepository,
      PaymentRepository paymentRepository, IncomeRepository incomeRepository,
      BudgetRepository budgetRepository, SavingsGoalRepository savingsRepository) {
    return new GetSmartInsightsUseCase(billRepository, paymentRepository, incomeRepository, budgetRepository, savingsRepository);
  }
}
