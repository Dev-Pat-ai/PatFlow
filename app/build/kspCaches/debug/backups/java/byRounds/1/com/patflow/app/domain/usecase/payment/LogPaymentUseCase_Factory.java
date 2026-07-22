package com.patflow.app.domain.usecase.payment;

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
public final class LogPaymentUseCase_Factory implements Factory<LogPaymentUseCase> {
  private final Provider<PaymentRepository> paymentRepositoryProvider;

  private final Provider<BillRepository> billRepositoryProvider;

  public LogPaymentUseCase_Factory(Provider<PaymentRepository> paymentRepositoryProvider,
      Provider<BillRepository> billRepositoryProvider) {
    this.paymentRepositoryProvider = paymentRepositoryProvider;
    this.billRepositoryProvider = billRepositoryProvider;
  }

  @Override
  public LogPaymentUseCase get() {
    return newInstance(paymentRepositoryProvider.get(), billRepositoryProvider.get());
  }

  public static LogPaymentUseCase_Factory create(
      Provider<PaymentRepository> paymentRepositoryProvider,
      Provider<BillRepository> billRepositoryProvider) {
    return new LogPaymentUseCase_Factory(paymentRepositoryProvider, billRepositoryProvider);
  }

  public static LogPaymentUseCase newInstance(PaymentRepository paymentRepository,
      BillRepository billRepository) {
    return new LogPaymentUseCase(paymentRepository, billRepository);
  }
}
