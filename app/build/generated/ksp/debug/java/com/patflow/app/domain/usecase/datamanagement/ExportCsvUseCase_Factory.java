package com.patflow.app.domain.usecase.datamanagement;

import com.patflow.app.domain.repository.DataManagementRepository;
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
public final class ExportCsvUseCase_Factory implements Factory<ExportCsvUseCase> {
  private final Provider<DataManagementRepository> repositoryProvider;

  public ExportCsvUseCase_Factory(Provider<DataManagementRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ExportCsvUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ExportCsvUseCase_Factory create(
      Provider<DataManagementRepository> repositoryProvider) {
    return new ExportCsvUseCase_Factory(repositoryProvider);
  }

  public static ExportCsvUseCase newInstance(DataManagementRepository repository) {
    return new ExportCsvUseCase(repository);
  }
}
