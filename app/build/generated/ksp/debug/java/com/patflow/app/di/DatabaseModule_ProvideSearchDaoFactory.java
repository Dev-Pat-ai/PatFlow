package com.patflow.app.di;

import com.patflow.app.data.local.dao.SearchDao;
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
public final class DatabaseModule_ProvideSearchDaoFactory implements Factory<SearchDao> {
  private final Provider<PatFlowDatabase> dbProvider;

  public DatabaseModule_ProvideSearchDaoFactory(Provider<PatFlowDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SearchDao get() {
    return provideSearchDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideSearchDaoFactory create(
      Provider<PatFlowDatabase> dbProvider) {
    return new DatabaseModule_ProvideSearchDaoFactory(dbProvider);
  }

  public static SearchDao provideSearchDao(PatFlowDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSearchDao(db));
  }
}
