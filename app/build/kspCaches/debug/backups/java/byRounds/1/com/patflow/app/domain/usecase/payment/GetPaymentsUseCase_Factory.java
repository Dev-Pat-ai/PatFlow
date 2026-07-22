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
public final class GetPaymentsUseCase_Factory implements Factory<GetPaymentsUseCase> {
  private final Provider<PaymentRepository> repositoryProvider;

  public GetPaymentsUseCase_Factory(Provider<PaymentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetPaymentsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetPaymentsUseCase_Factory create(Provider<PaymentRepository> repositoryProvider) {
    return new GetPaymentsUseCase_Factory(repositoryProvider);
  }

  public static GetPaymentsUseCase newInstance(PaymentRepository repository) {
    return new GetPaymentsUseCase(repository);
  }
}
