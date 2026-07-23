package com.patflow.app.feature.calendar;

import com.patflow.app.domain.repository.BillRepository;
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
public final class CalendarViewModel_Factory implements Factory<CalendarViewModel> {
  private final Provider<BillRepository> billRepositoryProvider;

  private final Provider<PaymentRepository> paymentRepositoryProvider;

  private final Provider<IncomeRepository> incomeRepositoryProvider;

  private final Provider<SavingsGoalRepository> savingsRepositoryProvider;

  public CalendarViewModel_Factory(Provider<BillRepository> billRepositoryProvider,
      Provider<PaymentRepository> paymentRepositoryProvider,
      Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<SavingsGoalRepository> savingsRepositoryProvider) {
    this.billRepositoryProvider = billRepositoryProvider;
    this.paymentRepositoryProvider = paymentRepositoryProvider;
    this.incomeRepositoryProvider = incomeRepositoryProvider;
    this.savingsRepositoryProvider = savingsRepositoryProvider;
  }

  @Override
  public CalendarViewModel get() {
    return newInstance(billRepositoryProvider.get(), paymentRepositoryProvider.get(), incomeRepositoryProvider.get(), savingsRepositoryProvider.get());
  }

  public static CalendarViewModel_Factory create(Provider<BillRepository> billRepositoryProvider,
      Provider<PaymentRepository> paymentRepositoryProvider,
      Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<SavingsGoalRepository> savingsRepositoryProvider) {
    return new CalendarViewModel_Factory(billRepositoryProvider, paymentRepositoryProvider, incomeRepositoryProvider, savingsRepositoryProvider);
  }

  public static CalendarViewModel newInstance(BillRepository billRepository,
      PaymentRepository paymentRepository, IncomeRepository incomeRepository,
      SavingsGoalRepository savingsRepository) {
    return new CalendarViewModel(billRepository, paymentRepository, incomeRepository, savingsRepository);
  }
}
