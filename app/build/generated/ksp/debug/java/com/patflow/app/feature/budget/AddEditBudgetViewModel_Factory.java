package com.patflow.app.feature.budget;

import androidx.lifecycle.SavedStateHandle;
import com.patflow.app.domain.repository.BudgetRepository;
import com.patflow.app.domain.repository.CategoryRepository;
import com.patflow.app.domain.usecase.budget.AddBudgetUseCase;
import com.patflow.app.domain.usecase.budget.UpdateBudgetUseCase;
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
public final class AddEditBudgetViewModel_Factory implements Factory<AddEditBudgetViewModel> {
  private final Provider<BudgetRepository> budgetRepositoryProvider;

  private final Provider<AddBudgetUseCase> addBudgetUseCaseProvider;

  private final Provider<UpdateBudgetUseCase> updateBudgetUseCaseProvider;

  private final Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public AddEditBudgetViewModel_Factory(Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<AddBudgetUseCase> addBudgetUseCaseProvider,
      Provider<UpdateBudgetUseCase> updateBudgetUseCaseProvider,
      Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.budgetRepositoryProvider = budgetRepositoryProvider;
    this.addBudgetUseCaseProvider = addBudgetUseCaseProvider;
    this.updateBudgetUseCaseProvider = updateBudgetUseCaseProvider;
    this.getUserSettingsUseCaseProvider = getUserSettingsUseCaseProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public AddEditBudgetViewModel get() {
    return newInstance(budgetRepositoryProvider.get(), addBudgetUseCaseProvider.get(), updateBudgetUseCaseProvider.get(), getUserSettingsUseCaseProvider.get(), categoryRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static AddEditBudgetViewModel_Factory create(
      Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<AddBudgetUseCase> addBudgetUseCaseProvider,
      Provider<UpdateBudgetUseCase> updateBudgetUseCaseProvider,
      Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new AddEditBudgetViewModel_Factory(budgetRepositoryProvider, addBudgetUseCaseProvider, updateBudgetUseCaseProvider, getUserSettingsUseCaseProvider, categoryRepositoryProvider, savedStateHandleProvider);
  }

  public static AddEditBudgetViewModel newInstance(BudgetRepository budgetRepository,
      AddBudgetUseCase addBudgetUseCase, UpdateBudgetUseCase updateBudgetUseCase,
      GetUserSettingsUseCase getUserSettingsUseCase, CategoryRepository categoryRepository,
      SavedStateHandle savedStateHandle) {
    return new AddEditBudgetViewModel(budgetRepository, addBudgetUseCase, updateBudgetUseCase, getUserSettingsUseCase, categoryRepository, savedStateHandle);
  }
}
