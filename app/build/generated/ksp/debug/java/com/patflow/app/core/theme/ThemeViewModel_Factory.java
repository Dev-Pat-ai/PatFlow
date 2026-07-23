package com.patflow.app.core.theme;

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
public final class ThemeViewModel_Factory implements Factory<ThemeViewModel> {
  private final Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider;

  public ThemeViewModel_Factory(Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider) {
    this.getUserSettingsUseCaseProvider = getUserSettingsUseCaseProvider;
  }

  @Override
  public ThemeViewModel get() {
    return newInstance(getUserSettingsUseCaseProvider.get());
  }

  public static ThemeViewModel_Factory create(
      Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider) {
    return new ThemeViewModel_Factory(getUserSettingsUseCaseProvider);
  }

  public static ThemeViewModel newInstance(GetUserSettingsUseCase getUserSettingsUseCase) {
    return new ThemeViewModel(getUserSettingsUseCase);
  }
}
