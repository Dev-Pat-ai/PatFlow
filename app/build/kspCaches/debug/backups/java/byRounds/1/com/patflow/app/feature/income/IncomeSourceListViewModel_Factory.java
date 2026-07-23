package com.patflow.app.feature.income;

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
public final class IncomeSourceListViewModel_Factory implements Factory<IncomeSourceListViewModel> {
  private final Provider<IncomeRepository> repositoryProvider;

  public IncomeSourceListViewModel_Factory(Provider<IncomeRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public IncomeSourceListViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static IncomeSourceListViewModel_Factory create(
      Provider<IncomeRepository> repositoryProvider) {
    return new IncomeSourceListViewModel_Factory(repositoryProvider);
  }

  public static IncomeSourceListViewModel newInstance(IncomeRepository repository) {
    return new IncomeSourceListViewModel(repository);
  }
}
