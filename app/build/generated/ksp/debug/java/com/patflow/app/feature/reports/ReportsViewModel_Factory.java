package com.patflow.app.feature.reports;

import com.patflow.app.domain.usecase.report.GetReportDataUseCase;
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
public final class ReportsViewModel_Factory implements Factory<ReportsViewModel> {
  private final Provider<GetReportDataUseCase> getReportDataUseCaseProvider;

  private final Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider;

  public ReportsViewModel_Factory(Provider<GetReportDataUseCase> getReportDataUseCaseProvider,
      Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider) {
    this.getReportDataUseCaseProvider = getReportDataUseCaseProvider;
    this.getUserSettingsUseCaseProvider = getUserSettingsUseCaseProvider;
  }

  @Override
  public ReportsViewModel get() {
    return newInstance(getReportDataUseCaseProvider.get(), getUserSettingsUseCaseProvider.get());
  }

  public static ReportsViewModel_Factory create(
      Provider<GetReportDataUseCase> getReportDataUseCaseProvider,
      Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider) {
    return new ReportsViewModel_Factory(getReportDataUseCaseProvider, getUserSettingsUseCaseProvider);
  }

  public static ReportsViewModel newInstance(GetReportDataUseCase getReportDataUseCase,
      GetUserSettingsUseCase getUserSettingsUseCase) {
    return new ReportsViewModel(getReportDataUseCase, getUserSettingsUseCase);
  }
}
