package com.patflow.app.feature.dashboard;

import com.patflow.app.domain.usecase.dashboard.GetDashboardDataUseCase;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<GetDashboardDataUseCase> getDashboardDataUseCaseProvider;

  private final Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider;

  public DashboardViewModel_Factory(
      Provider<GetDashboardDataUseCase> getDashboardDataUseCaseProvider,
      Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider) {
    this.getDashboardDataUseCaseProvider = getDashboardDataUseCaseProvider;
    this.getUserSettingsUseCaseProvider = getUserSettingsUseCaseProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(getDashboardDataUseCaseProvider.get(), getUserSettingsUseCaseProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<GetDashboardDataUseCase> getDashboardDataUseCaseProvider,
      Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider) {
    return new DashboardViewModel_Factory(getDashboardDataUseCaseProvider, getUserSettingsUseCaseProvider);
  }

  public static DashboardViewModel newInstance(GetDashboardDataUseCase getDashboardDataUseCase,
      GetUserSettingsUseCase getUserSettingsUseCase) {
    return new DashboardViewModel(getDashboardDataUseCase, getUserSettingsUseCase);
  }
}
