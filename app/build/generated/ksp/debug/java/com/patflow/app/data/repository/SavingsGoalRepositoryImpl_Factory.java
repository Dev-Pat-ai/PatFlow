package com.patflow.app.data.repository;

import com.patflow.app.data.local.dao.SavingsGoalDao;
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
public final class SavingsGoalRepositoryImpl_Factory implements Factory<SavingsGoalRepositoryImpl> {
  private final Provider<SavingsGoalDao> savingsGoalDaoProvider;

  public SavingsGoalRepositoryImpl_Factory(Provider<SavingsGoalDao> savingsGoalDaoProvider) {
    this.savingsGoalDaoProvider = savingsGoalDaoProvider;
  }

  @Override
  public SavingsGoalRepositoryImpl get() {
    return newInstance(savingsGoalDaoProvider.get());
  }

  public static SavingsGoalRepositoryImpl_Factory create(
      Provider<SavingsGoalDao> savingsGoalDaoProvider) {
    return new SavingsGoalRepositoryImpl_Factory(savingsGoalDaoProvider);
  }

  public static SavingsGoalRepositoryImpl newInstance(SavingsGoalDao savingsGoalDao) {
    return new SavingsGoalRepositoryImpl(savingsGoalDao);
  }
}
