package com.patflow.app.domain.usecase.income;

import com.patflow.app.domain.repository.IncomeRepository;
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
public final class GetIncomeSourcesUseCase_Factory implements Factory<GetIncomeSourcesUseCase> {
  private final Provider<IncomeRepository> repositoryProvider;

  public GetIncomeSourcesUseCase_Factory(Provider<IncomeRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetIncomeSourcesUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetIncomeSourcesUseCase_Factory create(
      Provider<IncomeRepository> repositoryProvider) {
    return new GetIncomeSourcesUseCase_Factory(repositoryProvider);
  }

  public static GetIncomeSourcesUseCase newInstance(IncomeRepository repository) {
    return new GetIncomeSourcesUseCase(repository);
  }
}
