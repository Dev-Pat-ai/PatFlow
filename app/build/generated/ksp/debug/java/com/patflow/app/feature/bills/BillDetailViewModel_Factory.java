package com.patflow.app.feature.bills;

import androidx.lifecycle.SavedStateHandle;
import com.patflow.app.domain.usecase.bill.DeleteBillUseCase;
import com.patflow.app.domain.usecase.bill.GetBillDetailUseCase;
import com.patflow.app.domain.usecase.bill.MarkBillAsPaidUseCase;
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
public final class BillDetailViewModel_Factory implements Factory<BillDetailViewModel> {
  private final Provider<GetBillDetailUseCase> getBillDetailUseCaseProvider;

  private final Provider<DeleteBillUseCase> deleteBillUseCaseProvider;

  private final Provider<MarkBillAsPaidUseCase> markBillAsPaidUseCaseProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public BillDetailViewModel_Factory(Provider<GetBillDetailUseCase> getBillDetailUseCaseProvider,
      Provider<DeleteBillUseCase> deleteBillUseCaseProvider,
      Provider<MarkBillAsPaidUseCase> markBillAsPaidUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.getBillDetailUseCaseProvider = getBillDetailUseCaseProvider;
    this.deleteBillUseCaseProvider = deleteBillUseCaseProvider;
    this.markBillAsPaidUseCaseProvider = markBillAsPaidUseCaseProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public BillDetailViewModel get() {
    return newInstance(getBillDetailUseCaseProvider.get(), deleteBillUseCaseProvider.get(), markBillAsPaidUseCaseProvider.get(), savedStateHandleProvider.get());
  }

  public static BillDetailViewModel_Factory create(
      Provider<GetBillDetailUseCase> getBillDetailUseCaseProvider,
      Provider<DeleteBillUseCase> deleteBillUseCaseProvider,
      Provider<MarkBillAsPaidUseCase> markBillAsPaidUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new BillDetailViewModel_Factory(getBillDetailUseCaseProvider, deleteBillUseCaseProvider, markBillAsPaidUseCaseProvider, savedStateHandleProvider);
  }

  public static BillDetailViewModel newInstance(GetBillDetailUseCase getBillDetailUseCase,
      DeleteBillUseCase deleteBillUseCase, MarkBillAsPaidUseCase markBillAsPaidUseCase,
      SavedStateHandle savedStateHandle) {
    return new BillDetailViewModel(getBillDetailUseCase, deleteBillUseCase, markBillAsPaidUseCase, savedStateHandle);
  }
}
