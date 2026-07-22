package com.patflow.app.feature.payment;

import com.patflow.app.domain.usecase.payment.GetPaymentsUseCase;
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
public final class PaymentHistoryViewModel_Factory implements Factory<PaymentHistoryViewModel> {
  private final Provider<GetPaymentsUseCase> getPaymentsUseCaseProvider;

  public PaymentHistoryViewModel_Factory(Provider<GetPaymentsUseCase> getPaymentsUseCaseProvider) {
    this.getPaymentsUseCaseProvider = getPaymentsUseCaseProvider;
  }

  @Override
  public PaymentHistoryViewModel get() {
    return newInstance(getPaymentsUseCaseProvider.get());
  }

  public static PaymentHistoryViewModel_Factory create(
      Provider<GetPaymentsUseCase> getPaymentsUseCaseProvider) {
    return new PaymentHistoryViewModel_Factory(getPaymentsUseCaseProvider);
  }

  public static PaymentHistoryViewModel newInstance(GetPaymentsUseCase getPaymentsUseCase) {
    return new PaymentHistoryViewModel(getPaymentsUseCase);
  }
}
