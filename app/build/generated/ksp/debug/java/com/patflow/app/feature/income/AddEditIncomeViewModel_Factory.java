package com.patflow.app.feature.income;

import androidx.lifecycle.SavedStateHandle;
import com.patflow.app.domain.repository.IncomeRepository;
import com.patflow.app.domain.usecase.settings.GetUserSettingsUseCase;
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
public final class AddEditIncomeViewModel_Factory implements Factory<AddEditIncomeViewModel> {
  private final Provider<IncomeRepository> repositoryProvider;

  private final Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public AddEditIncomeViewModel_Factory(Provider<IncomeRepository> repositoryProvider,
      Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.repositoryProvider = repositoryProvider;
    this.getUserSettingsUseCaseProvider = getUserSettingsUseCaseProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public AddEditIncomeViewModel get() {
    return newInstance(repositoryProvider.get(), getUserSettingsUseCaseProvider.get(), savedStateHandleProvider.get());
  }

  public static AddEditIncomeViewModel_Factory create(Provider<IncomeRepository> repositoryProvider,
      Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new AddEditIncomeViewModel_Factory(repositoryProvider, getUserSettingsUseCaseProvider, savedStateHandleProvider);
  }

  public static AddEditIncomeViewModel newInstance(IncomeRepository repository,
      GetUserSettingsUseCase getUserSettingsUseCase, SavedStateHandle savedStateHandle) {
    return new AddEditIncomeViewModel(repository, getUserSettingsUseCase, savedStateHandle);
  }
}
