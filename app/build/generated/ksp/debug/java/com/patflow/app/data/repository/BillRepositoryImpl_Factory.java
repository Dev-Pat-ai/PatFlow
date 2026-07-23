package com.patflow.app.data.repository;

import com.patflow.app.data.local.dao.BillCycleDao;
import com.patflow.app.data.local.dao.BillDao;
import com.patflow.app.domain.repository.ReminderRepository;
import com.patflow.app.domain.usecase.settings.GetUserSettingsUseCase;
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
public final class BillRepositoryImpl_Factory implements Factory<BillRepositoryImpl> {
  private final Provider<BillDao> billDaoProvider;

  private final Provider<BillCycleDao> billCycleDaoProvider;

  private final Provider<ReminderRepository> reminderRepositoryProvider;

  private final Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider;

  public BillRepositoryImpl_Factory(Provider<BillDao> billDaoProvider,
      Provider<BillCycleDao> billCycleDaoProvider,
      Provider<ReminderRepository> reminderRepositoryProvider,
      Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider) {
    this.billDaoProvider = billDaoProvider;
    this.billCycleDaoProvider = billCycleDaoProvider;
    this.reminderRepositoryProvider = reminderRepositoryProvider;
    this.getUserSettingsUseCaseProvider = getUserSettingsUseCaseProvider;
  }

  @Override
  public BillRepositoryImpl get() {
    return newInstance(billDaoProvider.get(), billCycleDaoProvider.get(), reminderRepositoryProvider.get(), getUserSettingsUseCaseProvider.get());
  }

  public static BillRepositoryImpl_Factory create(Provider<BillDao> billDaoProvider,
      Provider<BillCycleDao> billCycleDaoProvider,
      Provider<ReminderRepository> reminderRepositoryProvider,
      Provider<GetUserSettingsUseCase> getUserSettingsUseCaseProvider) {
    return new BillRepositoryImpl_Factory(billDaoProvider, billCycleDaoProvider, reminderRepositoryProvider, getUserSettingsUseCaseProvider);
  }

  public static BillRepositoryImpl newInstance(BillDao billDao, BillCycleDao billCycleDao,
      ReminderRepository reminderRepository, GetUserSettingsUseCase getUserSettingsUseCase) {
    return new BillRepositoryImpl(billDao, billCycleDao, reminderRepository, getUserSettingsUseCase);
  }
}
