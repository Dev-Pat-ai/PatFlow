package com.patflow.app.domain.usecase.dashboard;

import com.patflow.app.domain.repository.BillRepository;
import com.patflow.app.domain.repository.IncomeRepository;
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
public final class GetDashboardDataUseCase_Factory implements Factory<GetDashboardDataUseCase> {
  private final Provider<BillRepository> billRepositoryProvider;

  private final Provider<PaymentRepository> paymentRepositoryProvider;

  private final Provider<IncomeRepository> incomeRepositoryProvider;

  public GetDashboardDataUseCase_Factory(Provider<BillRepository> billRepositoryProvider,
      Provider<PaymentRepository> paymentRepositoryProvider,
      Provider<IncomeRepository> incomeRepositoryProvider) {
    this.billRepositoryProvider = billRepositoryProvider;
    this.paymentRepositoryProvider = paymentRepositoryProvider;
    this.incomeRepositoryProvider = incomeRepositoryProvider;
  }

  @Override
  public GetDashboardDataUseCase get() {
    return newInstance(billRepositoryProvider.get(), paymentRepositoryProvider.get(), incomeRepositoryProvider.get());
  }

  public static GetDashboardDataUseCase_Factory create(
      Provider<BillRepository> billRepositoryProvider,
      Provider<PaymentRepository> paymentRepositoryProvider,
      Provider<IncomeRepository> incomeRepositoryProvider) {
    return new GetDashboardDataUseCase_Factory(billRepositoryProvider, paymentRepositoryProvider, incomeRepositoryProvider);
  }

  public static GetDashboardDataUseCase newInstance(BillRepository billRepository,
      PaymentRepository paymentRepository, IncomeRepository incomeRepository) {
    return new GetDashboardDataUseCase(billRepository, paymentRepository, incomeRepository);
  }
}
