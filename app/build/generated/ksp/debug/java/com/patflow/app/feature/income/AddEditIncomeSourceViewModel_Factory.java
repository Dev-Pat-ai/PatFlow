package com.patflow.app.feature.income;

import androidx.lifecycle.SavedStateHandle;
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
public final class AddEditIncomeSourceViewModel_Factory implements Factory<AddEditIncomeSourceViewModel> {
  private final Provider<IncomeRepository> repositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public AddEditIncomeSourceViewModel_Factory(Provider<IncomeRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.repositoryProvider = repositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public AddEditIncomeSourceViewModel get() {
    return newInstance(repositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static AddEditIncomeSourceViewModel_Factory create(
      Provider<IncomeRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new AddEditIncomeSourceViewModel_Factory(repositoryProvider, savedStateHandleProvider);
  }

  public static AddEditIncomeSourceViewModel newInstance(IncomeRepository repository,
      SavedStateHandle savedStateHandle) {
    return new AddEditIncomeSourceViewModel(repository, savedStateHandle);
  }
}
