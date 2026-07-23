package com.patflow.app.domain.usecase.budget;

import com.patflow.app.domain.repository.BudgetRepository;
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
public final class ArchiveBudgetUseCase_Factory implements Factory<ArchiveBudgetUseCase> {
  private final Provider<BudgetRepository> repositoryProvider;

  public ArchiveBudgetUseCase_Factory(Provider<BudgetRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ArchiveBudgetUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ArchiveBudgetUseCase_Factory create(Provider<BudgetRepository> repositoryProvider) {
    return new ArchiveBudgetUseCase_Factory(repositoryProvider);
  }

  public static ArchiveBudgetUseCase newInstance(BudgetRepository repository) {
    return new ArchiveBudgetUseCase(repository);
  }
}
