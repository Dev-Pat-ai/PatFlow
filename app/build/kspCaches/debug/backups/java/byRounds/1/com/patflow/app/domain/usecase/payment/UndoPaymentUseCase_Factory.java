package com.patflow.app.domain.usecase.payment;

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
public final class UndoPaymentUseCase_Factory implements Factory<UndoPaymentUseCase> {
  private final Provider<PaymentRepository> paymentRepositoryProvider;

  public UndoPaymentUseCase_Factory(Provider<PaymentRepository> paymentRepositoryProvider) {
    this.paymentRepositoryProvider = paymentRepositoryProvider;
  }

  @Override
  public UndoPaymentUseCase get() {
    return newInstance(paymentRepositoryProvider.get());
  }

  public static UndoPaymentUseCase_Factory create(
      Provider<PaymentRepository> paymentRepositoryProvider) {
    return new UndoPaymentUseCase_Factory(paymentRepositoryProvider);
  }

  public static UndoPaymentUseCase newInstance(PaymentRepository paymentRepository) {
    return new UndoPaymentUseCase(paymentRepository);
  }
}
