package com.patflow.app.data.repository;

import androidx.work.WorkManager;
import com.patflow.app.data.local.dao.ReminderDao;
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
public final class ReminderRepositoryImpl_Factory implements Factory<ReminderRepositoryImpl> {
  private final Provider<ReminderDao> reminderDaoProvider;

  private final Provider<WorkManager> workManagerProvider;

  public ReminderRepositoryImpl_Factory(Provider<ReminderDao> reminderDaoProvider,
      Provider<WorkManager> workManagerProvider) {
    this.reminderDaoProvider = reminderDaoProvider;
    this.workManagerProvider = workManagerProvider;
  }

  @Override
  public ReminderRepositoryImpl get() {
    return newInstance(reminderDaoProvider.get(), workManagerProvider.get());
  }

  public static ReminderRepositoryImpl_Factory create(Provider<ReminderDao> reminderDaoProvider,
      Provider<WorkManager> workManagerProvider) {
    return new ReminderRepositoryImpl_Factory(reminderDaoProvider, workManagerProvider);
  }

  public static ReminderRepositoryImpl newInstance(ReminderDao reminderDao,
      WorkManager workManager) {
    return new ReminderRepositoryImpl(reminderDao, workManager);
  }
}
