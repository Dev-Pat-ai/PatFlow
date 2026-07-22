package com.patflow.app.di;

import com.patflow.app.data.local.dao.BillCycleDao;
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
public final class DatabaseModule_ProvideBillCycleDaoFactory implements Factory<BillCycleDao> {
  private final Provider<PatFlowDatabase> dbProvider;

  public DatabaseModule_ProvideBillCycleDaoFactory(Provider<PatFlowDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public BillCycleDao get() {
    return provideBillCycleDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideBillCycleDaoFactory create(
      Provider<PatFlowDatabase> dbProvider) {
    return new DatabaseModule_ProvideBillCycleDaoFactory(dbProvider);
  }

  public static BillCycleDao provideBillCycleDao(PatFlowDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideBillCycleDao(db));
  }
}
