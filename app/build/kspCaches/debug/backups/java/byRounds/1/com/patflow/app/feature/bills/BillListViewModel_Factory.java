package com.patflow.app.feature.bills;

import com.patflow.app.domain.usecase.bill.GetBillsUseCase;
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

  public BillListViewModel_Factory(Provider<GetBillsUseCase> getBillsUseCaseProvider) {
    this.getBillsUseCaseProvider = getBillsUseCaseProvider;
  }

  @Override
  public BillListViewModel get() {
    return newInstance(getBillsUseCaseProvider.get());
  }

  public static BillListViewModel_Factory create(
      Provider<GetBillsUseCase> getBillsUseCaseProvider) {
    return new BillListViewModel_Factory(getBillsUseCaseProvider);
  }

  public static BillListViewModel newInstance(GetBillsUseCase getBillsUseCase) {
    return new BillListViewModel(getBillsUseCase);
  }
}
