package com.patflow.app.domain.usecase.settings;

import com.patflow.app.domain.repository.SettingsRepository;
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
public final class UpdateUserPreferenceUseCase_Factory implements Factory<UpdateUserPreferenceUseCase> {
  private final Provider<SettingsRepository> repositoryProvider;

  public UpdateUserPreferenceUseCase_Factory(Provider<SettingsRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UpdateUserPreferenceUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UpdateUserPreferenceUseCase_Factory create(
      Provider<SettingsRepository> repositoryProvider) {
    return new UpdateUserPreferenceUseCase_Factory(repositoryProvider);
  }

  public static UpdateUserPreferenceUseCase newInstance(SettingsRepository repository) {
    return new UpdateUserPreferenceUseCase(repository);
  }
}
