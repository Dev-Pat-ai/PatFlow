package com.patflow.app.di;

import com.patflow.app.data.local.dao.IncomeDao;
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
public final class DatabaseModule_ProvideIncomeDaoFactory implements Factory<IncomeDao> {
  private final Provider<PatFlowDatabase> dbProvider;

  public DatabaseModule_ProvideIncomeDaoFactory(Provider<PatFlowDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public IncomeDao get() {
    return provideIncomeDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideIncomeDaoFactory create(
      Provider<PatFlowDatabase> dbProvider) {
    return new DatabaseModule_ProvideIncomeDaoFactory(dbProvider);
  }

  public static IncomeDao provideIncomeDao(PatFlowDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideIncomeDao(db));
  }
}
