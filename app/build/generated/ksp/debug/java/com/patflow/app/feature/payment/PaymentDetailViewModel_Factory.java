package com.patflow.app.feature.payment;

import androidx.lifecycle.SavedStateHandle;
import com.patflow.app.domain.usecase.payment.GetPaymentDetailUseCase;
import com.patflow.app.domain.usecase.payment.UndoPaymentUseCase;
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
public final class PaymentDetailViewModel_Factory implements Factory<PaymentDetailViewModel> {
  private final Provider<GetPaymentDetailUseCase> getPaymentDetailUseCaseProvider;

  private final Provider<UndoPaymentUseCase> undoPaymentUseCaseProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public PaymentDetailViewModel_Factory(
      Provider<GetPaymentDetailUseCase> getPaymentDetailUseCaseProvider,
      Provider<UndoPaymentUseCase> undoPaymentUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.getPaymentDetailUseCaseProvider = getPaymentDetailUseCaseProvider;
    this.undoPaymentUseCaseProvider = undoPaymentUseCaseProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public PaymentDetailViewModel get() {
    return newInstance(getPaymentDetailUseCaseProvider.get(), undoPaymentUseCaseProvider.get(), savedStateHandleProvider.get());
  }

  public static PaymentDetailViewModel_Factory create(
      Provider<GetPaymentDetailUseCase> getPaymentDetailUseCaseProvider,
      Provider<UndoPaymentUseCase> undoPaymentUseCaseProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new PaymentDetailViewModel_Factory(getPaymentDetailUseCaseProvider, undoPaymentUseCaseProvider, savedStateHandleProvider);
  }

  public static PaymentDetailViewModel newInstance(GetPaymentDetailUseCase getPaymentDetailUseCase,
      UndoPaymentUseCase undoPaymentUseCase, SavedStateHandle savedStateHandle) {
    return new PaymentDetailViewModel(getPaymentDetailUseCase, undoPaymentUseCase, savedStateHandle);
  }
}
