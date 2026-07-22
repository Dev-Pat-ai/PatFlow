package com.patflow.app.di;

import com.patflow.app.data.local.dao.SavingsGoalDao;
import com.patflow.app.data.local.database.PatFlowDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideSavingsGoalDaoFactory implements Factory<SavingsGoalDao> {
  private final Provider<PatFlowDatabase> dbProvider;

  public DatabaseModule_ProvideSavingsGoalDaoFactory(Provider<PatFlowDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SavingsGoalDao get() {
    return provideSavingsGoalDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideSavingsGoalDaoFactory create(
      Provider<PatFlowDatabase> dbProvider) {
    return new DatabaseModule_ProvideSavingsGoalDaoFactory(dbProvider);
  }

  public static SavingsGoalDao provideSavingsGoalDao(PatFlowDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSavingsGoalDao(db));
  }
}
