package com.patflow.app.domain.usecase.budget;

import com.patflow.app.domain.repository.BillRepository;
import com.patflow.app.domain.repository.BudgetRepository;
import com.patflow.app.domain.repository.PaymentRepository;
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
public final class GetBudgetAnalyticsUseCase_Factory implements Factory<GetBudgetAnalyticsUseCase> {
  private final Provider<BudgetRepository> budgetRepositoryProvider;

  private final Provider<PaymentRepository> paymentRepositoryProvider;

  private final Provider<BillRepository> billRepositoryProvider;

  public GetBudgetAnalyticsUseCase_Factory(Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<PaymentRepository> paymentRepositoryProvider,
      Provider<BillRepository> billRepositoryProvider) {
    this.budgetRepositoryProvider = budgetRepositoryProvider;
    this.paymentRepositoryProvider = paymentRepositoryProvider;
    this.billRepositoryProvider = billRepositoryProvider;
  }

  @Override
  public GetBudgetAnalyticsUseCase get() {
    return newInstance(budgetRepositoryProvider.get(), paymentRepositoryProvider.get(), billRepositoryProvider.get());
  }

  public static GetBudgetAnalyticsUseCase_Factory create(
      Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<PaymentRepository> paymentRepositoryProvider,
      Provider<BillRepository> billRepositoryProvider) {
    return new GetBudgetAnalyticsUseCase_Factory(budgetRepositoryProvider, paymentRepositoryProvider, billRepositoryProvider);
  }

  public static GetBudgetAnalyticsUseCase newInstance(BudgetRepository budgetRepository,
      PaymentRepository paymentRepository, BillRepository billRepository) {
    return new GetBudgetAnalyticsUseCase(budgetRepository, paymentRepository, billRepository);
  }
}
