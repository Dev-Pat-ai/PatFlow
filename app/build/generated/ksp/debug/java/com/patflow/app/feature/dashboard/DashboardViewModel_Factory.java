package com.patflow.app.feature.dashboard;

import com.patflow.app.domain.usecase.dashboard.GetDashboardDataUseCase;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<GetDashboardDataUseCase> getDashboardDataUseCaseProvider;

  public DashboardViewModel_Factory(
      Provider<GetDashboardDataUseCase> getDashboardDataUseCaseProvider) {
    this.getDashboardDataUseCaseProvider = getDashboardDataUseCaseProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(getDashboardDataUseCaseProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<GetDashboardDataUseCase> getDashboardDataUseCaseProvider) {
    return new DashboardViewModel_Factory(getDashboardDataUseCaseProvider);
  }

  public static DashboardViewModel newInstance(GetDashboardDataUseCase getDashboardDataUseCase) {
    return new DashboardViewModel(getDashboardDataUseCase);
  }
}
