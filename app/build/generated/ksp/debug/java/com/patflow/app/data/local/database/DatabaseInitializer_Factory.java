package com.patflow.app.data.local.database;

import com.patflow.app.data.local.dao.CategoryDao;
import com.patflow.app.data.local.dao.IncomeDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DatabaseInitializer_Factory implements Factory<DatabaseInitializer> {
  private final Provider<CategoryDao> categoryDaoProvider;

  private final Provider<IncomeDao> incomeDaoProvider;

  public DatabaseInitializer_Factory(Provider<CategoryDao> categoryDaoProvider,
      Provider<IncomeDao> incomeDaoProvider) {
    this.categoryDaoProvider = categoryDaoProvider;
    this.incomeDaoProvider = incomeDaoProvider;
  }

  @Override
  public DatabaseInitializer get() {
    return newInstance(categoryDaoProvider.get(), incomeDaoProvider.get());
  }

  public static DatabaseInitializer_Factory create(Provider<CategoryDao> categoryDaoProvider,
      Provider<IncomeDao> incomeDaoProvider) {
    return new DatabaseInitializer_Factory(categoryDaoProvider, incomeDaoProvider);
  }

  public static DatabaseInitializer newInstance(CategoryDao categoryDao, IncomeDao incomeDao) {
    return new DatabaseInitializer(categoryDao, incomeDao);
  }
}
