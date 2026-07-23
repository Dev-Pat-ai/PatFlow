package com.patflow.app.data.repository;

import com.patflow.app.data.local.dao.BudgetDao;
import com.patflow.app.data.local.dao.CategoryDao;
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
public final class BudgetRepositoryImpl_Factory implements Factory<BudgetRepositoryImpl> {
  private final Provider<BudgetDao> budgetDaoProvider;

  private final Provider<CategoryDao> categoryDaoProvider;

  public BudgetRepositoryImpl_Factory(Provider<BudgetDao> budgetDaoProvider,
      Provider<CategoryDao> categoryDaoProvider) {
    this.budgetDaoProvider = budgetDaoProvider;
    this.categoryDaoProvider = categoryDaoProvider;
  }

  @Override
  public BudgetRepositoryImpl get() {
    return newInstance(budgetDaoProvider.get(), categoryDaoProvider.get());
  }

  public static BudgetRepositoryImpl_Factory create(Provider<BudgetDao> budgetDaoProvider,
      Provider<CategoryDao> categoryDaoProvider) {
    return new BudgetRepositoryImpl_Factory(budgetDaoProvider, categoryDaoProvider);
  }

  public static BudgetRepositoryImpl newInstance(BudgetDao budgetDao, CategoryDao categoryDao) {
    return new BudgetRepositoryImpl(budgetDao, categoryDao);
  }
}
