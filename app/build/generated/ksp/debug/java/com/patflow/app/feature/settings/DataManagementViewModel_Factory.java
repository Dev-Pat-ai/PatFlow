package com.patflow.app.feature.settings;

import com.patflow.app.domain.usecase.datamanagement.CreateBackupUseCase;
import com.patflow.app.domain.usecase.datamanagement.ExportCsvUseCase;
import com.patflow.app.domain.usecase.datamanagement.RestoreBackupUseCase;
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
public final class DataManagementViewModel_Factory implements Factory<DataManagementViewModel> {
  private final Provider<CreateBackupUseCase> createBackupUseCaseProvider;

  private final Provider<RestoreBackupUseCase> restoreBackupUseCaseProvider;

  private final Provider<ExportCsvUseCase> exportCsvUseCaseProvider;

  public DataManagementViewModel_Factory(Provider<CreateBackupUseCase> createBackupUseCaseProvider,
      Provider<RestoreBackupUseCase> restoreBackupUseCaseProvider,
      Provider<ExportCsvUseCase> exportCsvUseCaseProvider) {
    this.createBackupUseCaseProvider = createBackupUseCaseProvider;
    this.restoreBackupUseCaseProvider = restoreBackupUseCaseProvider;
    this.exportCsvUseCaseProvider = exportCsvUseCaseProvider;
  }

  @Override
  public DataManagementViewModel get() {
    return newInstance(createBackupUseCaseProvider.get(), restoreBackupUseCaseProvider.get(), exportCsvUseCaseProvider.get());
  }

  public static DataManagementViewModel_Factory create(
      Provider<CreateBackupUseCase> createBackupUseCaseProvider,
      Provider<RestoreBackupUseCase> restoreBackupUseCaseProvider,
      Provider<ExportCsvUseCase> exportCsvUseCaseProvider) {
    return new DataManagementViewModel_Factory(createBackupUseCaseProvider, restoreBackupUseCaseProvider, exportCsvUseCaseProvider);
  }

  public static DataManagementViewModel newInstance(CreateBackupUseCase createBackupUseCase,
      RestoreBackupUseCase restoreBackupUseCase, ExportCsvUseCase exportCsvUseCase) {
    return new DataManagementViewModel(createBackupUseCase, restoreBackupUseCase, exportCsvUseCase);
  }
}
