package com.patflow.app.feature.savings;

import androidx.lifecycle.SavedStateHandle;
import com.patflow.app.domain.repository.SavingsGoalRepository;
import com.patflow.app.domain.usecase.savings.AddSavingsGoalUseCase;
import com.patflow.app.domain.usecase.savings.UpdateSavingsGoalUseCase;
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
public final class AddEditSavingsGoalViewModel_Factory implements Factory<AddEditSavingsGoalViewModel> {
  private final Provider<SavingsGoalRepository> repositoryProvider;

  private final Provider<AddSavingsGoalUseCase> addGoalUseCaseProvider;

  private final Provider<UpdateSavingsGoalUseCase> updateGoalUseCaseProvider;

  private final Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public AddEditSavingsGoalViewModel_Factory(Provider<SavingsGoalRepository> repositoryProvider,
      Provider<AddSavingsGoalUseCase> addGoalUseCaseProvider,
      Provider<UpdateSavingsGoalUseCase> updateGoalUseCaseProvider,
      Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.repositoryProvider = repositoryProvider;
    this.addGoalUseCaseProvider = addGoalUseCaseProvider;
    this.updateGoalUseCaseProvider = updateGoalUseCaseProvider;
    this.getUserSettingsUseCaseProvider = getUserSettingsUseCaseProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public AddEditSavingsGoalViewModel get() {
    return newInstance(repositoryProvider.get(), addGoalUseCaseProvider.get(), updateGoalUseCaseProvider.get(), getUserSettingsUseCaseProvider.get(), savedStateHandleProvider.get());
  }

  public static AddEditSavingsGoalViewModel_Factory create(
      Provider<SavingsGoalRepository> repositoryProvider,
      Provider<AddSavingsGoalUseCase> addGoalUseCaseProvider,
      Provider<UpdateSavingsGoalUseCase> updateGoalUseCaseProvider,
      Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new AddEditSavingsGoalViewModel_Factory(repositoryProvider, addGoalUseCaseProvider, updateGoalUseCaseProvider, getUserSettingsUseCaseProvider, savedStateHandleProvider);
  }

  public static AddEditSavingsGoalViewModel newInstance(SavingsGoalRepository repository,
      AddSavingsGoalUseCase addGoalUseCase, UpdateSavingsGoalUseCase updateGoalUseCase,
      GetUserSettingsUseCase getUserSettingsUseCase, SavedStateHandle savedStateHandle) {
    return new AddEditSavingsGoalViewModel(repository, addGoalUseCase, updateGoalUseCase, getUserSettingsUseCase, savedStateHandle);
  }
}
