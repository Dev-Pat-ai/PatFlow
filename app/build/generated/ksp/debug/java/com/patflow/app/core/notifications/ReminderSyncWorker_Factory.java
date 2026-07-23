package com.patflow.app.core.notifications;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.patflow.app.data.local.dao.ReminderDao;
import com.patflow.app.domain.repository.BillRepository;
import com.patflow.app.domain.repository.IncomeRepository;
import com.patflow.app.domain.repository.NotificationRepository;
import com.patflow.app.domain.repository.ReminderRepository;
import dagger.internal.DaggerGenerated;
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
public final class ReminderSyncWorker_Factory {
  private final Provider<ReminderDao> reminderDaoProvider;

  private final Provider<ReminderRepository> reminderRepositoryProvider;

  private final Provider<BillRepository> billRepositoryProvider;

  private final Provider<IncomeRepository> incomeRepositoryProvider;

  private final Provider<NotificationRepository> notificationRepositoryProvider;

  public ReminderSyncWorker_Factory(Provider<ReminderDao> reminderDaoProvider,
      Provider<ReminderRepository> reminderRepositoryProvider,
      Provider<BillRepository> billRepositoryProvider,
      Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<NotificationRepository> notificationRepositoryProvider) {
    this.reminderDaoProvider = reminderDaoProvider;
    this.reminderRepositoryProvider = reminderRepositoryProvider;
    this.billRepositoryProvider = billRepositoryProvider;
    this.incomeRepositoryProvider = incomeRepositoryProvider;
    this.notificationRepositoryProvider = notificationRepositoryProvider;
  }

  public ReminderSyncWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, reminderDaoProvider.get(), reminderRepositoryProvider.get(), billRepositoryProvider.get(), incomeRepositoryProvider.get(), notificationRepositoryProvider.get());
  }

  public static ReminderSyncWorker_Factory create(Provider<ReminderDao> reminderDaoProvider,
      Provider<ReminderRepository> reminderRepositoryProvider,
      Provider<BillRepository> billRepositoryProvider,
      Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<NotificationRepository> notificationRepositoryProvider) {
    return new ReminderSyncWorker_Factory(reminderDaoProvider, reminderRepositoryProvider, billRepositoryProvider, incomeRepositoryProvider, notificationRepositoryProvider);
  }

  public static ReminderSyncWorker newInstance(Context context, WorkerParameters params,
      ReminderDao reminderDao, ReminderRepository reminderRepository, BillRepository billRepository,
      IncomeRepository incomeRepository, NotificationRepository notificationRepository) {
    return new ReminderSyncWorker(context, params, reminderDao, reminderRepository, billRepository, incomeRepository, notificationRepository);
  }
}
