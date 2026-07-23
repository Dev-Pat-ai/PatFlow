package com.patflow.app.data.repository;

import com.patflow.app.data.local.dao.BillCycleDao;
import com.patflow.app.data.local.dao.BillDao;
import com.patflow.app.data.local.dao.CategoryDao;
import com.patflow.app.data.local.dao.PaymentDao;
import com.patflow.app.data.local.database.PatFlowDatabase;
import com.patflow.app.domain.repository.SettingsRepository;
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
public final class DataManagementRepositoryImpl_Factory implements Factory<DataManagementRepositoryImpl> {
  private final Provider<PatFlowDatabase> databaseProvider;

  private final Provider<BillDao> billDaoProvider;

  private final Provider<BillCycleDao> billCycleDaoProvider;

  private final Provider<PaymentDao> paymentDaoProvider;

  private final Provider<CategoryDao> categoryDaoProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public DataManagementRepositoryImpl_Factory(Provider<PatFlowDatabase> databaseProvider,
      Provider<BillDao> billDaoProvider, Provider<BillCycleDao> billCycleDaoProvider,
      Provider<PaymentDao> paymentDaoProvider, Provider<CategoryDao> categoryDaoProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.databaseProvider = databaseProvider;
    this.billDaoProvider = billDaoProvider;
    this.billCycleDaoProvider = billCycleDaoProvider;
    this.paymentDaoProvider = paymentDaoProvider;
    this.categoryDaoProvider = categoryDaoProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public DataManagementRepositoryImpl get() {
    return newInstance(databaseProvider.get(), billDaoProvider.get(), billCycleDaoProvider.get(), paymentDaoProvider.get(), categoryDaoProvider.get(), settingsRepositoryProvider.get());
  }

  public static DataManagementRepositoryImpl_Factory create(
      Provider<PatFlowDatabase> databaseProvider, Provider<BillDao> billDaoProvider,
      Provider<BillCycleDao> billCycleDaoProvider, Provider<PaymentDao> paymentDaoProvider,
      Provider<CategoryDao> categoryDaoProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new DataManagementRepositoryImpl_Factory(databaseProvider, billDaoProvider, billCycleDaoProvider, paymentDaoProvider, categoryDaoProvider, settingsRepositoryProvider);
  }

  public static DataManagementRepositoryImpl newInstance(PatFlowDatabase database, BillDao billDao,
      BillCycleDao billCycleDao, PaymentDao paymentDao, CategoryDao categoryDao,
      SettingsRepository settingsRepository) {
    return new DataManagementRepositoryImpl(database, billDao, billCycleDao, paymentDao, categoryDao, settingsRepository);
  }
}
