package com.patflow.app.domain.usecase.report;

import com.patflow.app.domain.repository.BillRepository;
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
public final class GetReportDataUseCase_Factory implements Factory<GetReportDataUseCase> {
  private final Provider<BillRepository> billRepositoryProvider;

  private final Provider<PaymentRepository> paymentRepositoryProvider;

  public GetReportDataUseCase_Factory(Provider<BillRepository> billRepositoryProvider,
      Provider<PaymentRepository> paymentRepositoryProvider) {
    this.billRepositoryProvider = billRepositoryProvider;
    this.paymentRepositoryProvider = paymentRepositoryProvider;
  }

  @Override
  public GetReportDataUseCase get() {
    return newInstance(billRepositoryProvider.get(), paymentRepositoryProvider.get());
  }

  public static GetReportDataUseCase_Factory create(Provider<BillRepository> billRepositoryProvider,
      Provider<PaymentRepository> paymentRepositoryProvider) {
    return new GetReportDataUseCase_Factory(billRepositoryProvider, paymentRepositoryProvider);
  }

  public static GetReportDataUseCase newInstance(BillRepository billRepository,
      PaymentRepository paymentRepository) {
    return new GetReportDataUseCase(billRepository, paymentRepository);
  }
}
