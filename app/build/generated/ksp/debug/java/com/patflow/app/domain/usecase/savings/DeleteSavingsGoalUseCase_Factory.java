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
public final class DeleteSavingsGoalUseCase_Factory implements Factory<DeleteSavingsGoalUseCase> {
  private final Provider<SavingsGoalRepository> repositoryProvider;

  public DeleteSavingsGoalUseCase_Factory(Provider<SavingsGoalRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeleteSavingsGoalUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeleteSavingsGoalUseCase_Factory create(
      Provider<SavingsGoalRepository> repositoryProvider) {
    return new DeleteSavingsGoalUseCase_Factory(repositoryProvider);
  }

  public static DeleteSavingsGoalUseCase newInstance(SavingsGoalRepository repository) {
    return new DeleteSavingsGoalUseCase(repository);
  }
}
