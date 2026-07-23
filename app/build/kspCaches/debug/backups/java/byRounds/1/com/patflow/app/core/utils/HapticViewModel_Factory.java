package com.patflow.app.core.utils;

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
public final class HapticViewModel_Factory implements Factory<HapticViewModel> {
  private final Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider;

  public HapticViewModel_Factory(Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider) {
    this.getUserSettingsUseCaseProvider = getUserSettingsUseCaseProvider;
  }

  @Override
  public HapticViewModel get() {
    return newInstance(getUserSettingsUseCaseProvider.get());
  }

  public static HapticViewModel_Factory create(
      Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider) {
    return new HapticViewModel_Factory(getUserSettingsUseCaseProvider);
  }

  public static HapticViewModel newInstance(GetUserSettingsUseCase getUserSettingsUseCase) {
    return new HapticViewModel(getUserSettingsUseCase);
  }
}
