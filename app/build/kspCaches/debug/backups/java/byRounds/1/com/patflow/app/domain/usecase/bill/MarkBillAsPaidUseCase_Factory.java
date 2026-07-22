package com.patflow.app.domain.usecase.bill;

import com.patflow.app.domain.repository.BillRepository;
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
  private final Provider<BillRepository> repositoryProvider;

  public MarkBillAsPaidUseCase_Factory(Provider<BillRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public MarkBillAsPaidUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static MarkBillAsPaidUseCase_Factory create(Provider<BillRepository> repositoryProvider) {
    return new MarkBillAsPaidUseCase_Factory(repositoryProvider);
  }

  public static MarkBillAsPaidUseCase newInstance(BillRepository repository) {
    return new MarkBillAsPaidUseCase(repository);
  }
}
