package com.patflow.app.feature.bills;

import com.patflow.app.domain.usecase.bill.DeleteBillUseCase;
import com.patflow.app.domain.usecase.bill.GetBillsUseCase;
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
public final class BillListViewModel_Factory implements Factory<BillListViewModel> {
  private final Provider<GetBillsUseCase> getBillsUseCaseProvider;

  private final Provider<DeleteBillUseCase> deleteBillUseCaseProvider;

  private final Provider<MarkBillAsPaidUseCase> markBillAsPaidUseCaseProvider;

  public BillListViewModel_Factory(Provider<GetBillsUseCase> getBillsUseCaseProvider,
      Provider<DeleteBillUseCase> deleteBillUseCaseProvider,
      Provider<MarkBillAsPaidUseCase> markBillAsPaidUseCaseProvider) {
    this.getBillsUseCaseProvider = getBillsUseCaseProvider;
    this.deleteBillUseCaseProvider = deleteBillUseCaseProvider;
    this.markBillAsPaidUseCaseProvider = markBillAsPaidUseCaseProvider;
  }

  @Override
  public BillListViewModel get() {
    return newInstance(getBillsUseCaseProvider.get(), deleteBillUseCaseProvider.get(), markBillAsPaidUseCaseProvider.get());
  }

  public static BillListViewModel_Factory create(Provider<GetBillsUseCase> getBillsUseCaseProvider,
      Provider<DeleteBillUseCase> deleteBillUseCaseProvider,
      Provider<MarkBillAsPaidUseCase> markBillAsPaidUseCaseProvider) {
    return new BillListViewModel_Factory(getBillsUseCaseProvider, deleteBillUseCaseProvider, markBillAsPaidUseCaseProvider);
  }

  public static BillListViewModel newInstance(GetBillsUseCase getBillsUseCase,
      DeleteBillUseCase deleteBillUseCase, MarkBillAsPaidUseCase markBillAsPaidUseCase) {
    return new BillListViewModel(getBillsUseCase, deleteBillUseCase, markBillAsPaidUseCase);
  }
}
