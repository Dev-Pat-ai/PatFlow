package com.patflow.app.domain.usecase.savings;

import com.patflow.app.domain.repository.SavingsGoalRepository;
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
public final class AddSavingsContributionUseCase_Factory implements Factory<AddSavingsContributionUseCase> {
  private final Provider<SavingsGoalRepository> repositoryProvider;

  public AddSavingsContributionUseCase_Factory(Provider<SavingsGoalRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AddSavingsContributionUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static AddSavingsContributionUseCase_Factory create(
      Provider<SavingsGoalRepository> repositoryProvider) {
    return new AddSavingsContributionUseCase_Factory(repositoryProvider);
  }

  public static AddSavingsContributionUseCase newInstance(SavingsGoalRepository repository) {
    return new AddSavingsContributionUseCase(repository);
  }
}
