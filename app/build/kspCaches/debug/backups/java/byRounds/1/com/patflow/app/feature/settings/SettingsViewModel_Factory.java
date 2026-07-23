package com.patflow.app.feature.settings;

import com.patflow.app.domain.repository.NotificationRepository;
import com.patflow.app.domain.usecase.settings.GetUserSettingsUseCase;
import com.patflow.app.domain.usecase.settings.UpdateUserPreferenceUseCase;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider;

  private final Provider<UpdateUserPreferenceUseCase> updatePreferenceProvider;

  private final Provider<NotificationRepository> notificationRepositoryProvider;

  public SettingsViewModel_Factory(Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider,
      Provider<UpdateUserPreferenceUseCase> updatePreferenceProvider,
      Provider<NotificationRepository> notificationRepositoryProvider) {
    this.getUserSettingsUseCaseProvider = getUserSettingsUseCaseProvider;
    this.updatePreferenceProvider = updatePreferenceProvider;
    this.notificationRepositoryProvider = notificationRepositoryProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(getUserSettingsUseCaseProvider.get(), updatePreferenceProvider.get(), notificationRepositoryProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider,
      Provider<UpdateUserPreferenceUseCase> updatePreferenceProvider,
      Provider<NotificationRepository> notificationRepositoryProvider) {
    return new SettingsViewModel_Factory(getUserSettingsUseCaseProvider, updatePreferenceProvider, notificationRepositoryProvider);
  }

  public static SettingsViewModel newInstance(GetUserSettingsUseCase getUserSettingsUseCase,
      UpdateUserPreferenceUseCase updatePreference, NotificationRepository notificationRepository) {
    return new SettingsViewModel(getUserSettingsUseCase, updatePreference, notificationRepository);
  }
}
