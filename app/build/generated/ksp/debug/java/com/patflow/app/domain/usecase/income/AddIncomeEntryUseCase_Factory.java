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
public final class AddIncomeEntryUseCase_Factory implements Factory<AddIncomeEntryUseCase> {
  private final Provider<IncomeRepository> repositoryProvider;

  public AddIncomeEntryUseCase_Factory(Provider<IncomeRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AddIncomeEntryUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static AddIncomeEntryUseCase_Factory create(
      Provider<IncomeRepository> repositoryProvider) {
    return new AddIncomeEntryUseCase_Factory(repositoryProvider);
  }

  public static AddIncomeEntryUseCase newInstance(IncomeRepository repository) {
    return new AddIncomeEntryUseCase(repository);
  }
}
