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
public final class GetBillsUseCase_Factory implements Factory<GetBillsUseCase> {
  private final Provider<BillRepository> repositoryProvider;

  public GetBillsUseCase_Factory(Provider<BillRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetBillsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetBillsUseCase_Factory create(Provider<BillRepository> repositoryProvider) {
    return new GetBillsUseCase_Factory(repositoryProvider);
  }

  public static GetBillsUseCase newInstance(BillRepository repository) {
    return new GetBillsUseCase(repository);
  }
}
