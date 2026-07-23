package com.patflow.app.feature.income;

import com.patflow.app.domain.repository.IncomeRepository;
import com.patflow.app.domain.usecase.income.DeleteIncomeEntryUseCase;
import com.patflow.app.domain.usecase.income.DuplicateIncomeEntryUseCase;
import com.patflow.app.domain.usecase.income.GetIncomeEntriesUseCase;
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
public final class IncomeViewModel_Factory implements Factory<IncomeViewModel> {
  private final Provider<GetIncomeEntriesUseCase> getIncomeEntriesUseCaseProvider;

  private final Provider<DeleteIncomeEntryUseCase> deleteIncomeEntryUseCaseProvider;

  private final Provider<DuplicateIncomeEntryUseCase> duplicateIncomeEntryUseCaseProvider;

  private final Provider<IncomeRepository> repositoryProvider;

  public IncomeViewModel_Factory(Provider<GetIncomeEntriesUseCase> getIncomeEntriesUseCaseProvider,
      Provider<DeleteIncomeEntryUseCase> deleteIncomeEntryUseCaseProvider,
      Provider<DuplicateIncomeEntryUseCase> duplicateIncomeEntryUseCaseProvider,
      Provider<IncomeRepository> repositoryProvider) {
    this.getIncomeEntriesUseCaseProvider = getIncomeEntriesUseCaseProvider;
    this.deleteIncomeEntryUseCaseProvider = deleteIncomeEntryUseCaseProvider;
    this.duplicateIncomeEntryUseCaseProvider = duplicateIncomeEntryUseCaseProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public IncomeViewModel get() {
    return newInstance(getIncomeEntriesUseCaseProvider.get(), deleteIncomeEntryUseCaseProvider.get(), duplicateIncomeEntryUseCaseProvider.get(), repositoryProvider.get());
  }

  public static IncomeViewModel_Factory create(
      Provider<GetIncomeEntriesUseCase> getIncomeEntriesUseCaseProvider,
      Provider<DeleteIncomeEntryUseCase> deleteIncomeEntryUseCaseProvider,
      Provider<DuplicateIncomeEntryUseCase> duplicateIncomeEntryUseCaseProvider,
      Provider<IncomeRepository> repositoryProvider) {
    return new IncomeViewModel_Factory(getIncomeEntriesUseCaseProvider, deleteIncomeEntryUseCaseProvider, duplicateIncomeEntryUseCaseProvider, repositoryProvider);
  }

  public static IncomeViewModel newInstance(GetIncomeEntriesUseCase getIncomeEntriesUseCase,
      DeleteIncomeEntryUseCase deleteIncomeEntryUseCase,
      DuplicateIncomeEntryUseCase duplicateIncomeEntryUseCase, IncomeRepository repository) {
    return new IncomeViewModel(getIncomeEntriesUseCase, deleteIncomeEntryUseCase, duplicateIncomeEntryUseCase, repository);
  }
}
