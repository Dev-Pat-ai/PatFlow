package com.patflow.app.feature.bills;

import androidx.lifecycle.SavedStateHandle;
import com.patflow.app.domain.repository.CategoryRepository;
import com.patflow.app.domain.usecase.bill.AddBillUseCase;
import com.patflow.app.domain.usecase.bill.GetBillDetailUseCase;
import com.patflow.app.domain.usecase.bill.UpdateBillUseCase;
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
public final class AddEditBillViewModel_Factory implements Factory<AddEditBillViewModel> {
  private final Provider<AddBillUseCase> addBillUseCaseProvider;

  private final Provider<UpdateBillUseCase> updateBillUseCaseProvider;

  private final Provider<GetBillDetailUseCase> getBillDetailUseCaseProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public AddEditBillViewModel_Factory(Provider<AddBillUseCase> addBillUseCaseProvider,
      Provider<UpdateBillUseCase> updateBillUseCaseProvider,
      Provider<GetBillDetailUseCase> getBillDetailUseCaseProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.addBillUseCaseProvider = addBillUseCaseProvider;
    this.updateBillUseCaseProvider = updateBillUseCaseProvider;
    this.getBillDetailUseCaseProvider = getBillDetailUseCaseProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.getUserSettingsUseCaseProvider = getUserSettingsUseCaseProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public AddEditBillViewModel get() {
    return newInstance(addBillUseCaseProvider.get(), updateBillUseCaseProvider.get(), getBillDetailUseCaseProvider.get(), categoryRepositoryProvider.get(), getUserSettingsUseCaseProvider.get(), savedStateHandleProvider.get());
  }

  public static AddEditBillViewModel_Factory create(Provider<AddBillUseCase> addBillUseCaseProvider,
      Provider<UpdateBillUseCase> updateBillUseCaseProvider,
      Provider<GetBillDetailUseCase> getBillDetailUseCaseProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new AddEditBillViewModel_Factory(addBillUseCaseProvider, updateBillUseCaseProvider, getBillDetailUseCaseProvider, categoryRepositoryProvider, getUserSettingsUseCaseProvider, savedStateHandleProvider);
  }

  public static AddEditBillViewModel newInstance(AddBillUseCase addBillUseCase,
      UpdateBillUseCase updateBillUseCase, GetBillDetailUseCase getBillDetailUseCase,
      CategoryRepository categoryRepository, GetUserSettingsUseCase getUserSettingsUseCase,
      SavedStateHandle savedStateHandle) {
    return new AddEditBillViewModel(addBillUseCase, updateBillUseCase, getBillDetailUseCase, categoryRepository, getUserSettingsUseCase, savedStateHandle);
  }
}
