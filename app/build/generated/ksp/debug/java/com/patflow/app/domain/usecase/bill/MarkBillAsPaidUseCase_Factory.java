package com.patflow.app.domain.usecase.bill;

import com.patflow.app.domain.usecase.payment.LogPaymentUseCase;
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
public final class MarkBillAsPaidUseCase_Factory implements Factory<MarkBillAsPaidUseCase> {
  private final Provider<LogPaymentUseCase> logPaymentUseCaseProvider;

  public MarkBillAsPaidUseCase_Factory(Provider<LogPaymentUseCase> logPaymentUseCaseProvider) {
    this.logPaymentUseCaseProvider = logPaymentUseCaseProvider;
  }

  @Override
  public MarkBillAsPaidUseCase get() {
    return newInstance(logPaymentUseCaseProvider.get());
  }

  public static MarkBillAsPaidUseCase_Factory create(
      Provider<LogPaymentUseCase> logPaymentUseCaseProvider) {
    return new MarkBillAsPaidUseCase_Factory(logPaymentUseCaseProvider);
  }

  public static MarkBillAsPaidUseCase newInstance(LogPaymentUseCase logPaymentUseCase) {
    return new MarkBillAsPaidUseCase(logPaymentUseCase);
  }
}
