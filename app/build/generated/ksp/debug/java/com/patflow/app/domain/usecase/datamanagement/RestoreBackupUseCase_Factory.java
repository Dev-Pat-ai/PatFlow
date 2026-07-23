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
public final class RestoreBackupUseCase_Factory implements Factory<RestoreBackupUseCase> {
  private final Provider<DataManagementRepository> repositoryProvider;

  public RestoreBackupUseCase_Factory(Provider<DataManagementRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public RestoreBackupUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static RestoreBackupUseCase_Factory create(
      Provider<DataManagementRepository> repositoryProvider) {
    return new RestoreBackupUseCase_Factory(repositoryProvider);
  }

  public static RestoreBackupUseCase newInstance(DataManagementRepository repository) {
    return new RestoreBackupUseCase(repository);
  }
}
