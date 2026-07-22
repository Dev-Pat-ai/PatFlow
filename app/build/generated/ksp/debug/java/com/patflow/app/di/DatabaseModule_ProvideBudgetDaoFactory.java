package com.patflow.app.di;

import com.patflow.app.data.local.dao.BudgetDao;
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
public final class DatabaseModule_ProvideBudgetDaoFactory implements Factory<BudgetDao> {
  private final Provider<PatFlowDatabase> dbProvider;

  public DatabaseModule_ProvideBudgetDaoFactory(Provider<PatFlowDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public BudgetDao get() {
    return provideBudgetDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideBudgetDaoFactory create(
      Provider<PatFlowDatabase> dbProvider) {
    return new DatabaseModule_ProvideBudgetDaoFactory(dbProvider);
  }

  public static BudgetDao provideBudgetDao(PatFlowDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideBudgetDao(db));
  }
}
