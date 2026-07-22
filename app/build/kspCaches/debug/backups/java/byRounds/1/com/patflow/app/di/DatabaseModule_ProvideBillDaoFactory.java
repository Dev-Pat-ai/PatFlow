package com.patflow.app.di;

import com.patflow.app.data.local.dao.BillDao;
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
public final class DatabaseModule_ProvideBillDaoFactory implements Factory<BillDao> {
  private final Provider<PatFlowDatabase> dbProvider;

  public DatabaseModule_ProvideBillDaoFactory(Provider<PatFlowDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public BillDao get() {
    return provideBillDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideBillDaoFactory create(Provider<PatFlowDatabase> dbProvider) {
    return new DatabaseModule_ProvideBillDaoFactory(dbProvider);
  }

  public static BillDao provideBillDao(PatFlowDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideBillDao(db));
  }
}
